/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive.impl;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import ai.nitro.bot4j.middle.repo.impl.StafefulBotProviderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.integration.alexa.domain.AlexaPlatformEnum;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.Session;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.DuplicateMessageFilter;
import ai.nitro.bot4j.middle.receive.MessageReceiver;
import ai.nitro.bot4j.middle.receive.SessionManager;

public class MessageReceiverImpl implements MessageReceiver {

	final static Logger LOG = LogManager.getLogger(MessageReceiverImpl.class);

	@Inject
	protected DuplicateMessageFilter duplicateMessageFilter;

	@Inject
	protected SessionManager sessionManager;

	@Inject
	protected StatefulBotProviderService botProviderService;

	protected void handleReceiveMessage(final ReceiveMessage receiveMessage, Long botId) {
		try {
			final Session session = sessionManager.getSession(receiveMessage);
			receiveMessage.setSession(session);

			final boolean isDuplicateMessage = duplicateMessageFilter.isDuplicate(receiveMessage);

			if (isDuplicateMessage) {
				LOG.info("ignoring duplicate message {}", receiveMessage);
			} else {
				Bot bot = botProviderService.getBot(botId);
				bot.onMessage(receiveMessage, botId);
			}
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	protected boolean isPlatformAsync(final Platform platform) {
		final boolean result = !AlexaPlatformEnum.ALEXA.equals(platform);
		return result;
	}

	@Override
	public void receive(final ReceiveMessage receiveMessage, Long botId) {
		final Participant sender = receiveMessage.getSender();
		final Platform platform = sender.getPlatform();
		final boolean isPlatformAsync = isPlatformAsync(platform);

		if (isPlatformAsync) {
			CompletableFuture.runAsync(() -> handleReceiveMessage(receiveMessage, botId));
		} else {
			handleReceiveMessage(receiveMessage, botId);
		}
	}
}
