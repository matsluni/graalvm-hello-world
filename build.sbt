name := "graalvm-hello"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++={
  Seq(
    "software.amazon.awssdk" % "sqs" % "2.0.0-preview-10"
  )
}

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("META-INF/io.netty.versions.properties") ⇒ MergeStrategy.first
  case x ⇒
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
