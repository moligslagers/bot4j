/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.receive.webhook.impl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ai.nitro.bot4j.integration.slack.receive.SlackReceiveHandler;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackActionWebhook;

public class SlackActionWebhookImpl implements SlackActionWebhook {

	private final static Logger LOG = LogManager.getLogger(SlackActionWebhookImpl.class);

	@Inject
	protected SlackReceiveHandler slackReceiveHandler;

	protected void handleException(final Exception e) {
		LOG.error(e.getMessage(), e);
	}

	@Override
	public String post(final HttpServletRequest req, final HttpServletResponse res) {
		try {
			final String payload = req.getParameter("payload");
			final JsonParser jsonParser = new JsonParser();
			final JsonObject json = jsonParser.parse(payload).getAsJsonObject();

			slackReceiveHandler.handleAction(json);
		} catch (final Exception e) {
			handleException(e);
		}

		return "";
	}
}
