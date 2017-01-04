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
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveActionMessageFactory;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;

public class SlackReceiveEventMessageFactoryTest extends TestBase {

	@Inject
	protected SlackReceiveActionMessageFactory slackReceiveActionMessageFactory;

	@Test
	public void testCreateReceiveMessage() throws Exception {
		final String payload = "{\"actions\":[],\"callback_id\":\"some button\",\"user\":{\"id\":\"test\",\"name\":\"uwol\"},\"action_ts\":\"1481800795.706419\",\"message_ts\":\"1481800793.000016\"}";

		final JsonParser jsonParser = new JsonParser();
		final JsonObject json = jsonParser.parse(payload).getAsJsonObject();

		final ReceiveMessage receiveMessage = slackReceiveActionMessageFactory.createReceiveMessage(json);
		assertEquals("1481800795.706419-test", receiveMessage.getMessageId());
	}

}
