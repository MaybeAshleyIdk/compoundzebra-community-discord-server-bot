# Systemd Service #

Since the bot JAR has no auto start or restart capabilities itself,
an external managing software needs to be used for it.  
This section describes how auto start and restart can be achieved using [systemd].

**Note:** Basic knowledge of systemd and it's units- & services system is recommended, but not required for
this step-by-step guide.

Create a new file `czd-bot.service` in the directory `/etc/systemd/system/` and the following contents, replacing
the placeholders with their actual values:

```ini
[Unit]
Description=CompoundZebra Community Discord Guild Bot
Documentation=https://github.com/MaybeAshleyIdk/compoundzebra-community-discord-server-bot#readme
Requires=network-online.target
After=network-online.target

[Service]
# optionally set the user and/or group the bot should be executed as (see manpage systemd.exec(5))
#User=<user>
#Group=<group>
Type=exec
WorkingDirectory=<absolute path to the directory containing the file bot_config.json>
ExecStart=<absolute path to the bot JAR>
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

To enable or disable auto start after system boot, use the following commands:

```sh
systemctl enable czd-bot.service
systemctl disable czd-bot.service
```

To start, stop or restart the bot, use the following commands:

```sh
systemctl start czd-bot.service
systemctl stop czd-bot.service
systemctl restart czd-bot.service
```

[systemd]: <https://systemd.io/> "System and Service Manager"
