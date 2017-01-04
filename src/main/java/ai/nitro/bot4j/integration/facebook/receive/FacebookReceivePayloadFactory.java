/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.receive;

import com.restfb.types.webhook.messaging.DeliveryItem;
import com.restfb.types.webhook.messaging.MessagingAttachment;
import com.restfb.types.webhook.messaging.PostbackItem;
import com.restfb.types.webhook.messaging.QuickReplyItem;
import com.restfb.types.webhook.messaging.ReadItem;

import ai.nitro.bot4j.middle.domain.receive.payload.DeliveryNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.QuickReplyReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.ReadNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;

public interface FacebookReceivePayloadFactory {

	DeliveryNotificationReceivePayload createDeliveryNotification(DeliveryItem deliveryItem);

	PostbackReceivePayload createPostback(PostbackItem postbackItem);

	QuickReplyReceivePayload createQuickReply(QuickReplyItem quickReplyItem);

	ReadNotificationReceivePayload createReadNotification(ReadItem readItem);

	TextReceivePayload createTextPayload(String text);

	UrlAttachmentReceivePayload createUrlAttachment(MessagingAttachment messagingAttachment);
}
