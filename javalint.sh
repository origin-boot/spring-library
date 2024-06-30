#!/bin/bash

CS_JAR="checkstyle-10.17.0-all.jar"

if [ ! -f "$CS_JAR" ]; then
    echo "Checked that ${CS_JAR} does not exist and is downloading..."
    wget -q https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.17.0/"$CS_JAR"
    if [ $? -ne 0 ]; then
        echo "Download failed, please check whether the network connection or URL is correct."
        exit 1
    fi
fi

params=("$@")

# 检查是否有参数传递
if [ ${#params[@]} -eq 0 ]; then
    echo "No parameters are provided, please specify the Java source file to be checked."
    exit 1
fi

java -jar $CS_JAR -c /google_checks.xml "${params[@]}"
