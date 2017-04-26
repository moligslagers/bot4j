/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.receive.impl;

import javax.inject.Inject;

import com.restfb.types.webhook.messaging.MessagingItem;

import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveHandler;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveMessageFactory;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.MessageReceiver;

public class FacebookReceiveHandlerImpl implements FacebookReceiveHandler {

	@Inject
	protected FacebookReceiveMessageFactory facebookReceiveMessageFactory;

	@Inject
	protected MessageReceiver messageReceiver;

	@Override
	public void handleMessagingItem(final MessagingItem messagingItem, Long botId) {
		final ReceiveMessage receiveMessage = facebookReceiveMessageFactory.createReceiveMessage(messagingItem);

		if (receiveMessage != null) {
			messageReceiver.receive(receiveMessage, botId);
		}
	}
}
