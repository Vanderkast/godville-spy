# Godville Spy Telegram bot

In this topic Telegram bot implementation of Godville Spy is described.

## Configuration

Godville Spy can be configured with `application.properties` config file. Template file can be found
in [sources](../telegram-bot/src/main/resources/application.properties.tpl).

Location of `application.properties` file must be configured with environment variable `CONFIG_PATH`.

`application.properties` content:

| Variable                              | Description                                                                   | Type | Required |
|---------------------------------------|-------------------------------------------------------------------------------|------|----------|
| `bot.token`                           | Telegram bot [token](https://core.telegram.org/bots/api#authorizing-your-bot) | str  | yes      |
| `bot.owner`                           | Telegram user name that will receive notifications                            | int  | yes      |
| `bot.notification.on-health-percents` | Send notification if character health is below this value                     | str  | yes      |
| `god.name`                            | Godville player (God) name                                                    | str  | yes      |
| `god.token`                           | Godville API access [token](https://godville.net/user/profile).               | str  | yes      |


## Docker image

You can run Godville Spy in docker container. More information in [../run/docker/readme.md](../run/docker/readme.md)

## Work

Bot waits a user to send `/bound` command to send notifications to them.
Note that name of user must be the same as specified in `bot.owner` configuration variable otherwise bot will not send notifications. 
