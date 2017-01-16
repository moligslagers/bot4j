/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.receive;

import com.pengrad.telegrambot.model.Update;

import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;

public interface TelegramReceiveMessageFactory {

	ReceiveMessage createReceiveMessage(Update update);

}