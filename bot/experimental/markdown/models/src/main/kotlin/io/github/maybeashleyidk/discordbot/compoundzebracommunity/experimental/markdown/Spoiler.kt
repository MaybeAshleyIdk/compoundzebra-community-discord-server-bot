package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import java.time.Instant
import kotlin.random.Random

public sealed interface SpoilerElement : MarkdownThing

@JvmInline
public value class Spoiler(private val elements: List<SpoilerElement>) : MarkdownElement, MarkdownThing {

	private companion object {

		val random: Random = Random(Instant.now().epochSecond)
	}

	init {
//		this.elements.
	}

	override fun toRawString(): String {
		val inside: String = this.elements
			.joinToString(
				separator = "",
				transform = SpoilerElement::toRawString,
			)

		return "||$inside||"
	}

	override suspend fun toDisplayString(channelContext: MessageChannel, showSpoiler: Boolean): String {
		val displayString: String = this.toDisplayString(channelContext)

		if (showSpoiler) {
			return displayString
		}

		return displayString
			.lineSequence()
			.map { line: String ->
				// generating a number in the range of [1.25, 1.75)
				val divisor: Float = (1.5f + ((Companion.random.nextFloat() / 2f) - 0.25f))
				"█".repeat((line.trim().length / divisor).toInt())
			}
			.joinToString(separator = "\n")
	}

	private suspend fun toDisplayString(channelContext: MessageChannel): String {
		return this.elements
			.map { element: SpoilerElement ->
				element.toDisplayString(channelContext, showSpoiler = true)
			}
			.joinToString(separator = "")
	}
}
