package ai.nitro.bot4j.integration.alexa.receive.hook.impl;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.integration.alexa.receive.AlexaReceiveHandler;
import ai.nitro.bot4j.integration.alexa.receive.hook.AlexaWebhook;

public class AlexaWebhookImpl implements AlexaWebhook {

	static Logger LOG = LogManager.getLogger(AlexaWebhookImpl.class);

	@Inject
	protected AlexaReceiveHandler alexaReceiveHandler;

	@Override
	public String post(final HttpServletRequest req, final HttpServletResponse res) {
		String result = "";

		try {
			final byte[] serializedSpeechletRequest = IOUtils.toByteArray(req.getInputStream());
			LOG.info("serializedSpeechletRequest");

			final byte[] outputBytes = alexaReceiveHandler.handleSpeechletRequest(serializedSpeechletRequest);

			result = new String(outputBytes);
		} catch (final IOException e) {
			LOG.warn(e.getMessage(), e);
		}

		return result;
	}

}
