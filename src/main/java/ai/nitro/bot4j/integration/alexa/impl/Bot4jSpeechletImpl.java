/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.alexa.impl;

import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.User;
import com.amazon.speech.ui.PlainTextOutputSpeech;

import ai.nitro.bot4j.integration.alexa.Bot4jSpeechlet;
import ai.nitro.bot4j.integration.alexa.domain.AlexaPlatformEnum;
import ai.nitro.bot4j.integration.alexa.send.AlexaMessageSender;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.NlpContext;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.receive.MessageReceiver;

public class Bot4jSpeechletImpl implements Bot4jSpeechlet {

	private static final Logger LOG = LogManager.getLogger(Bot4jSpeechletImpl.class);

	@Inject
	protected AlexaMessageSender alexaMessageSender;

	@Inject
	protected MessageReceiver messageReceiver;

	protected NlpContext createNlpContext(final Intent intent) {
		final NlpContext nlpContext = new NlpContext();

		final String intentName = intent.getName();
		nlpContext.setIntent(intentName);

		for (final Entry<String, Slot> entry : intent.getSlots().entrySet()) {
			final String key = entry.getKey();
			final Slot value = entry.getValue();

			final String slotValue = value.getValue();
			nlpContext.addNamedEntity(key, slotValue);
		}

		return nlpContext;
	}

	protected ReceiveMessage createReceiveMessage(final IntentRequest intentRequest, final User user) {
		final Participant sender = createSender(user);

		final ReceiveMessage result = new ReceiveMessage();
		result.setMessageId(intentRequest.getRequestId());
		result.setSender(sender);

		final TextReceivePayload textReceivePayload = createTextReceivePayload(intentRequest);
		result.addPayload(textReceivePayload);
		return result;
	}

	protected Participant createSender(final User user) {
		final Participant sender = new Participant();
		sender.setPlatform(AlexaPlatformEnum.ALEXA);
		sender.setId(user.getUserId());
		return sender;
	}

	protected TextReceivePayload createTextReceivePayload(final IntentRequest intentRequest) {
		final NlpContext nlpContext = createNlpContext(intentRequest.getIntent());
		final TextReceivePayload result = new TextReceivePayload();
		result.setNlpContext(nlpContext);
		return result;
	}

	@Override
	public SpeechletResponse onIntent(final SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		LOG.info("onIntent requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());

		if (requestEnvelope.getRequest().getIntent() == null) {
			LOG.warn("no intent set");
		} else if (requestEnvelope.getSession().getUser() == null) {
			LOG.warn("no user set");
		} else {
			receiveMessage(requestEnvelope.getRequest(), requestEnvelope.getSession().getUser());
		}

		final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		final String text = alexaMessageSender.getText();
		speech.setText(text);

		final SpeechletResponse result = SpeechletResponse.newTellResponse(speech);
		return result;
	}

	@Override
	public SpeechletResponse onLaunch(final SpeechletRequestEnvelope<LaunchRequest> arg0) {
		return null;
	}

	@Override
	public void onSessionEnded(final SpeechletRequestEnvelope<SessionEndedRequest> arg0) {
	}

	@Override
	public void onSessionStarted(final SpeechletRequestEnvelope<SessionStartedRequest> arg0) {
	}

	protected void receiveMessage(final IntentRequest intentRequest, final User user) {
		final ReceiveMessage receiveMessage = createReceiveMessage(intentRequest, user);
		messageReceiver.receive(receiveMessage);
	}

}
