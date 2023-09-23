package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.builtins.rng

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.supplier.ConfigSupplier
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.interactions.DiscordLocale
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

public class RngCommand @Inject constructor(
	private val configSupplier: ConfigSupplier,
) : Command(name = CommandName.ofString("rng")) {

	private companion object {

		const val BOUND_NUMBER_STRING_LENGTH_LIMIT: Int = 64

		val NUMBER_FORMAT_US: NumberFormat = NumberFormat.getNumberInstance(Locale.US)
		val NUMBER_FORMAT_ROOT: NumberFormat = NumberFormat.getNumberInstance(Locale.ROOT)
	}

	private sealed class BoundNumberParsingResult {
		data object TooLongString : BoundNumberParsingResult()
		data object InvalidNumber : BoundNumberParsingResult()
		data object TooSmallNumber : BoundNumberParsingResult()
		data object TooBigNumber : BoundNumberParsingResult()
		data object DecimalNumber : BoundNumberParsingResult()

		data class Success(val number: Long) : BoundNumberParsingResult()
	}

	override fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val (minStr: String, maxStr: String) =
			when (arguments.size) {
				0 -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.missingMinAndMaxArguments)
						.complete()
					return
				}

				1 -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.missingMaxArgument)
						.complete()
					return
				}

				2 -> {
					arguments[0] to arguments[1]
				}

				else -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.excessArguments)
						.complete()
					return
				}
			}

		val serverLocale: Locale = catalystMessage.guild.locale.toJvmLocale()

		val inputFormat: NumberFormat = NumberFormat.getNumberInstance(serverLocale)
		if (inputFormat is DecimalFormat) {
			inputFormat.isParseBigDecimal = true
		}

		val min: Long =
			when (val result: BoundNumberParsingResult = this.parseBoundNumber(inputFormat, minStr)) {
				is BoundNumberParsingResult.TooLongString -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minStringTooLong)
						.complete()
					return
				}

				is BoundNumberParsingResult.InvalidNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minInvalidNumber)
						.complete()
					return
				}

				is BoundNumberParsingResult.TooSmallNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minTooSmall)
						.complete()
					return
				}

				is BoundNumberParsingResult.TooBigNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minTooBig)
						.complete()
					return
				}

				is BoundNumberParsingResult.DecimalNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minIsDecimal)
						.complete()
					return
				}

				is BoundNumberParsingResult.Success -> result.number
			}

		val max: Long =
			when (val result: BoundNumberParsingResult = this.parseBoundNumber(inputFormat, maxStr)) {
				is BoundNumberParsingResult.TooLongString -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxStringTooLong)
						.complete()
					return
				}

				is BoundNumberParsingResult.InvalidNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxInvalidNumber)
						.complete()
					return
				}

				is BoundNumberParsingResult.TooSmallNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxTooSmall)
						.complete()
					return
				}

				is BoundNumberParsingResult.TooBigNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxTooBig)
						.complete()
					return
				}

				is BoundNumberParsingResult.DecimalNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxIsDecimal)
						.complete()
					return
				}

				is BoundNumberParsingResult.Success -> result.number
			}

		if (min == max) {
			val config: Config = this.configSupplier.get()
			catalystMessage.reply(config.strings.command.rng.minAndMaxAreEqual)
				.complete()
			return
		}

		if (min > max) {
			val config: Config = this.configSupplier.get()
			catalystMessage.reply(config.strings.command.rng.minGreaterThanMax)
				.complete()
			return
		}

		val random: Long = generateRandomLongValue(min, max)
		val outputFormat: NumberFormat = NumberFormat.getIntegerInstance(serverLocale)
		val config: Config = this.configSupplier.get()

		catalystMessage.reply(config.strings.command.rng.response(outputFormat.format(random)))
			.complete()
	}

	private fun parseBoundNumber(inputFormat: NumberFormat, source: String): BoundNumberParsingResult {
		@Suppress("RemoveRedundantQualifierName")
		if (source.length > RngCommand.BOUND_NUMBER_STRING_LENGTH_LIMIT) {
			return BoundNumberParsingResult.TooLongString
		}

		val preparedSource: String = source.removeChar('_')

		@Suppress("RemoveRedundantQualifierName")
		val decimal: BigDecimal = inputFormat.parseEntire(preparedSource)
			?: RngCommand.NUMBER_FORMAT_US.parseEntire(preparedSource)
			?: RngCommand.NUMBER_FORMAT_ROOT.parseEntire(preparedSource)
			?: return BoundNumberParsingResult.InvalidNumber

		if (decimal < BigDecimal(Long.MIN_VALUE)) {
			return BoundNumberParsingResult.TooSmallNumber
		}

		if (decimal > BigDecimal(Long.MAX_VALUE)) {
			return BoundNumberParsingResult.TooBigNumber
		}

		val integer: BigInteger = decimal.toBigIntegerExactOrNull()
			?: return BoundNumberParsingResult.DecimalNumber

		return BoundNumberParsingResult.Success(integer.longValueExact())
	}
}

private fun String.removeChar(ch: Char): String {
	return this.replace(ch.toString(), "")
}

private fun DiscordLocale.toJvmLocale(): Locale {
	return (Locale.forLanguageTag(this.locale) ?: Locale.ROOT)
}

private fun NumberFormat.parseEntire(source: String): BigDecimal? {
	if (source.isEmpty()) {
		return null
	}

	val position = ParsePosition(0)
	val parsedNumber: Number = this.parse(source, position)
		?: return null

	if ((position.errorIndex >= 0) || (position.index <= source.lastIndex)) {
		return null
	}

	return when (parsedNumber) {
		is Long -> BigDecimal(parsedNumber)
		is Double -> BigDecimal(parsedNumber)
		is BigDecimal -> parsedNumber
		else -> BigDecimal(parsedNumber.toDouble())
	}
}

private fun BigDecimal.toBigIntegerExactOrNull(): BigInteger? {
	return try {
		this.toBigIntegerExact()
	} catch (e: ArithmeticException) {
		null
	}
}

private fun generateRandomLongValue(min: Long, max: Long): Long {
	if (max != Long.MAX_VALUE) {
		return Random.nextLong(min, (max + 1))
	}

	if (min == Long.MIN_VALUE) {
		return Random.nextLong()
	}

	return (Random.nextLong((min - 1), max) + 1)
}
