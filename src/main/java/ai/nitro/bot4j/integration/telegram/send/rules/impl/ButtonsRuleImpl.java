/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;

import ai.nitro.bot4j.integration.telegram.send.TelegramSendInlineKeyboardFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.ButtonsSendPayload;

public class ButtonsRuleImpl extends AbstractTelegramSendRuleImpl {

	@Inject
	protected TelegramSendInlineKeyboardFactory telegramSendInlineKeyboardFactory;

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.BUTTONS, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final ButtonsSendPayload buttonsSendPayload = sendMessage.getPayloadWithType(ButtonsSendPayload.class);
		final List<InlineKeyboardButton> buttonList = new ArrayList<InlineKeyboardButton>();

		for (final AbstractSendButton button : buttonsSendPayload.getButtons()) {
			buttonList.add(telegramSendInlineKeyboardFactory.createInlineKeyboard(button));
		}

		InlineKeyboardButton[] inlineKeyboardButtons = new InlineKeyboardButton[buttonList.size()];
		inlineKeyboardButtons = buttonList.toArray(inlineKeyboardButtons);
		final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(inlineKeyboardButtons);

		String title = buttonsSendPayload.getTitle();

		if (Strings.isBlank(title)) {
			title = "undefined title of buttonSendPayload";
		}

		final String boldTitle = "*" + title + "*";
		final String recipient = sendMessage.getRecipient().getId();

		final com.pengrad.telegrambot.request.SendMessage sendMessageTelegram = new com.pengrad.telegrambot.request.SendMessage(
				recipient, boldTitle).replyMarkup(inlineKeyboardMarkup).parseMode(ParseMode.Markdown);
		super.execute(sendMessageTelegram, recipient);
	}

}
