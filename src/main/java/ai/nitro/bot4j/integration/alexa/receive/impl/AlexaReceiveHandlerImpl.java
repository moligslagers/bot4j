package ai.nitro.bot4j.integration.alexa.receive.impl;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletRequestHandlerException;
import com.amazon.speech.speechlet.servlet.ServletSpeechletRequestHandler;

import ai.nitro.bot4j.integration.alexa.Bot4jSpeechlet;
import ai.nitro.bot4j.integration.alexa.receive.AlexaReceiveHandler;

public class AlexaReceiveHandlerImpl implements AlexaReceiveHandler {

	static Logger LOG = LogManager.getLogger(AlexaReceiveHandlerImpl.class);

	@Inject
	protected Bot4jSpeechlet bot4jSpeechlet;

	@Override
	public byte[] handleSpeechletRequest(final byte[] speechletRequest) {
		LOG.warn("handleSpeechletRequest");

		final ServletSpeechletRequestHandler speechletRequestHandler = new ServletSpeechletRequestHandler();

		byte[] result = null;

		try {
			result = speechletRequestHandler.handleSpeechletCall(bot4jSpeechlet, speechletRequest);
		} catch (IOException | SpeechletRequestHandlerException | SpeechletException e) {
			LOG.warn(e.getMessage(), e);
		}

		return result;
	}

}
