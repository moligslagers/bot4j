/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.rules.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;

import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.QuickRepliesSendPayload;
import ai.nitro.bot4j.middle.domain.send.quickreply.QuickReply;
import ai.nitro.bot4j.middle.domain.send.quickreply.QuickReply.ContentType;

public class QuickRepliesRuleImpl extends AbstractFacebookSendRuleImpl {

	final static Logger LOG = LogManager.getLogger(QuickRepliesRuleImpl.class);

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.QUICK_REPLIES, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final QuickRepliesSendPayload quickReplies = sendMessage.getPayloadWithType(QuickRepliesSendPayload.class);

		final String text = quickReplies.getText();
		final Message message = new Message(text);

		for (final QuickReply quickReply : quickReplies.getQuickReplies()) {
			message.addQuickReply(createQuickReply(quickReply));
		}

		final IdMessageRecipient recipient = createIdMessageRecipient(sendMessage.getRecipient());
		publish(message, recipient);
	}

	protected com.restfb.types.send.QuickReply createQuickReply(final QuickReply quickReply) {
		final ContentType contentType = quickReply.getContentType();
		final com.restfb.types.send.QuickReply result;

		if (contentType == null) {
			LOG.warn("content type is {}", contentType);
			result = null;
		} else {
			switch (contentType) {
			case LOCATION:
				result = new com.restfb.types.send.QuickReply();
				break;
			case TEXT:
			default:
				final String title = quickReply.getTitle();
				final String payload = quickReply.getPayload() != null ? quickReply.getPayload() : "";
				result = new com.restfb.types.send.QuickReply(title, payload);
			}
		}

		return result;
	}
}
