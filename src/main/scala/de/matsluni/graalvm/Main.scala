package de.matsluni.graalvm

import okhttp3.{ConnectionSpec, OkHttpClient, Request}

import scala.collection.JavaConverters._

object Main {

  def main(args: Array[String]): Unit = {

    val client = new OkHttpClient.Builder().connectionSpecs((ConnectionSpec.CLEARTEXT :: Nil).asJava).build()
    val request = new Request.Builder()
      .url("http://localhost:9324?Action=ListQueues")
      .build()

    val response = client.newCall(request).execute()

    println(response.body.string)

  }
}
