/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.receive.impl;

import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import com.restfb.types.webhook.messaging.DeliveryItem;
import com.restfb.types.webhook.messaging.MessageItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.MessagingItem;
import com.restfb.types.webhook.messaging.MessagingParticipant;
import com.restfb.types.webhook.messaging.PostbackItem;
import com.restfb.types.webhook.messaging.QuickReplyItem;
import com.restfb.types.webhook.messaging.ReadItem;

import ai.nitro.bot4j.integration.facebook.domain.FacebookPlatformEnum;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveMessageFactory;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceivePayloadFactory;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.DeliveryNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.QuickReplyReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.ReadNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;

public class FacebookReceiveMessageFactoryImpl implements FacebookReceiveMessageFactory {

	@Inject
	protected FacebookReceivePayloadFactory facebookReceivePayloadFactory;

	@Override
	public ReceiveMessage createReceiveMessage(final MessagingItem messagingItem) {
		final ReceiveMessage result = new ReceiveMessage();

		final MessageItem messageItem = messagingItem.getMessage();
		result.setNativePayload(FacebookPlatformEnum.FACEBOOK, messagingItem);

		handleSender(messagingItem.getSender(), result);
		handleRecipient(messagingItem.getRecipient(), result);

		if (messageItem != null) {
			handleMessageItem(messageItem, result);
		}

		if (messagingItem.getPostback() != null) {
			handlePostbackItem(messagingItem.getPostback(), result);
		}

		if (messagingItem.getDelivery() != null) {
			handleDeliveryItem(messagingItem.getDelivery(), result);
		}

		if (messagingItem.getRead() != null) {
			handleReadItem(messagingItem.getRead(), result);
		}

		return result;
	}

	protected void handleDeliveryItem(final DeliveryItem deliveryItem, final ReceiveMessage result) {
		final DeliveryNotificationReceivePayload deliveryNotification = facebookReceivePayloadFactory
				.createDeliveryNotification(deliveryItem);
		result.addPayload(deliveryNotification);
	}

	protected void handleMessageItem(final MessageItem messageItem, final ReceiveMessage result) {
		result.setMessageId(messageItem.getMid());

		if (!Strings.isBlank(messageItem.getText())) {
			handleText(messageItem.getText(), result);
		}

		if (messageItem.getQuickReply() != null) {
			handleQuickReplyItem(messageItem.getQuickReply(), result);
		}

		for (final MessagingAttachment messagingAttachment : messageItem.getAttachments()) {
			handleMessagingAttachment(result, messagingAttachment);
		}
	}

	protected void handleMessagingAttachment(final ReceiveMessage result,
			final MessagingAttachment messagingAttachment) {
		final UrlAttachmentReceivePayload urlAttachment = facebookReceivePayloadFactory
				.createUrlAttachment(messagingAttachment);
		result.addPayload(urlAttachment);
	}

	protected void handlePostbackItem(final PostbackItem postbackItem, final ReceiveMessage result) {
		final PostbackReceivePayload postback = facebookReceivePayloadFactory.createPostback(postbackItem);
		result.addPayload(postback);
	}

	protected void handleQuickReplyItem(final QuickReplyItem quickReplyItem, final ReceiveMessage result) {
		final QuickReplyReceivePayload quickReply = facebookReceivePayloadFactory.createQuickReply(quickReplyItem);
		result.addPayload(quickReply);
	}

	protected void handleReadItem(final ReadItem readItem, final ReceiveMessage result) {
		final ReadNotificationReceivePayload readNotification = facebookReceivePayloadFactory
				.createReadNotification(readItem);
		result.addPayload(readNotification);
	}

	protected void handleRecipient(final MessagingParticipant recipient, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(FacebookPlatformEnum.FACEBOOK);
		participant.setId(recipient.getId());
		result.setRecipient(participant);
	}

	protected void handleSender(final MessagingParticipant sender, final ReceiveMessage result) {
		final Participant participant = new Participant();
		participant.setPlatform(FacebookPlatformEnum.FACEBOOK);
		participant.setId(sender.getId());
		result.setSender(participant);
	}

	protected void handleText(final String text, final ReceiveMessage result) {
		final TextReceivePayload textPayload = facebookReceivePayloadFactory.createTextPayload(text);
		result.addPayload(textPayload);
	}
}
