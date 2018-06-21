# Readme

## use without installation

export JAVA_HOME=<graalvm-download-location>
export PATH=$JAVA_HOME/bin:$PATH

## 

- create assembly with

      sbt assembly
  
- create native image with

      native-image -jar target/scala-2.12/graalvm-hello-assembly-0.1.jar
      
  `$JAVA_HOME` needs to point to the graalvm jdk home folder
  `$PATH` needs to include `$JAVA_HOME/bin` 