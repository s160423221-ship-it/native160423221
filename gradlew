#!/bin/sh
APP_HOME=$(cd "${0%/*}" 2>/dev/null; pwd -P)
exec java -Dorg.gradle.appname=gradlew -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@"
