/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send.impl;

import javax.inject.Inject;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import ai.nitro.bot4j.integration.telegram.send.TelegramSendInlineKeyboardFactory;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton.Type;
import ai.nitro.bot4j.middle.domain.send.button.PostbackSendButton;
import ai.nitro.bot4j.middle.domain.send.button.WebSendButton;
import ai.nitro.bot4j.middle.payload.PostbackPayload;
import ai.nitro.bot4j.middle.payload.PostbackPayloadService;

public class TelegramSendInlineKeyboardFactoryImpl implements TelegramSendInlineKeyboardFactory {

	@Inject
	protected PostbackPayloadService postbackPayloadService;

	@Override
	public InlineKeyboardButton createInlineKeyboard(final AbstractSendButton abstractSendButton) {
		final Type buttonType = abstractSendButton.getType();
		final InlineKeyboardButton result;

		switch (buttonType) {
		case WEB_BUTTON:
			result = creatWebButton((WebSendButton) abstractSendButton);
			break;
		case POSTBACK_BUTTON:
			result = createPostbackButton((PostbackSendButton) abstractSendButton);
			break;
		default:
			result = null;
		}

		return result;
	}

	protected InlineKeyboardButton createPostbackButton(final PostbackSendButton postbackSendButton) {
		final String title = postbackSendButton.getTitle();
		final String name = postbackSendButton.getName();
		final String[] payload = postbackSendButton.getPayload();

		final PostbackPayload postbackPayload = new PostbackPayload();
		postbackPayload.name = name;
		postbackPayload.payload = payload;

		final String serializedPayload = postbackPayloadService.serialize(postbackPayload);
		final InlineKeyboardButton result = new InlineKeyboardButton(title).callbackData(serializedPayload);

		return result;
	}

	protected InlineKeyboardButton creatWebButton(final WebSendButton webSendButton) {
		final String title = webSendButton.getTitle();
		final String webUrl = webSendButton.getUrl();

		final InlineKeyboardButton result = new InlineKeyboardButton(title).url(webUrl);
		return result;
	}
}
