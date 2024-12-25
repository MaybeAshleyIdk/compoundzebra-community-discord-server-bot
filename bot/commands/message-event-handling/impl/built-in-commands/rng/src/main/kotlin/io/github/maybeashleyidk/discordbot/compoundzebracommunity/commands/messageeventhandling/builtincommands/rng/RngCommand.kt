package io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.builtincommands.rng

import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.messageeventhandling.Command
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.commands.name.CommandName
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.config.Config
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.configsupplier.ConfigSupplier
import io.github.maybeashleyidk.discordbot.compoundzebracommunity.utils.coroutinesjda.await
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParsePosition
import java.util.Locale
import kotlin.random.Random

public class RngCommand(
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

	override suspend fun execute(arguments: List<String>, catalystMessage: Message, textChannel: TextChannel) {
		val (minStr: String, maxStr: String) =
			when (arguments.size) {
				0 -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.missingMinAndMaxArguments).await()
					return
				}

				1 -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.missingMaxArgument).await()
					return
				}

				2 -> {
					arguments[0] to arguments[1]
				}

				else -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.excessArguments).await()
					return
				}
			}

		val guildLocale: Locale = catalystMessage.guild.locale.toLocale()

		val inputFormat: NumberFormat = NumberFormat.getNumberInstance(guildLocale)
		if (inputFormat is DecimalFormat) {
			inputFormat.isParseBigDecimal = true
		}

		val min: Long =
			when (val result: BoundNumberParsingResult = this.parseBoundNumber(inputFormat, minStr)) {
				is BoundNumberParsingResult.TooLongString -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minStringTooLong).await()
					return
				}

				is BoundNumberParsingResult.InvalidNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minInvalidNumber).await()
					return
				}

				is BoundNumberParsingResult.TooSmallNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minTooSmall).await()
					return
				}

				is BoundNumberParsingResult.TooBigNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minTooBig).await()
					return
				}

				is BoundNumberParsingResult.DecimalNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.minIsDecimal).await()
					return
				}

				is BoundNumberParsingResult.Success -> result.number
			}

		val max: Long =
			when (val result: BoundNumberParsingResult = this.parseBoundNumber(inputFormat, maxStr)) {
				is BoundNumberParsingResult.TooLongString -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxStringTooLong).await()
					return
				}

				is BoundNumberParsingResult.InvalidNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxInvalidNumber).await()
					return
				}

				is BoundNumberParsingResult.TooSmallNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxTooSmall).await()
					return
				}

				is BoundNumberParsingResult.TooBigNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxTooBig).await()
					return
				}

				is BoundNumberParsingResult.DecimalNumber -> {
					val config: Config = this.configSupplier.get()
					catalystMessage.reply(config.strings.command.rng.maxIsDecimal).await()
					return
				}

				is BoundNumberParsingResult.Success -> result.number
			}

		if (min == max) {
			val config: Config = this.configSupplier.get()
			catalystMessage.reply(config.strings.command.rng.minAndMaxAreEqual).await()
			return
		}

		if (min > max) {
			val config: Config = this.configSupplier.get()
			catalystMessage.reply(config.strings.command.rng.minGreaterThanMax).await()
			return
		}

		val random: Long = generateRandomLongValue(min, max)
		val outputFormat: NumberFormat = NumberFormat.getIntegerInstance(guildLocale)
		val config: Config = this.configSupplier.get()

		catalystMessage.reply(config.strings.command.rng.response(outputFormat.format(random))).await()
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
