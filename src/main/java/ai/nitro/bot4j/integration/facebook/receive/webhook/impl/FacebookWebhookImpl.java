/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.receive.webhook.impl;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.google.common.io.CharStreams;
import com.restfb.DefaultJsonMapper;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.MessagingParticipant;

import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveHandler;
import ai.nitro.bot4j.integration.facebook.receive.webhook.FacebookWebhook;

public class FacebookWebhookImpl implements FacebookWebhook {

	private final static Logger LOG = LogManager.getLogger(FacebookWebhookImpl.class);

	@Inject
	protected FacebookReceiveHandler facebookMessageHandler;

	@Override
	public String get(final HttpServletRequest req, final HttpServletResponse res) {
		String result = "";

		try {
			result = handleChallenge(req);
		} catch (final Exception e) {
			handleException(e);
		}

		return result;
	}

	protected String handleChallenge(final HttpServletRequest req) throws IOException {
		final String hubMode = req.getParameter("hub.mode");
		final String hubChallenge = req.getParameter("hub.challenge");
		final String result;

		if (isGetModeSubscribe(hubMode, hubChallenge)) {
			result = hubChallenge;
		} else {
			result = "";
		}

		return result;
	}

	protected void handleException(final Exception e) {
		LOG.error(e.getMessage(), e);
	}

	protected void handleMessagingItem(final MessagingItem messagingItem, Long botId) {
		final MessagingParticipant sender = messagingItem.getSender();

		if (sender == null || Strings.isBlank(sender.getId())) {
			LOG.warn("Ignoring message with empty sender");
		} else {
			facebookMessageHandler.handleMessagingItem(messagingItem, botId);
		}
	}

	protected void handleWebhookEntry(final WebhookEntry webhookEntry, Long botId) {
		for (final MessagingItem messagingItem : webhookEntry.getMessaging()) {
			if (messagingItem != null) {
				handleMessagingItem(messagingItem, botId);
			}
		}
	}

	protected boolean isGetModeSubscribe(final String hubMode, final String hubChallenge) {
		return Strings.isNotBlank(hubMode) && Strings.isNotBlank(hubChallenge) && hubMode.equals("subscribe");
	}

	@Override
	public String post(final HttpServletRequest req, final HttpServletResponse res) {
		final String result = "";


		try {
			final String body = CharStreams.toString(req.getReader());
			final DefaultJsonMapper mapper = new DefaultJsonMapper();
			final WebhookObject webhookObject = mapper.toJavaObject(body, WebhookObject.class);

			for (final WebhookEntry webhookEntry : webhookObject.getEntryList()) {
				handleWebhookEntry(webhookEntry, (long)0);
			}
		} catch (final Exception e) {
			handleException(e);
		}

		return result;
	}
}
