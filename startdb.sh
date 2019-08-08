#!/bin/sh

mvn -pl integration-tests docker:start flyway:migrate jooq-codegen:generate
