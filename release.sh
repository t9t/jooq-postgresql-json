#!/bin/bash

set -euv

if [[ $# -lt 1 ]]; then
  echo 'Usage: ./release.sh <new version>'
  exit 1
fi

mvn -Prelease release:clean release:prepare -DpushChanges=false -Dresume=false -DreleaseVersion=${1}
mvn -Prelease release:perform
mvn -Prelease deploy
