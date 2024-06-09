package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

@JvmInline
public value class RoleMention(public val roleId: ULong) : TextElement {

	private companion object {

		// mirroring the official Discord client's behavior
		const val UNKNOWN_ROLE_NAME: String = "deleted-role"
	}

	override fun toRawString(): String {
		return "<@&${this.roleId}>"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		val roleName: String =
			when (channelContext) {
				is PrivateChannel -> null

				is GuildChannel -> {
					channelContext.guild.roles
						.firstOrNull { role: Role ->
							role.idLong.toULong() == this.roleId
						}
						?.name
				}

				else -> null
			}
				?: Companion.UNKNOWN_ROLE_NAME

		return "@$roleName"
	}
}
