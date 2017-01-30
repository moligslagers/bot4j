/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.alexa.send.impl;

import javax.inject.Singleton;

import ai.nitro.bot4j.integration.alexa.domain.AlexaPlatformEnum;
import ai.nitro.bot4j.integration.alexa.send.AlexaMessageSender;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.TextSendPayload;

@Singleton
public class AlexaMessageSenderImpl implements AlexaMessageSender {

	protected ThreadLocal<StringBuilder> threadLocalText = new ThreadLocal<>();

	protected void assureThreadLocalText() {
		if (threadLocalText.get() == null) {
			threadLocalText.set(new StringBuilder());
		}
	}

	@Override
	public Platform getPlatform() {
		return AlexaPlatformEnum.ALEXA;
	}

	@Override
	public String getText() {
		final String result = threadLocalText.get().toString();
		return result;
	}

	@Override
	public boolean send(final SendMessage sendMessage) {
		boolean result = false;

		final AbstractSendPayload sendPayload = sendMessage.getPayload();

		if (Type.TEXT.equals(sendPayload.getType())) {
			final TextSendPayload textSendPayload = (TextSendPayload) sendPayload;
			final String text = textSendPayload.getText();

			assureThreadLocalText();

			threadLocalText.get().append(text);
			result = true;
		}

		return result;
	}

}
