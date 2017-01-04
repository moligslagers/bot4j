/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

import ai.nitro.bot4j.integration.facebook.config.FacebookConfig;
import ai.nitro.bot4j.integration.facebook.config.impl.FacebookConfigImpl;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveHandler;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceiveMessageFactory;
import ai.nitro.bot4j.integration.facebook.receive.FacebookReceivePayloadFactory;
import ai.nitro.bot4j.integration.facebook.receive.impl.FacebookReceiveHandlerImpl;
import ai.nitro.bot4j.integration.facebook.receive.impl.FacebookReceiveMessageFactoryImpl;
import ai.nitro.bot4j.integration.facebook.receive.impl.FacebookReceivePayloadFactoryImpl;
import ai.nitro.bot4j.integration.facebook.receive.webhook.FacebookWebhook;
import ai.nitro.bot4j.integration.facebook.receive.webhook.impl.FacebookWebhookImpl;
import ai.nitro.bot4j.integration.facebook.send.FacebookMessageSender;
import ai.nitro.bot4j.integration.facebook.send.FacebookSendButtonFactory;
import ai.nitro.bot4j.integration.facebook.send.impl.FacebookMessageSenderImpl;
import ai.nitro.bot4j.integration.facebook.send.impl.FacebookSendButtonFactoryImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.FacebookSendRule;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.BubbleRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.ButtonsRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.ImageRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.ListRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.NativeRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.QuickRepliesRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.TextRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.TypingRuleImpl;
import ai.nitro.bot4j.integration.facebook.send.rules.impl.VideoRuleImpl;

public class FacebookModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FacebookConfig.class).to(FacebookConfigImpl.class);
		bind(FacebookMessageSender.class).to(FacebookMessageSenderImpl.class);
		bind(FacebookReceiveHandler.class).to(FacebookReceiveHandlerImpl.class);
		bind(FacebookReceiveMessageFactory.class).to(FacebookReceiveMessageFactoryImpl.class);
		bind(FacebookReceivePayloadFactory.class).to(FacebookReceivePayloadFactoryImpl.class);
		bind(FacebookSendButtonFactory.class).to(FacebookSendButtonFactoryImpl.class);
		bind(FacebookWebhook.class).to(FacebookWebhookImpl.class);

		final Multibinder<FacebookSendRule> facebookSendRuleBinder = Multibinder.newSetBinder(binder(),
				FacebookSendRule.class);
		facebookSendRuleBinder.addBinding().to(NativeRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(TextRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(ButtonsRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(ImageRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(VideoRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(QuickRepliesRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(TypingRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(BubbleRuleImpl.class);
		facebookSendRuleBinder.addBinding().to(ListRuleImpl.class);
	}

	@Provides
	protected FacebookClient provideFacebookClient(final FacebookConfig config) {
		final String facebookAccessToken = config.getAccessToken();
		return new DefaultFacebookClient(facebookAccessToken, Version.VERSION_2_8);
	}

}
