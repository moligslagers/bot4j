package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pengrad.telegrambot.TelegramBot;

import ai.nitro.bot4j.integration.telegram.send.rules.TelegramSendRule;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;

public abstract class AbstractTelegramSendRuleImpl implements TelegramSendRule {

	final static Logger LOG = LogManager.getLogger(AbstractTelegramSendRuleImpl.class);

	@Inject
	protected TelegramBot client;

	protected void execute(final com.pengrad.telegrambot.request.SendMessage message, final String recipient) {
		LOG.info("sending to {} message {}", recipient, message);
		client.execute(message);
	}

	protected boolean hasPayloadType(final Type type, final SendMessage sendMessage) {
		final AbstractSendPayload payload = sendMessage.getPayload();
		final boolean result = payload != null && type.equals(payload.getType());
		return result;
	}
}
