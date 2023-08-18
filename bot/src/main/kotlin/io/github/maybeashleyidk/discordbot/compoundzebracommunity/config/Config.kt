package io.github.maybeashleyidk.discordbot.compoundzebracommunity.config

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
	val dummy: Int?,
)
