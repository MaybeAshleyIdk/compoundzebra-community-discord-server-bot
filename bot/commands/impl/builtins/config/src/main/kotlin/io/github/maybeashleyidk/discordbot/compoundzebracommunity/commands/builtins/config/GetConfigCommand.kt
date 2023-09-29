package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutines.jda.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.utils.FileUpload
import java.nio.file.Path
import javax.inject.Inject

internal class GetConfigCommand @Suppress("ktlint:standard:annotation") @Inject constructor(
	private val configSupplier: ConfigSupplier,
	private val configFilePath: Path, // FIXME: qualifier
) : Command(name = CommandName.ofString("getconfig")) {

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val authorAsGuildMember: Member = catalystMessage.getAuthorAsGuildMember()
		val config: Config = this.configSupplier.get()

		if (!(authorAsGuildMember.isAllowedToSeeConfig(config.botAdminUserIds))) {
			textChannel.sendMessage(config.strings.command.getConfig.insufficientPermissions).await()
			return
		}

		textChannel.sendFiles(FileUpload.fromData(this.configFilePath)).await()
	}
}

private suspend fun Message.getAuthorAsGuildMember(): Member {
	return this.guild.retrieveMemberById(this.author.idLong)
		.await()
}

private fun Member.isAllowedToSeeConfig(botAdminUserIds: Set<String>): Boolean {
	return (this.isOwner || this.hasPermission(Permission.ADMINISTRATOR) || (this.id in botAdminUserIds))
}
