/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.TextSendPayload;

public class TextRuleImpl extends AbstractTelegramSendRuleImpl {

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.TEXT, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final TextSendPayload textSendPayload = sendMessage.getPayloadWithType(TextSendPayload.class);
		final String text = textSendPayload.getText();

		final String recipient = sendMessage.getRecipient().getId();

		final com.pengrad.telegrambot.request.SendMessage sendMessageTelegram = new com.pengrad.telegrambot.request.SendMessage(
				recipient, text);
		super.execute(sendMessageTelegram, recipient);
	}
}
