/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import ai.nitro.bot4j.integration.telegram.send.TelegramSendInlineKeyboardFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;

public class ListRuleImpl extends AbstractTelegramSendRuleImpl {

	protected TelegramSendInlineKeyboardFactory telegramSendInlineKeyboardFactory;

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.LIST, sendMessage);

	}

	@Override
	public void apply(final SendMessage sendMessage) {
		// TODO
	}

}
