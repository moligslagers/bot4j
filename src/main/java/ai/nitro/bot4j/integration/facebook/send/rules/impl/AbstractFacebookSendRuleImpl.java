/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.rules.impl;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;
import com.restfb.types.send.SenderActionEnum;

import ai.nitro.bot4j.integration.facebook.send.rules.FacebookSendRule;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;

public abstract class AbstractFacebookSendRuleImpl implements FacebookSendRule {

	final static Logger LOG = LogManager.getLogger(AbstractFacebookSendRuleImpl.class);

	@Inject
	protected FacebookClient facebookClient;

	protected IdMessageRecipient createIdMessageRecipient(final Participant recipient) {
		final IdMessageRecipient result = new IdMessageRecipient(recipient.getId());
		return result;
	}

	protected boolean hasPayloadType(final Type type, final SendMessage sendMessage) {
		final AbstractSendPayload payload = sendMessage.getPayload();
		final boolean result = payload != null && type.equals(payload.getType());
		return result;
	}

	protected void publish(final Message message, final IdMessageRecipient recipient) {
		LOG.info("sending to {} message {}", recipient, message);

		final Parameter recipientParam = Parameter.with("recipient", recipient);
		final Parameter messageParam = Parameter.with("message", message);

		facebookClient.publish("me/messages", SendResponse.class, recipientParam, messageParam);
	}

	protected void publish(final SenderActionEnum senderAction, final IdMessageRecipient recipient) {
		LOG.info("sending to {} param {}", recipient, senderAction);

		final Parameter recipientParam = Parameter.with("recipient", recipient);
		final Parameter senderActionParam = Parameter.with("sender_action", senderAction);

		facebookClient.publish("me/messages", SendResponse.class, recipientParam, senderActionParam);
	}
}
