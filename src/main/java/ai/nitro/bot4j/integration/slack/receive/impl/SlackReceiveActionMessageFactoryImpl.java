/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.receive.impl;

import javax.inject.Inject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ai.nitro.bot4j.integration.slack.domain.SlackPlatformEnum;
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveActionMessageFactory;
import ai.nitro.bot4j.integration.slack.receive.SlackReceivePayloadFactory;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;

public class SlackReceiveActionMessageFactoryImpl implements SlackReceiveActionMessageFactory {

	private static final String ACTION_TS = "action_ts";

	private static final String ACTIONS = "actions";

	private static final String CHANNEL = "channel";

	private static final String ID = "id";

	private static final String USER = "user";

	@Inject
	protected SlackReceivePayloadFactory slackReceivePayloadFactory;

	@Override
	public ReceiveMessage createReceiveMessage(final JsonObject json) {
		final ReceiveMessage result = new ReceiveMessage();
		result.setNativePayload(SlackPlatformEnum.SLACK, json);

		final String actionTs = json.get(ACTION_TS).getAsString();
		final JsonObject userJsonObject = json.get(USER).getAsJsonObject();
		final String userId = userJsonObject.get(ID).getAsString();
		final String messageId = actionTs + "-" + userId;
		result.setMessageId(messageId);

		handleSender(json, result);
		handleRecipient(json, result);

		if (json.has(ACTIONS)) {
			handleActions(json.get(ACTIONS).getAsJsonArray(), result);
		}

		return result;
	}

	protected void handleAction(final JsonObject json, final ReceiveMessage result) {
		final PostbackReceivePayload postbackReceivePayload = slackReceivePayloadFactory
				.createPostbackReceivePayload(json);
		result.addPayload(postbackReceivePayload);
	}

	protected void handleActions(final JsonArray jsonArray, final ReceiveMessage result) {
		if (jsonArray.size() > 0) {
			final JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
			handleAction(jsonObject, result);
		}
	}

	protected void handleRecipient(final JsonObject json, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(SlackPlatformEnum.SLACK);

		if (json.has(CHANNEL)) {
			final JsonObject channelJsonObject = json.get(CHANNEL).getAsJsonObject();

			if (channelJsonObject.has(ID)) {
				final String channel = channelJsonObject.get(ID).getAsString();
				participant.setId(channel);
			}
		}

		result.setRecipient(participant);
	}

	protected void handleSender(final JsonObject json, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(SlackPlatformEnum.SLACK);

		if (json.has(CHANNEL)) {
			final JsonObject channelJsonObject = json.get(CHANNEL).getAsJsonObject();

			if (channelJsonObject.has(ID)) {
				final String channel = channelJsonObject.get(ID).getAsString();
				participant.setId(channel);
			}
		}

		result.setSender(participant);
	}

}
