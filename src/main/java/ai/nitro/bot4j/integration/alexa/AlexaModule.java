/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.alexa;

import com.google.inject.AbstractModule;

import ai.nitro.bot4j.integration.alexa.receive.AlexaReceiveHandler;
import ai.nitro.bot4j.integration.alexa.receive.impl.AlexaReceiveHandlerImpl;
import ai.nitro.bot4j.integration.alexa.receive.webhook.AlexaWebhook;
import ai.nitro.bot4j.integration.alexa.receive.webhook.impl.AlexaWebhookImpl;
import ai.nitro.bot4j.integration.alexa.send.AlexaMessageSender;
import ai.nitro.bot4j.integration.alexa.send.impl.AlexaMessageSenderImpl;

public class AlexaModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AlexaReceiveHandler.class).to(AlexaReceiveHandlerImpl.class);
		bind(AlexaMessageSender.class).to(AlexaMessageSenderImpl.class);
		bind(AlexaWebhook.class).to(AlexaWebhookImpl.class);

		bind(Bot4jSpeechlet.class);
	}

}
