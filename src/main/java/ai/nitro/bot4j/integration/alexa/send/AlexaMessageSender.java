package ai.nitro.bot4j.integration.alexa.send;

import ai.nitro.bot4j.middle.send.PlatformMessageSender;

public interface AlexaMessageSender extends PlatformMessageSender {

	String getText();
}
