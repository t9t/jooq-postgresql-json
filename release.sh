#!/bin/bash

set -euv

if [[ $# -lt 1 ]]; then
  echo 'Usage: ./release.sh <new version>'
  exit 1
fi

JAVAC_VERSION=$(javac -version |& cut -d ' ' -f 2)
echo "javac version: ${JAVAC_VERSION}"

if [[ ${JAVAC_VERSION} != 1.8* ]]; then
  echo 'Please use JDK8'
  exit 1
fi

mvn -Prelease release:clean release:prepare -DpushChanges=false -Dresume=false -DreleaseVersion=${1}
mvn -Prelease release:perform
mvn -Prelease deploy
