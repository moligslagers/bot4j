/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.receive.impl;

import javax.inject.Inject;

import com.restfb.types.webhook.messaging.DeliveryItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.MessagingPayload;
import com.restfb.types.webhook.messaging.PostbackItem;
import com.restfb.types.webhook.messaging.QuickReplyItem;
import com.restfb.types.webhook.messaging.ReadItem;

import ai.nitro.bot4j.integration.facebook.receive.FacebookReceivePayloadFactory;
import ai.nitro.bot4j.middle.domain.receive.payload.DeliveryNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.QuickReplyReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.ReadNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;
import ai.nitro.bot4j.middle.payload.PostbackPayload;
import ai.nitro.bot4j.middle.payload.PostbackPayloadService;

public class FacebookReceivePayloadFactoryImpl implements FacebookReceivePayloadFactory {

	@Inject
	protected PostbackPayloadService postbackPayloadService;

	@Override
	public DeliveryNotificationReceivePayload createDeliveryNotification(final DeliveryItem deliveryItem) {
		final DeliveryNotificationReceivePayload result = new DeliveryNotificationReceivePayload();
		result.setMessageIds(deliveryItem.getMids());
		return result;
	}

	@Override
	public PostbackReceivePayload createPostback(final PostbackItem postbackItem) {
		final PostbackReceivePayload result = new PostbackReceivePayload();
		final String serializedPayload = postbackItem.getPayload();

		final PostbackPayload postbackPayload = postbackPayloadService.deserialize(serializedPayload);
		result.setName(postbackPayload.name);
		result.setPayload(postbackPayload.payload);
		return result;
	}

	@Override
	public QuickReplyReceivePayload createQuickReply(final QuickReplyItem quickReplyItem) {
		final String payload = quickReplyItem.getPayload();
		final QuickReplyReceivePayload result = new QuickReplyReceivePayload();
		result.setPayload(payload);
		return result;
	}

	@Override
	public ReadNotificationReceivePayload createReadNotification(final ReadItem readItem) {
		final ReadNotificationReceivePayload result = new ReadNotificationReceivePayload();
		result.setWatermark(readItem.getWatermark());
		return result;
	}

	@Override
	public TextReceivePayload createTextPayload(final String text) {
		final TextReceivePayload result = new TextReceivePayload();
		result.setText(text);
		return result;
	}

	@Override
	public UrlAttachmentReceivePayload createUrlAttachment(final MessagingAttachment messagingAttachment) {
		final UrlAttachmentReceivePayload result = new UrlAttachmentReceivePayload();
		result.setTitle(messagingAttachment.getTitle());
		result.setAttachmentType(messagingAttachment.getType());

		final MessagingPayload payload = messagingAttachment.getPayload();

		if (payload != null) {
			result.setUrl(payload.getUrl());
		}

		return result;
	}

}
