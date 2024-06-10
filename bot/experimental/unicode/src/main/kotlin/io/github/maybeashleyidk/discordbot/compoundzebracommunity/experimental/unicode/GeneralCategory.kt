package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

import java.lang.Character.COMBINING_SPACING_MARK
import java.lang.Character.CONNECTOR_PUNCTUATION
import java.lang.Character.CURRENCY_SYMBOL
import java.lang.Character.DASH_PUNCTUATION
import java.lang.Character.DECIMAL_DIGIT_NUMBER
import java.lang.Character.ENCLOSING_MARK
import java.lang.Character.END_PUNCTUATION
import java.lang.Character.FINAL_QUOTE_PUNCTUATION
import java.lang.Character.INITIAL_QUOTE_PUNCTUATION
import java.lang.Character.LETTER_NUMBER
import java.lang.Character.LINE_SEPARATOR
import java.lang.Character.LOWERCASE_LETTER
import java.lang.Character.MATH_SYMBOL
import java.lang.Character.MODIFIER_LETTER
import java.lang.Character.MODIFIER_SYMBOL
import java.lang.Character.NON_SPACING_MARK
import java.lang.Character.OTHER_LETTER
import java.lang.Character.OTHER_NUMBER
import java.lang.Character.OTHER_PUNCTUATION
import java.lang.Character.OTHER_SYMBOL
import java.lang.Character.PARAGRAPH_SEPARATOR
import java.lang.Character.SPACE_SEPARATOR
import java.lang.Character.START_PUNCTUATION
import java.lang.Character.TITLECASE_LETTER
import java.lang.Character.UPPERCASE_LETTER
import kotlin.enums.EnumEntries

public sealed interface GeneralCategory {

	public operator fun contains(codePoint: CodePoint): Boolean

	public enum class Letter(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		UPPERCASE(UPPERCASE_LETTER),
		LOWERCASE(LOWERCASE_LETTER),
		TITLECASE(TITLECASE_LETTER),
		MODIFIER(MODIFIER_LETTER),
		OTHER(OTHER_LETTER),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}

	public enum class Mark(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		NONSPACING(NON_SPACING_MARK),
		SPACING_COMBINING(COMBINING_SPACING_MARK),
		ENCLOSING(ENCLOSING_MARK),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}

	public enum class Number(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		DECIMAL_DIGIT(DECIMAL_DIGIT_NUMBER),
		LETTER(LETTER_NUMBER),
		OTHER(OTHER_NUMBER),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}

	public enum class Punctuation(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		CONNECTOR(CONNECTOR_PUNCTUATION),
		DASH(DASH_PUNCTUATION),
		OPEN(START_PUNCTUATION),
		CLOSE(END_PUNCTUATION),
		INITIAL_QUOTE(INITIAL_QUOTE_PUNCTUATION),
		FINAL_QUOTE(FINAL_QUOTE_PUNCTUATION),
		OTHER(OTHER_PUNCTUATION),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}

	public enum class Symbol(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		MATH(MATH_SYMBOL),
		CURRENCY(CURRENCY_SYMBOL),
		MODIFIER(MODIFIER_SYMBOL),
		OTHER(OTHER_SYMBOL),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}

	public enum class Separator(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		SPACE(SPACE_SEPARATOR),
		LINE(LINE_SEPARATOR),
		PARAGRAPH(PARAGRAPH_SEPARATOR),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}

	public enum class Other(jvmCatValue: Byte) : GeneralCategory by JvmGeneralCategoryDelegate(jvmCatValue) {
		CONTROL(Character.CONTROL),
		FORMAT(Character.FORMAT),
		SURROGATE(Character.SURROGATE),
		PRIVATE_USE(Character.PRIVATE_USE),
		NOT_ASSIGNED(Character.UNASSIGNED),
		;

		public companion object : GeneralCategory by MajorCategoryEnumCompanionDelegate(entries)
	}
}

public operator fun GeneralCategory.contains(scalarValue: ScalarValue): Boolean {
	return this.contains(scalarValue.toCodePoint())
}

public fun CodePoint.determineCategory(): GeneralCategory {
	return when (Character.getType(this.integer).toByte()) {
		UPPERCASE_LETTER -> GeneralCategory.Letter.UPPERCASE
		LOWERCASE_LETTER -> GeneralCategory.Letter.LOWERCASE
		TITLECASE_LETTER -> GeneralCategory.Letter.TITLECASE
		MODIFIER_LETTER -> GeneralCategory.Letter.MODIFIER
		OTHER_LETTER -> GeneralCategory.Letter.OTHER

		NON_SPACING_MARK -> GeneralCategory.Mark.NONSPACING
		COMBINING_SPACING_MARK -> GeneralCategory.Mark.SPACING_COMBINING
		ENCLOSING_MARK -> GeneralCategory.Mark.ENCLOSING

		DECIMAL_DIGIT_NUMBER -> GeneralCategory.Number.DECIMAL_DIGIT
		LETTER_NUMBER -> GeneralCategory.Number.LETTER
		OTHER_NUMBER -> GeneralCategory.Number.OTHER

		CONNECTOR_PUNCTUATION -> GeneralCategory.Punctuation.CONNECTOR
		DASH_PUNCTUATION -> GeneralCategory.Punctuation.DASH
		START_PUNCTUATION -> GeneralCategory.Punctuation.OPEN
		END_PUNCTUATION -> GeneralCategory.Punctuation.CLOSE
		INITIAL_QUOTE_PUNCTUATION -> GeneralCategory.Punctuation.INITIAL_QUOTE
		FINAL_QUOTE_PUNCTUATION -> GeneralCategory.Punctuation.FINAL_QUOTE
		OTHER_PUNCTUATION -> GeneralCategory.Punctuation.OTHER

		MATH_SYMBOL -> GeneralCategory.Symbol.MATH
		CURRENCY_SYMBOL -> GeneralCategory.Symbol.CURRENCY
		MODIFIER_SYMBOL -> GeneralCategory.Symbol.MODIFIER
		OTHER_SYMBOL -> GeneralCategory.Symbol.OTHER

		SPACE_SEPARATOR -> GeneralCategory.Separator.SPACE
		LINE_SEPARATOR -> GeneralCategory.Separator.LINE
		PARAGRAPH_SEPARATOR -> GeneralCategory.Separator.PARAGRAPH

		Character.CONTROL -> GeneralCategory.Other.CONTROL
		Character.FORMAT -> GeneralCategory.Other.FORMAT
		Character.SURROGATE -> GeneralCategory.Other.SURROGATE
		Character.PRIVATE_USE -> GeneralCategory.Other.PRIVATE_USE
		Character.UNASSIGNED -> GeneralCategory.Other.NOT_ASSIGNED

		else -> error("Unknown character category")
	}
}

public fun ScalarValue.determineCategory(): GeneralCategory {
	return this.toCodePoint().determineCategory()
}

private class JvmGeneralCategoryDelegate(jvmCategoryValue: Byte) : GeneralCategory {

	private val jvmCategoryValue: Int = jvmCategoryValue.toInt()

	override fun contains(codePoint: CodePoint): Boolean {
		return (Character.getType(codePoint.integer) == this.jvmCategoryValue)
	}
}

private open class MajorCategoryEnumCompanionDelegate<E>(
	private val entries: EnumEntries<E>,
) : GeneralCategory where E : Enum<E>,
                          E : GeneralCategory {

	final override fun contains(codePoint: CodePoint): Boolean {
		return this.entries
			.any { entry: E ->
				entry.contains(codePoint)
			}
	}
}
