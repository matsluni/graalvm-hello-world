# Readme

- create assembly with

      sbt assembly
  
- create native image with

      $JAVA_HOME/bin/native-image -jar target/scala-2.12/graalvm-hello-assembly-0.1.jar
      
  `$JAVA_HOME` needs to point to the graalvm jdk home folder  