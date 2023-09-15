package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.utils.FileUpload
import java.nio.file.Path
import javax.annotation.CheckReturnValue
import javax.inject.Inject

internal class GetConfigCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val configFilePath: Path, // FIXME: qualifier
) : Command(name = CommandName.ofString("getconfig")) {

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()
		val config: Config = this.configSupplier.get()

		if (!(authorAsGuildMember.isAllowedToSeeConfig(config.botAdminUserIds))) {
			textChannel.sendMessage(config.strings.command.getConfig.insufficientPermissions)
				.complete()

			return
		}

		textChannel.sendFiles(FileUpload.fromData(this.configFilePath))
			.complete()
	}
}

@CheckReturnValue
private fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.complete()
}

@CheckReturnValue
private fun Member.isAllowedToSeeConfig(botAdminUserIds: Set<String>): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR) || (this.id in botAdminUserIds))
}
