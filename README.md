# CompoundZebra Community Discord Server Bot #

## About ##

This is a custom [Discord] bot for the community server of the Twitch streamer [CompoundZebra].

[Discord]: <https://discord.com/> "Discord | Your Place to Talk and Hang Out"
[CompoundZebra]: <https://www.twitch.tv/compoundzebra> "CompoundZebra - Twitch"

## Features ##

├─── WIP ───┤

## Download ##

The fully built executable JAR file (that is compressed using `gzip`) can be downloaded from the [GitHub release page].

**Shellscript snippet to download+uncompress the file and set the executable bit on it:**

<!-- markdownlint-disable line-length -->
```sh
wget -O- 'https://github.com/MaybeAshleyIdk/compoundzebra-community-discord-server-bot/releases/download/v0.1.0-indev10/czd-bot-0.1.0-indev10.gz' |
	gzip -d > 'czd-bot-0.1.0-indev10' &&
	chmod +x 'czd-bot-0.1.0-indev10'
```
<!-- markdownlint-enable line-length -->

[GitHub release page]: <https://github.com/MaybeAshleyIdk/compoundzebra-community-discord-server-bot/releases/tag/v0.1.0-indev10> "Release Version 0.1.0-indev10 · MaybeAshleyIdk/compoundzebra-community-discord-server-bot"

## Usage ##

The execute the JAR file, a Java 17 (or above) runtime is required.  
The bot token must be supplied in the environment variable `DISCORD_BOT_TOKEN`.

```sh
DISCORD_BOT_TOKEN='************************************************************************' \
	./czd-bot-0.1.0-indev10
```

If the JAR file is executed like shown above and the environment variable `JAVA_HOME` is *not* unset or empty, then
a Java runtime will be automatically searched in the directory that is stored in that environment variable.  
If no `java` command is found under this directory, then the program will abort.

If the environment variable `JAVA_HOME` *is* unset or empty, then the previous step is skipped and the command `java`,
found in the `PATH`, is used instead.  
If the `java` command can also not be found in the `PATH`, then the program will also abort.

Additional JVM options can be passed along via the environment variable `JAVA_OPTS`.

---

Alternatively, the JAR file can be executed via the command `java -jar` (on Microsoft Windows `javaw -jar`),
which obviously gives full control over which Java runtime is used and which JVM options are passed:

```sh
DISCORD_BOT_TOKEN='************************************************************************' \
	java -jar czd-bot-0.1.0-indev10
```

## License ##

This bot is licensed under the [**GNU General Public License v3.0 or later**](LICENSE.txt).
