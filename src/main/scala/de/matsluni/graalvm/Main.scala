package de.matsluni.graalvm

import java.net.URLEncoder

import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl
import okhttp3.{ConnectionSpec, OkHttpClient, Request, Response}

import scala.collection.JavaConverters._
import scala.xml.factory.XMLLoader
import scala.xml.{Elem, Source, XML}
import io.circe._, io.circe.generic.auto._
import io.circe.parser._, io.circe.syntax._

object Main {

  val baseUrl = "http://localhost:9324"

  def main(args: Array[String]): Unit = {

    case class Order(orderId: Long, items: List[String], orderPrice: Double)
    val order = Order(1, List("Pants", "Socks"), 10.0)


    val queueActions = new QueueActions(baseUrl)
     import queueActions._
     val resp = for {
       queueUrl <- createQueue("myQueue")
       _ <- writeToQueue(queueUrl, order.asJson.noSpaces)
       message <- readFromQueue(queueUrl)
     } yield message

     resp.foreach(json => println(decode[Order](json)))
  }
}

class QueueActions(baseUrl: String) {

  val loader: XMLLoader[Elem] = XML.withSAXParser(new SAXParserFactoryImpl().newSAXParser())
  val client = new OkHttpClient.Builder().connectionSpecs((ConnectionSpec.CLEARTEXT :: Nil).asJava).build()


  def readFromQueue(queueUrl: String): Option[String] = {
    val response =  runRequest(s"$queueUrl?Action=ReceiveMessage&MaxNumberOfMessages=1")
    val body = response.body().string()
    val bodyRes = readNode(body, "Body")
    response.close()
    bodyRes
  }

  def writeToQueue(queueUrl: String, messageBody: String): Option[String] = {
    val encoded = URLEncoder.encode(messageBody, "utf-8")
    val response = runRequest(s"$queueUrl?Action=SendMessage&MessageBody=$encoded")
    val body = readNode(response.body().string(), "MessageId")
    response.close()
    body
  }

  def createQueue(queueName: String): Option[String] = {
    val response = runRequest(s"$baseUrl?Action=CreateQueue&QueueName=$queueName")
    val body = readNode(response.body().string(), "QueueUrl")
    response.close()
    body
  }

  def readNode(body: String, nodeName: String): Option[String] = {
    (loader.load(Source.fromString(body)) \\ nodeName).toList.map(_.text.trim).headOption
  }

  def runRequest(url: String): Response = {
    client.newCall(new Request.Builder().url(url).build()).execute()
  }

}
