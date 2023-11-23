package io.github.maybeashleyidk.discordbot.compoundzebracommunity.selftimeout

import net.dv8tion.jda.api.entities.Member

public interface SelfTimeoutService {

	public suspend fun timeOutMember(member: Member)
}
