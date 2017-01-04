/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.receive.impl;

import javax.inject.Inject;

import com.google.gson.JsonObject;

import ai.nitro.bot4j.integration.slack.domain.SlackPlatformEnum;
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveEventMessageFactory;
import ai.nitro.bot4j.integration.slack.receive.SlackReceivePayloadFactory;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;

public class SlackReceiveEventMessageFactoryImpl implements SlackReceiveEventMessageFactory {

	private static final String CHANNEL = "channel";

	private static final String EVENT_TS = "event_ts";

	private static final String TEXT = "text";

	private static final String USER = "user";

	@Inject
	protected SlackReceivePayloadFactory slackReceivePayloadFactory;

	@Override
	public ReceiveMessage createReceiveMessage(final JsonObject json) {
		final ReceiveMessage result = new ReceiveMessage();
		result.setNativePayload(SlackPlatformEnum.SLACK, json);

		final String eventTs = json.get(EVENT_TS).getAsString();
		final String username = json.get(USER).getAsString();
		final String messageId = eventTs + "-" + username;
		result.setMessageId(messageId);

		handleSender(json, result);
		handleRecipient(json, result);

		if (json.has(TEXT)) {
			handleText(json.get(TEXT).getAsString(), result);
		}

		return result;
	}

	protected void handleRecipient(final JsonObject json, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(SlackPlatformEnum.SLACK);

		final String channel = json.get(CHANNEL).getAsString();
		participant.setId(channel);
		result.setRecipient(participant);
	}

	protected void handleSender(final JsonObject json, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(SlackPlatformEnum.SLACK);

		final String channel = json.get(CHANNEL).getAsString();
		participant.setId(channel);
		result.setSender(participant);
	}

	protected void handleText(final String text, final ReceiveMessage result) {
		final TextReceivePayload textReceivePayload = slackReceivePayloadFactory.createTextReceivePayload(text);
		result.addPayload(textReceivePayload);
	}

}
