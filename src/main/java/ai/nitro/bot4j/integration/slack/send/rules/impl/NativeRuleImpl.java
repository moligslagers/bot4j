/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.send.rules.impl;

import com.github.seratch.jslack.api.webhook.Payload;

import ai.nitro.bot4j.integration.slack.domain.SlackPlatformEnum;
import ai.nitro.bot4j.middle.domain.send.SendMessage;

public class NativeRuleImpl extends AbstractSlackSendRuleImpl {

	@Override
	public boolean applies(final SendMessage sendMessage) {
		final boolean result = sendMessage.getNativePayload(SlackPlatformEnum.SLACK) != null;
		return result;
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final Payload payload = (Payload) sendMessage.getNativePayload(SlackPlatformEnum.SLACK);
		chatPostMessage(payload);
	}

}
