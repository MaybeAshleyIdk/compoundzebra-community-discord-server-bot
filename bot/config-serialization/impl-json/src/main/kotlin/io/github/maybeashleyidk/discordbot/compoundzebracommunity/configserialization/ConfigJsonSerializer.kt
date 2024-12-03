package io.github.maybeashleyidk.discordbot.compoundzebracommunity.configserialization

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import okio.BufferedSink
import okio.BufferedSource
import javax.inject.Inject

public class ConfigJsonSerializer @Inject constructor(moshi: Moshi) : ConfigSerializer {

	@OptIn(ExperimentalStdlibApi::class)
	private val configJsonAdapter: JsonAdapter<ConfigJson> = moshi.adapter<ConfigJson>()
		.nonNull()
		.lenient()
		.indent("\t")

	override fun serialize(config: Config, sink: BufferedSink) {
		val initConfigJson: ConfigJson = ConfigModelAdapter.transformConfig(config)
		this.configJsonAdapter.toJson(sink, initConfigJson)
	}

	override fun deserialize(source: BufferedSource): Config {
		val configJson: ConfigJson? = this.configJsonAdapter.fromJson(source)
		checkNotNull(configJson)
		return ConfigModelAdapter.transformConfig(configJson)
	}
}
