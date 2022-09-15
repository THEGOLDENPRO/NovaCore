package net.zeeraa.novacore.commons.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentBuilder {
	protected TextComponent component;

	public TextComponentBuilder() {
		this(new TextComponent());
	}

	public TextComponentBuilder(String text) {
		this(new TextComponent(text));
	}

	public TextComponentBuilder(TextComponent component) {
		this.component = component;
	}

	public TextComponentBuilder setText(String text) {
		component.setText(text);
		return this;
	}

	public TextComponentBuilder setColor(ChatColor color) {
		component.setColor(color);
		return this;
	}

	public TextComponentBuilder setColor(ClickEvent clickEvent) {
		component.setClickEvent(clickEvent);
		return this;
	}

	public TextComponentBuilder setBold(boolean bold) {
		component.setBold(bold);
		return this;
	}

	public TextComponentBuilder setItalic(boolean italic) {
		component.setBold(italic);
		return this;
	}

	public TextComponentBuilder setUnderlined(boolean underlined) {
		component.setUnderlined(underlined);
		return this;
	}

	public TextComponentBuilder setStrikethrough(boolean strikethrough) {
		component.setStrikethrough(strikethrough);
		return this;
	}

	public TextComponentBuilder setObfuscated(boolean obfuscated) {
		component.setObfuscated(obfuscated);
		return this;
	}

	public TextComponentBuilder addExtra(String text) {
		component.addExtra(text);
		return this;
	}

	public TextComponentBuilder addExtra(BaseComponent component) {
		component.addExtra(component);
		return this;
	}

	public TextComponentBuilder addExtra(TextComponentBuilder builder) {
		return this.addExtra(builder.build());
	}

	public TextComponent build() {
		return component;
	}
}