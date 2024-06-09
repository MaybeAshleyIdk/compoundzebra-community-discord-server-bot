package io.github.maybeashleyidk.discordbot.compoundzebracommunity.experimental.markdown

public sealed interface ItalicElement : MarkdownThing

public sealed interface Italic : MarkdownElement, MarkdownThing {
}

public interface ItalicAsterisk : Italic
public interface ItalicUnderscore : Italic
