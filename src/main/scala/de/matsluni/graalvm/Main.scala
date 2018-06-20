package de.matsluni.graalvm

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.services.sqs.SQSAsyncClient


object Main {

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    val client = SQSAsyncClient.builder()
      .credentialsProvider(ProfileCredentialsProvider.create())
      .region(software.amazon.awssdk.regions.Region.EU_WEST_1)
      .build()

    val queues = client.listQueues().join()

    println(queues.queueUrls())

  }
}
