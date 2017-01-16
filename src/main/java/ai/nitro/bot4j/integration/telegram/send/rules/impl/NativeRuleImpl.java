/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import com.pengrad.telegrambot.request.BaseRequest;

import ai.nitro.bot4j.integration.telegram.domain.TelegramPlatformEnum;
import ai.nitro.bot4j.middle.domain.send.SendMessage;

public class NativeRuleImpl extends AbstractTelegramSendRuleImpl {

	@Override
	public boolean applies(final SendMessage sendMessage) {
		final boolean result = sendMessage.getNativePayload(TelegramPlatformEnum.TELEGRAM) != null;
		return result;
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final BaseRequest message = (BaseRequest) sendMessage.getNativePayload(TelegramPlatformEnum.TELEGRAM);
		client.execute(message);
	}

}
