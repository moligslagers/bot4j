/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.send.impl;

import javax.inject.Inject;

import com.github.seratch.jslack.api.model.Action;
import com.github.seratch.jslack.api.model.ActionAttachment;
import com.github.seratch.jslack.api.model.Attachment;
import com.google.common.collect.Lists;

import ai.nitro.bot4j.integration.slack.send.SlackSendActionFactory;
import ai.nitro.bot4j.integration.slack.send.SlackSendAttachmentFactory;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.button.PostbackSendButton;
import ai.nitro.bot4j.middle.domain.send.button.WebSendButton;

public class SlackSendAttachmentFactoryImpl implements SlackSendAttachmentFactory {

	@Inject
	protected SlackSendActionFactory slackSendActionFactory;

	@Override
	public Attachment createAttachment(final AbstractSendButton button) {
		final Attachment result;

		switch (button.getType()) {
		case POSTBACK_BUTTON:
			result = createPostbackButton((PostbackSendButton) button);
			break;
		case WEB_BUTTON:
		default:
			result = createWebButton((WebSendButton) button);
			break;
		}

		return result;
	}

	protected ActionAttachment createPostbackButton(final PostbackSendButton postbackSendButton) {
		final Action action = slackSendActionFactory.createPostbackAction(postbackSendButton);

		final ActionAttachment result = new ActionAttachment();
		result.setTitle(postbackSendButton.getTitle());
		result.setFallback(postbackSendButton.getTitle());
		result.setCallbackId(postbackSendButton.getTitle());
		result.setActions(Lists.newArrayList(action));

		return result;
	}

	protected Attachment createWebButton(final WebSendButton webSendButton) {
		return Attachment.builder().title(webSendButton.getTitle()).titleLink(webSendButton.getUrl())
				.fallback(webSendButton.getTitle()).build();
	}

}
