# CompoundZebra Community Discord Server Bot #

This is a custom [Discord] bot for the community server of the Twitch streamer [CompoundZebra].

The token must be supplied in the environment variable `DISCORD_BOT_TOKEN`.

[Discord]: <https://discord.com/> "Discord | Your Place to Talk and Hang Out"
[CompoundZebra]: <https://www.twitch.tv/compoundzebra> "CompoundZebra - Twitch"

## Module Structure ##

Module IDs formatted in **bold** are "real" modules, while non-bold ones are *just* groups/folders for other submodules
and don't actually contain any source code themselves.

* **`:bot`**
  * `:bot:config`
    * **`:bot:config:models`**
    * `:bot:config:serialization`
      * **`:bot:config:serialization:public`**
      * **`:bot:config:serialization:impl-json`**
    * `:bot:config:source`
      * **`:bot:config:source:public`**
      * **`:bot:config:source:impl-file`**
    * `:bot:config:cache`
      * **`:bot:config:cache:public`**
      * **`:bot:config:cache:impl-memory`**
    * `:bot:config:supplier`
      * **`:bot:config:supplier:public`**
      * **`:bot:config:supplier:impl-cache`**

TODO: unfinished
