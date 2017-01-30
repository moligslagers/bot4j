package ai.nitro.bot4j.integration.alexa;

import com.google.inject.AbstractModule;

import ai.nitro.bot4j.integration.alexa.receive.AlexaReceiveHandler;
import ai.nitro.bot4j.integration.alexa.receive.hook.AlexaWebhook;
import ai.nitro.bot4j.integration.alexa.receive.hook.impl.AlexaWebhookImpl;
import ai.nitro.bot4j.integration.alexa.receive.impl.AlexaReceiveHandlerImpl;
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
