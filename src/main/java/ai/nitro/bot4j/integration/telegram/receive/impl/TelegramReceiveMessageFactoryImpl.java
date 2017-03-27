/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.receive.impl;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.pengrad.telegrambot.model.Audio;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Sticker;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Voice;

import ai.nitro.bot4j.integration.telegram.domain.TelegramPlatformEnum;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveMessageFactory;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceivePayloadFactory;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;

public class TelegramReceiveMessageFactoryImpl implements TelegramReceiveMessageFactory {

	private static final String GET_STARTED = "GET_STARTED";

	final static Logger LOG = LogManager.getLogger(TelegramReceiveMessageFactoryImpl.class);

	@Inject
	protected TelegramReceivePayloadFactory telegramReceivePayloadFactory;

	@Override
	public ReceiveMessage createReceiveMessage(final Update update) {
		final ReceiveMessage result = new ReceiveMessage();
		result.setNativePayload(TelegramPlatformEnum.TELEGRAM, update);

		if (update.message() != null) {
			handleMessage(update.message(), result);
			handleSender(update.message().chat().id().intValue(), result);
		}

		if (update.callbackQuery() != null) {
			handlePostback(update.callbackQuery(), result);
			handleSender(update.callbackQuery().from().id(), result);
		}

		return result;
	}

	protected void handleAudio(final Audio audio, final ReceiveMessage result) {
		final UrlAttachmentReceivePayload urlAttachmentReceivePayload = telegramReceivePayloadFactory
				.createAudio(audio);
		result.addPayload(urlAttachmentReceivePayload);
	}

	protected void handleDocument(final Message message, final ReceiveMessage result) {
		final UrlAttachmentReceivePayload urlAttachmentReceivePayload = telegramReceivePayloadFactory
				.createDocument(message);
		result.addPayload(urlAttachmentReceivePayload);
	}

	protected void handleMessage(final Message message, final ReceiveMessage result) {
		final String messageId = String.valueOf(message.messageId());
		result.setMessageId(messageId);

		if (message.audio() != null) {
			handleAudio(message.audio(), result);
		}

		if (message.document() != null) {
			handleDocument(message, result);
		}

		if (message.photo() != null) {
			handlePhoto(message, result);
		}

		if (message.sticker() != null) {
			handleSticker(message.sticker(), result);
		}

		if (!Strings.isBlank(message.text())) {
			if ("/start".equals(message.text())) {
				handleStartMessage(message.text(), result);
			} else {
				handleText(message.text(), result);
			}
		}

		if (message.video() != null) {
			handleVideo(message, result);
		}

		if (message.voice() != null) {
			handleVoice(message.voice(), result);
		}
	}

	protected void handlePhoto(final Message message, final ReceiveMessage result) {
		final UrlAttachmentReceivePayload urlAttachmentReceivePayload = telegramReceivePayloadFactory
				.createPhoto(message);
		result.addPayload(urlAttachmentReceivePayload);
	}

	protected void handlePostback(final CallbackQuery callbackQuery, final ReceiveMessage result) {
		final PostbackReceivePayload postback = telegramReceivePayloadFactory.createPostback(callbackQuery);
		result.addPayload(postback);
	}

	protected void handleSender(final Integer sender, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(TelegramPlatformEnum.TELEGRAM);
		participant.setId(String.valueOf(sender));
		result.setSender(participant);
	}

	private void handleStartMessage(final String text, final ReceiveMessage result) {
		final PostbackReceivePayload postbackReceivePayload = new PostbackReceivePayload();
		postbackReceivePayload.setName(GET_STARTED);

		result.addPayload(postbackReceivePayload);
	}

	protected void handleSticker(final Sticker sticker, final ReceiveMessage result) {
		final UrlAttachmentReceivePayload urlAttachmentReceivePayload = telegramReceivePayloadFactory
				.createSticker(sticker);
		result.addPayload(urlAttachmentReceivePayload);
	}

	protected void handleText(final String text, final ReceiveMessage result) {
		final TextReceivePayload textPayload = telegramReceivePayloadFactory.createTextPayload(text);
		result.addPayload(textPayload);
	}

	protected void handleVideo(final Message message, final ReceiveMessage result) {
		final UrlAttachmentReceivePayload urlAttachmentReceivePayload = telegramReceivePayloadFactory
				.createVideo(message);
		result.addPayload(urlAttachmentReceivePayload);
	}

	protected void handleVoice(final Voice voice, final ReceiveMessage result) {
		final UrlAttachmentReceivePayload urlAttachmentReceivePayload = telegramReceivePayloadFactory
				.createVoice(voice);
		result.addPayload(urlAttachmentReceivePayload);
	}
}
