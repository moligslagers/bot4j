/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.rules.impl;

import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.MediaAttachment;
import com.restfb.types.send.Message;

import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.ImageSendPayload;

public class ImageRuleImpl extends AbstractFacebookSendRuleImpl {

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.IMAGE, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage, Long botId) {
		final ImageSendPayload imageSendPayload = sendMessage.getPayloadWithType(ImageSendPayload.class);

		final MediaAttachment.Type type = MediaAttachment.Type.IMAGE;
		final String imageUrl = imageSendPayload.getImageUrl();

		final MediaAttachment mediaAttachment = new MediaAttachment(type, imageUrl);
		final Message message = new Message(mediaAttachment);

		final IdMessageRecipient recipient = createIdMessageRecipient(sendMessage.getRecipient());
		publish(message, recipient, botId);
	}

}
