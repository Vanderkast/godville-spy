# Run in docker container

You can run Godville Spy Telegram bot in docker container.

# Configuration

You can skip this step if you have filled in _application.properties_ file on your machine.

Run `setup.sh` script, that will create `./resources` directory and copy _application.properties_ and _logback.xml_ files from `telegram-bot` sources for bot configuration.
Fill in _application.properties_ in created _resources_ directory.

You can change directory to copy from if specify environment variable `RESOURCES_DIR`.

# Startup

Run `run.sh` script to start docker container. _resources_ directory will be mounted to container.
You can specify following environment variables to configure script behavior:

| Variable        | Description                    | Default                 |
|-----------------|--------------------------------|-------------------------|
| RESOURCES_DIR   | Resources directory path       | ./resources             |
| DOCKER_REGISTRY | Docker registry                | ghcr.io                 |
| IMAGE_NAME      | Image name                     | vanderkast/godville-spy |
| IMAGE_TAG       | Image tag                      | latest                  |
| DETACH          | Run container in detached mode | false                   |

Example:
```shell
DETACH=true IMAGE_TAG=1.0.0-rc2 ./run.sh
```
