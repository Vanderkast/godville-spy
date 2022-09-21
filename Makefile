APP_NAME = godville-spy

DOCKER_REGISTRY ?= ghcr.io
IMAGE_NAME ?= vanderkast/${APP_NAME}
IMAGE_TAG ?= latest

IMAGE_NAME_FULL=${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}

image:
	docker build -t ${IMAGE_NAME_FULL} .
