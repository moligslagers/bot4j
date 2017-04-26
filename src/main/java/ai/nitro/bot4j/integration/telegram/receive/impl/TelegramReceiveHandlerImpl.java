/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.receive.impl;

import javax.inject.Inject;

import com.pengrad.telegrambot.model.Update;

import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveHandler;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveMessageFactory;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.MessageReceiver;

public class TelegramReceiveHandlerImpl implements TelegramReceiveHandler {

	@Inject
	protected MessageReceiver messageReceiver;

	@Inject
	protected TelegramReceiveMessageFactory telegramReceiveMessageFactory;

	@Override
	public void handleUpdateMessage(final Update update, Long botId) {
		final ReceiveMessage receiveMessage = telegramReceiveMessageFactory.createReceiveMessage(update);

		if (receiveMessage != null) {
			messageReceiver.receive(receiveMessage, botId);
		}
	}
}
