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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendPhoto;

import ai.nitro.bot4j.integration.telegram.send.TelegramSendInlineKeyboardFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.list.ListSendElement;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.ListSendPayload;

public class ListRuleImpl extends AbstractTelegramSendRuleImpl {

	private final static Logger LOG = LogManager.getLogger(ListRuleImpl.class);

	@Inject
	protected TelegramSendInlineKeyboardFactory telegramSendInlineKeyboardFactory;

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.LIST, sendMessage);

	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final ListSendPayload listSendPayload = sendMessage.getPayloadWithType(ListSendPayload.class);
		final String recipient = sendMessage.getRecipient().getId();

		for (final ListSendElement listSendElement : listSendPayload.getListElements()) {
			sendListElement(recipient, listSendElement);

		}
	}

	protected void sendButtonElement(final String recipient, final ListSendElement listSendElement) {
		final AbstractSendButton abstractButton = listSendElement.getButton();

		final List<InlineKeyboardButton> buttonList = new ArrayList<InlineKeyboardButton>();

		buttonList.add(telegramSendInlineKeyboardFactory.createInlineKeyboard(abstractButton));
		InlineKeyboardButton[] inlineKeyboardButtons = new InlineKeyboardButton[buttonList.size()];
		inlineKeyboardButtons = buttonList.toArray(inlineKeyboardButtons);
		final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(inlineKeyboardButtons);

		String title = listSendElement.getTitle();

		if (Strings.isBlank(title)) {
			title = "undefined title of buttonSendPayload";
		}

		final String boldTitle = "*" + title + "*";
		final com.pengrad.telegrambot.request.SendMessage sendMessageTelegram = new com.pengrad.telegrambot.request.SendMessage(
				recipient, boldTitle).replyMarkup(inlineKeyboardMarkup).parseMode(ParseMode.Markdown);
		super.execute(sendMessageTelegram, recipient);
	}

	protected void sendListElement(final String recipient, final ListSendElement listSendElement) {
		if (!Strings.isBlank(listSendElement.getTitle())) {
			final String title = "*" + listSendElement.getTitle() + "*";
			final com.pengrad.telegrambot.request.SendMessage sendMessageTelegram = new com.pengrad.telegrambot.request.SendMessage(
					recipient, title).parseMode(ParseMode.Markdown);

			super.execute(sendMessageTelegram, recipient);
		}

		if (!Strings.isBlank(listSendElement.getSubTitle())) {
			final String subTitle = listSendElement.getSubTitle();
			final com.pengrad.telegrambot.request.SendMessage sendMessageTelegram = new com.pengrad.telegrambot.request.SendMessage(
					recipient, subTitle);

			super.execute(sendMessageTelegram, recipient);
		}

		if (!Strings.isBlank(listSendElement.getImageUrl())) {
			sendPhotoElement(recipient, listSendElement);
		}

		if (listSendElement.getButton() != null) {
			sendButtonElement(recipient, listSendElement);
		}
	}

	protected void sendPhotoElement(final String recipient, final ListSendElement listSendElement) {
		final SendPhoto sendPhotoTelegram = new SendPhoto(recipient, listSendElement.getImageUrl());
		final String title = listSendElement.getTitle();

		if (!Strings.isBlank(title)) {
			sendPhotoTelegram.caption(title);
		}

		LOG.info("sending image {} to recipient {}", sendPhotoTelegram, recipient);
		client.execute(sendPhotoTelegram);
	}
}
