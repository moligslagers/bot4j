/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.receive.webhook.impl;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.io.CharStreams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ai.nitro.bot4j.integration.slack.receive.SlackReceiveHandler;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackEventWebhook;

public class SlackEventWebhookImpl implements SlackEventWebhook {

	private static final String BOT_ID = "bot_id";

	private static final String BOT_MESSAGE = "bot_message";

	private static final String CHALLENGE = "challenge";

	private static final String EVENT = "event";

	private static final String EVENT_CALLBACK = "event_callback";

	private final static Logger LOG = LogManager.getLogger(SlackEventWebhookImpl.class);

	private static final String MESSAGE = "message";

	private static final String SUBTYPE = "subtype";

	private static final String TYPE = "type";

	private static final String URL_VERIFICATION = "url_verification";

	@Inject
	protected SlackReceiveHandler slackReceiveHandler;

	protected void handleEvent(final JsonObject jsonReq, final HttpServletResponse res) {
		final JsonObject eventJsonObject = jsonReq.get(EVENT).getAsJsonObject();

		if (!eventJsonObject.has(TYPE)) {
			LOG.warn("no type in event JSON");
		} else if (!MESSAGE.equals(eventJsonObject.get(TYPE).getAsString())) {
			LOG.warn("no message in event JSON");
		} else if (eventJsonObject.has(BOT_ID)) {
			LOG.info("ignoring bot message");
		} else if (eventJsonObject.has(SUBTYPE) && BOT_MESSAGE.equals(eventJsonObject.get(SUBTYPE).getAsString())) {
			LOG.info("ignoring bot message");
		} else {
			slackReceiveHandler.handleEvent(eventJsonObject);
		}
	}

	protected void handleException(final Exception e) {
		LOG.error(e.getMessage(), e);
	}

	protected String handleUrlVerification(final JsonObject jsonReq, final HttpServletResponse res) throws IOException {
		res.setContentType("application/x-www-form-urlencoded");

		final JsonElement jsonElementChallenge = jsonReq.get(CHALLENGE);
		final String challenge = jsonElementChallenge.getAsString();

		LOG.info("Responding challenge {}", challenge);

		return challenge;
	}

	@Override
	public String post(final HttpServletRequest req, final HttpServletResponse res) {
		String result = "";

		try {
			final String body = CharStreams.toString(req.getReader());
			final JsonParser jsonParser = new JsonParser();
			final JsonObject jsonReq = jsonParser.parse(body).getAsJsonObject();

			if (!jsonReq.has(TYPE)) {
				LOG.warn("no type in JSON");
			} else {
				final JsonElement typeJsonElement = jsonReq.get(TYPE);
				final String type = typeJsonElement.getAsString();

				switch (type) {
				case URL_VERIFICATION:
					result = handleUrlVerification(jsonReq, res);
					break;
				case EVENT_CALLBACK:
					handleEvent(jsonReq, res);
					break;
				default:
					LOG.info("unknown type {}", type);
					break;
				}
			}
		} catch (final Exception e) {
			handleException(e);
		}

		return result;
	}
}
