package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.config

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.utils.FileUpload
import java.nio.file.Path

public class GetConfigCommand(
	private val configSupplier: ConfigSupplier,
	private val configFilePath: Path,
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
