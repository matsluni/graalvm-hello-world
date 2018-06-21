package de.matsluni.graalvm

import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl
import okhttp3.{ConnectionSpec, OkHttpClient, Request}

import scala.collection.JavaConverters._
import scala.xml.{NodeSeq, Source, XML}

object Main {

  def main(args: Array[String]): Unit = {

    val loader = XML.withSAXParser(new SAXParserFactoryImpl().newSAXParser())

    val client = new OkHttpClient.Builder().connectionSpecs((ConnectionSpec.CLEARTEXT :: Nil).asJava).build()
    val requestl = new Request.Builder()
      .url("http://localhost:9324?Action=ListQueues")
      .build()

    val requestw = new Request.Builder()
      .url("http://localhost:9324?Action=CreateQueue&QueueName=MyQueue")
      .build()

    val responsew = client.newCall(requestw).execute()
    val response = client.newCall(requestl).execute()

    val queueList: List[String] = (loader.load(Source.fromString(response.body.string)) \ "ListQueuesResult").toList.map(_.text.trim)

    println(queueList)

  }
}
