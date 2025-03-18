package io.github.maybeashleyidk.discordbot.compoundzebracommunity.slashcommands

import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

public data class ImmutableSlashCommandData(
	public val name: String,
	public val description: String,
) {

	init {
		// Checks whether the field are valid.
		this.mutable()
	}

	public fun mutable(): SlashCommandData {
		return Commands.slash(this.name, this.description)
	}
}

public fun SlashCommandData.immutable(): ImmutableSlashCommandData {
	return ImmutableSlashCommandData(
		name = this.name,
		description = this.description,
	)
}
