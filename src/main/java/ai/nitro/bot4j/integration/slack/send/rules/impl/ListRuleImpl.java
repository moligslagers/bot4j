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
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.list.ListSendElement;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.ListSendPayload;

public class ListRuleImpl extends AbstractSlackSendRuleImpl {

	@Inject
	protected SlackSendAttachmentFactory slackSendAttachmentFactory;

	protected void addListAttachment(final ListSendPayload listSendPayload, final List<Attachment> attachments) {
		final AbstractSendButton button = listSendPayload.getButton();

		if (button != null) {
			final Attachment result = slackSendAttachmentFactory.createAttachment(button);
			attachments.add(result);
		}
	}

	protected void addListElementAttachment(final ListSendElement listSendElement, final List<Attachment> attachments) {
		final AbstractSendButton button = listSendElement.getButton();
		final Attachment result;

		if (button != null) {
			result = slackSendAttachmentFactory.createAttachment(button);

			final boolean isWebButton = AbstractSendButton.Type.WEB_BUTTON.equals(button.getType());

			if (isWebButton) {
				final String title = listSendElement.getTitle() + ": " + button.getTitle();
				result.setTitle(title);
			}
		} else {
			result = Attachment.builder().title(listSendElement.getTitle()).build();
		}

		result.setText(listSendElement.getSubTitle());
		result.setImageUrl(listSendElement.getImageUrl());
		attachments.add(result);
	}

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.LIST, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final ListSendPayload listSendPayload = sendMessage.getPayloadWithType(ListSendPayload.class);
		final List<Attachment> attachments = new ArrayList<Attachment>();

		for (final ListSendElement listSendElement : listSendPayload.getListElements()) {
			addListElementAttachment(listSendElement, attachments);
		}

		addListAttachment(listSendPayload, attachments);

		final String channel = sendMessage.getRecipient().getId();
		final String text = listSendPayload.getTitle();
		final Payload payload = Payload.builder().channel(channel).text(text).attachments(attachments).build();
		chatPostMessage(payload);
	}

}
