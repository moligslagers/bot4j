/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.rules.impl;

import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;

import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.TextSendPayload;

public class TextRuleImpl extends AbstractFacebookSendRuleImpl {

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.TEXT, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage, Long botId) {
		final TextSendPayload textPayload = sendMessage.getPayloadWithType(TextSendPayload.class);
		final String text = textPayload.getText();
		final Message message = new Message(text);

		final IdMessageRecipient recipient = createIdMessageRecipient(sendMessage.getRecipient());
		publish(message, recipient, botId);
	}

}
