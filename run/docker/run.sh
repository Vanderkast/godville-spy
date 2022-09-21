#!/bin/bash

set -e

if [[ -z "${DOCKER_REGISTRY}" ]]; then
  DOCKER_REGISTRY="ghcr.io"
else
  DOCKER_REGISTRY="${DOCKER_REGISTRY}"
fi

if [[ -z "${IMAGE_NAME}" ]]; then
  IMAGE_NAME="vanderkast/godville-spy"
else
  IMAGE_NAME="${IMAGE_NAME}"
fi

if [[ -z "${IMAGE_TAG}" ]]; then
  IMAGE_TAG="latest"
else
  IMAGE_TAG="${IMAGE_TAG}"
fi

if [[ "${DETACH}" == "true" ]]; then
  ARGS="--detach"
fi

if [[ -z "${RESOURCES_DIR}" ]]; then
  RESOURCES_DIR=$(pwd)/resources
else
  RESOURCES_DIR="${RESOURCES_DIR}"
fi

RESOURCES_DIR_HOST=$RESOURCES_DIR
RESOURCES_DIR_CONTAINER=/godville-spy/resources

docker run \
  $ARGS \
  -v "$RESOURCES_DIR_HOST":$RESOURCES_DIR_CONTAINER \
  -e CONFIG_PATH=$RESOURCES_DIR_CONTAINER/application.properties \
  $DOCKER_REGISTRY/$IMAGE_NAME:$IMAGE_TAG
