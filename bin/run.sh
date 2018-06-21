#!/usr/bin/env bash
export JAVA_HOME=/Users/ssengespeick/software/graalvm-ee-1.0.0-rc2/Contents/Home
sbt assembly
${JAVA_HOME}/bin/native-image \
    -H:IncludeResources=software/amazon/awssdk/regions/internal/region/endpoints.json \
    -H:ReflectionConfigurationFiles=reflection.json \
    -H:+ReportUnsupportedElementsAtRuntime -jar target/scala-2.12/graalvm-hello-assembly-0.1.jar
./graalvm-hello-assembly-0.1
