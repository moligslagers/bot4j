/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.send.rules.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.webhook.Payload;

import ai.nitro.bot4j.integration.slack.send.SlackSendAttachmentFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.bubble.Bubble;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.BubbleSendPayload;

public class BubbleRuleImpl extends AbstractSlackSendRuleImpl {

	@Inject
	protected SlackSendAttachmentFactory slackSendAttachmentFactory;

	protected void addHeader(final Bubble bubble, final List<Attachment> attachments) {
		final Attachment attachment = Attachment.builder().title(bubble.getTitle()).titleLink(bubble.getUrl())
				.fallback(bubble.getTitle()).imageUrl(bubble.getImageUrl()).build();
		attachments.add(attachment);
	}

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.BUBBLE, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final BubbleSendPayload bubbleSendPayload = sendMessage.getPayloadWithType(BubbleSendPayload.class);
		final Bubble bubble = bubbleSendPayload.getBubbles().get(0);
		final List<Attachment> attachments = new ArrayList<Attachment>();

		addHeader(bubble, attachments);

		for (final AbstractSendButton button : bubble.getButtons()) {
			attachments.add(slackSendAttachmentFactory.createAttachment(button));
		}

		final String channel = sendMessage.getRecipient().getId();
		final String text = bubble.getTitle();
		final Payload payload = Payload.builder().channel(channel).text(text).attachments(attachments).build();
		chatPostMessage(payload);
	}

}
