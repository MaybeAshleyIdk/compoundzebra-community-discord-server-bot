package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.quoted
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel

@JvmInline
public value class Plaintext(private val string: String) : TextElement {

	private companion object {

		const val ESCAPE_CHARS: String = "<*_|#-`/[(:\""
	}

	override fun toRawString(): String {
		return Companion.ESCAPE_CHARS
			.replace("\\", "\\\\")
			.fold(this.string) { escapingString: String, charNeedsEscaping: Char ->
				escapingString.replace(charNeedsEscaping.toString(), "\\$charNeedsEscaping")
			}
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		return this.string
	}

	override fun toString(): String {
		val quotedString: String = this.string
			.quoted()
			.replace("\n", "\\n")

		return "Plaintext(string = $quotedString)"
	}
}
