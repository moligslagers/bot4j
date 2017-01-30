/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

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
		byte[] result = null;

		try {
			final ServletSpeechletRequestHandler speechletRequestHandler = new ServletSpeechletRequestHandler();
			result = speechletRequestHandler.handleSpeechletCall(bot4jSpeechlet, speechletRequest);
		} catch (IOException | SpeechletRequestHandlerException | SpeechletException e) {
			LOG.warn(e.getMessage(), e);
		}

		return result;
	}

}
