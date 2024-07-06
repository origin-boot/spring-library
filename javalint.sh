#!/bin/bash

CS_VERSION="10.17.0"
CS_JAR="checkstyle-${CS_VERSION}-all.jar"
CS_CONFIG="google_checks.xml"

LINT_VERSION="1.0.1"
LINT_JAR="spring-lint-${LINT_VERSION}.jar"

if [ ! -f "$CS_CONFIG" ]; then
    echo "Checked that ${CS_CONFIG} does not exist and is downloading..."
    wget -q https://raw.githubusercontent.com/checkstyle/checkstyle/checkstyle-"$CS_VERSION"/src/main/resources/"$CS_CONFIG"
    if [ $? -ne 0 ]; then
        echo "Download failed, please check whether the network connection or URL is correct."
        exit 1
    fi
fi


if [ ! -f "$CS_JAR" ]; then
    echo "Checked that ${CS_JAR} does not exist and is downloading..."
    wget -q https://github.com/checkstyle/checkstyle/releases/download/checkstyle-"$CS_VERSION"/"$CS_JAR"
    if [ $? -ne 0 ]; then
        echo "Download failed, please check whether the network connection or URL is correct."
        exit 1
    fi
fi

if [ ! -f "$LINT_JAR" ]; then
    echo "Checked that ${LINT_JAR} does not exist and is downloading..."
    wget -q https://github.com/origin-boot/spring-lint/releases/download/spring-lint-"$LINT_VERSION"/"$LINT_JAR"
    if [ $? -ne 0 ]; then
        echo "Download failed, please check whether the network connection or URL is correct."
        exit 1
    fi
fi


params=("$@")

if [ ${#params[@]} -eq 0 ]; then
    echo "No parameters are provided, please specify the Java source file to be checked."
    exit 1
fi

ERROR=0

LOGS=$( java -jar "$CS_JAR" -c "$CS_CONFIG" "${params[@]}" )
echo "$LOGS"

if echo "$LOGS" | grep -q "\["; then
    echo "Error or warning detected in the logs."
    ERROR=1
else
    echo "No errors or warnings detected."
fi

java -jar spring-lint-"$LINT_VERSION".jar "${params[@]}"
if [ $? -ne 0 ]; then
    ERROR=1
fi

exit $ERROR
