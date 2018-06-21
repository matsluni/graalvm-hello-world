name := "graalvm-hello"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= {
  Seq(
    "com.squareup.okhttp3"    %  "okhttp"         % "3.10.0",
    "org.scala-lang.modules"  %% "scala-xml"      % "1.1.0",
    "io.circe"                %% "circe-core"     % "0.9.3",
    "io.circe"                %% "circe-generic"  % "0.9.3",
    "io.circe"                %% "circe-parser"   % "0.9.3"
  )
}