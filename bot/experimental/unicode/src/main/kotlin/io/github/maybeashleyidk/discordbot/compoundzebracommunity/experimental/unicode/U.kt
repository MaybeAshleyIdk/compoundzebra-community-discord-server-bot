package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.unicode

public object U {

	public operator fun plus(codePointInt: Int): CodePoint {
		return CodePoint.ofInt(codePointInt)
	}
}
