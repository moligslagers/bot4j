package ai.nitro.bot4j.integration.alexa.receive;

public interface AlexaReceiveHandler {

	byte[] handleSpeechletRequest(byte[] speechletRequest);
}