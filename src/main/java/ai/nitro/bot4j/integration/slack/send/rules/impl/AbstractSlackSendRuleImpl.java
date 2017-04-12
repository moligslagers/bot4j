/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.send.rules.impl;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.webhook.Payload;

import ai.nitro.bot4j.integration.slack.config.SlackConfig;
import ai.nitro.bot4j.integration.slack.send.rules.SlackSendRule;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;

public abstract class AbstractSlackSendRuleImpl implements SlackSendRule {

	final static Logger LOG = LogManager.getLogger(AbstractSlackSendRuleImpl.class);

	@Inject
	protected SlackConfig slackConfig;

	protected void chatPostMessage(final Payload payload) {
		final String channel = payload.getChannel();
		final String username = payload.getUsername();
		final String text = payload.getText();
		final List<Attachment> attachments = payload.getAttachments();

		LOG.info("sending with username {} to channel {} payload {}", username, channel, payload);

		chatPostMessage(channel, username, text, attachments);
	}

	protected void chatPostMessage(final String channel, final String username, final String text,
			final List<Attachment> attachments) {
		final Slack slack = Slack.getInstance();
		final String token = slackConfig.getAccessToken();

		final ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder().username(username)
				.token(token).channel(channel).text(text).attachments(attachments).build();

		try {
			slack.methods().chatPostMessage(chatPostMessageRequest);
		} catch (final IOException e) {
			LOG.warn(e.getMessage(), e);
		} catch (final SlackApiException e) {
			LOG.warn(e.getMessage(), e);
		}
	}

	protected boolean hasPayloadType(final Type type, final SendMessage sendMessage) {
		final AbstractSendPayload payload = sendMessage.getPayload();
		final boolean result = payload != null && type.equals(payload.getType());
		return result;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
