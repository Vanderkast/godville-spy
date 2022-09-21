#!/bin/bash

if [[ -z "${RESOURCES_DIR}" ]]; then
  RESOURCES_DIR="../../telegram-bot/src/main/resources"
else
  RESOURCES_DIR="${RESOURCES_DIR}"
fi

mkdir resources
cp -r RESOURCES_DIR/application.properties.tpl resources/application.properties
cp -r RESOURCES_DIR/logback.xml resources/logback.xml

