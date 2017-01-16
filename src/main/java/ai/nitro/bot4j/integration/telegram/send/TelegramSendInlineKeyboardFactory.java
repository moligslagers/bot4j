/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;

public interface TelegramSendInlineKeyboardFactory {

	InlineKeyboardButton createInlineKeyboard(AbstractSendButton abstractSendButton);

}
