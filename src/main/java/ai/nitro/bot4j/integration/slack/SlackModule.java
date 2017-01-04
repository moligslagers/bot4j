/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import ai.nitro.bot4j.integration.slack.config.SlackConfig;
import ai.nitro.bot4j.integration.slack.config.impl.SlackConfigImpl;
import ai.nitro.bot4j.integration.slack.oauth.SlackOAuthClient;
import ai.nitro.bot4j.integration.slack.oauth.impl.SlackOAuthClientImpl;
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveActionMessageFactory;
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveEventMessageFactory;
import ai.nitro.bot4j.integration.slack.receive.SlackReceiveHandler;
import ai.nitro.bot4j.integration.slack.receive.SlackReceivePayloadFactory;
import ai.nitro.bot4j.integration.slack.receive.impl.SlackReceiveActionMessageFactoryImpl;
import ai.nitro.bot4j.integration.slack.receive.impl.SlackReceiveEventMessageFactoryImpl;
import ai.nitro.bot4j.integration.slack.receive.impl.SlackReceiveHandlerImpl;
import ai.nitro.bot4j.integration.slack.receive.impl.SlackReceivePayloadFactoryImpl;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackActionWebhook;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackEventWebhook;
import ai.nitro.bot4j.integration.slack.receive.webhook.SlackOAuthWebhook;
import ai.nitro.bot4j.integration.slack.receive.webhook.impl.SlackActionWebhookImpl;
import ai.nitro.bot4j.integration.slack.receive.webhook.impl.SlackEventWebhookImpl;
import ai.nitro.bot4j.integration.slack.receive.webhook.impl.SlackOAuthWebhookImpl;
import ai.nitro.bot4j.integration.slack.send.SlackMessageSender;
import ai.nitro.bot4j.integration.slack.send.SlackSendActionFactory;
import ai.nitro.bot4j.integration.slack.send.SlackSendAttachmentFactory;
import ai.nitro.bot4j.integration.slack.send.impl.SlackMessageSenderImpl;
import ai.nitro.bot4j.integration.slack.send.impl.SlackSendActionFactoryImpl;
import ai.nitro.bot4j.integration.slack.send.impl.SlackSendAttachmentFactoryImpl;
import ai.nitro.bot4j.integration.slack.send.rules.SlackSendRule;
import ai.nitro.bot4j.integration.slack.send.rules.impl.BubbleRuleImpl;
import ai.nitro.bot4j.integration.slack.send.rules.impl.ButtonsRuleImpl;
import ai.nitro.bot4j.integration.slack.send.rules.impl.ImageRuleImpl;
import ai.nitro.bot4j.integration.slack.send.rules.impl.ListRuleImpl;
import ai.nitro.bot4j.integration.slack.send.rules.impl.NativeRuleImpl;
import ai.nitro.bot4j.integration.slack.send.rules.impl.TextRuleImpl;
import ai.nitro.bot4j.integration.slack.send.rules.impl.VideoRuleImpl;

public class SlackModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SlackActionWebhook.class).to(SlackActionWebhookImpl.class);
		bind(SlackConfig.class).to(SlackConfigImpl.class);
		bind(SlackEventWebhook.class).to(SlackEventWebhookImpl.class);
		bind(SlackMessageSender.class).to(SlackMessageSenderImpl.class);
		bind(SlackOAuthClient.class).to(SlackOAuthClientImpl.class);
		bind(SlackOAuthWebhook.class).to(SlackOAuthWebhookImpl.class);
		bind(SlackReceiveHandler.class).to(SlackReceiveHandlerImpl.class);
		bind(SlackReceiveActionMessageFactory.class).to(SlackReceiveActionMessageFactoryImpl.class);
		bind(SlackReceiveEventMessageFactory.class).to(SlackReceiveEventMessageFactoryImpl.class);
		bind(SlackReceivePayloadFactory.class).to(SlackReceivePayloadFactoryImpl.class);
		bind(SlackSendActionFactory.class).to(SlackSendActionFactoryImpl.class);
		bind(SlackSendAttachmentFactory.class).to(SlackSendAttachmentFactoryImpl.class);

		final Multibinder<SlackSendRule> slackSendRuleBinder = Multibinder.newSetBinder(binder(), SlackSendRule.class);
		slackSendRuleBinder.addBinding().to(NativeRuleImpl.class);
		slackSendRuleBinder.addBinding().to(TextRuleImpl.class);
		slackSendRuleBinder.addBinding().to(ButtonsRuleImpl.class);
		slackSendRuleBinder.addBinding().to(ImageRuleImpl.class);
		slackSendRuleBinder.addBinding().to(VideoRuleImpl.class);
		slackSendRuleBinder.addBinding().to(BubbleRuleImpl.class);
		slackSendRuleBinder.addBinding().to(ListRuleImpl.class);
	}

}
