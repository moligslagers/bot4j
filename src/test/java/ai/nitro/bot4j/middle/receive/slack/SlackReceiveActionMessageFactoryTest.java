/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive.slack;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveEventMessageFactory;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;

public class SlackReceiveActionMessageFactoryTest extends TestBase {

	@Inject
	protected SlackReceiveEventMessageFactory slackReceiveEventMessageFactory;

	@Test
	public void testCreateReceiveMessage() throws Exception {
		final String payload = "{\"type\":\"message\",\"user\":\"U1GNSJNDS\",\"ts\":\"1481799582.000014\",\"channel\":\"C3DM4L9MF\",\"event_ts\":\"1481799582.000014\"}";

		final JsonParser jsonParser = new JsonParser();
		final JsonObject json = jsonParser.parse(payload).getAsJsonObject();

		final ReceiveMessage receiveMessage = slackReceiveEventMessageFactory.createReceiveMessage(json);
		assertEquals("1481799582.000014-U1GNSJNDS", receiveMessage.getMessageId());
	}

}
