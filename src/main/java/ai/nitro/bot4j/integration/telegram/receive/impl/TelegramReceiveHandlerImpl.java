package ai.nitro.bot4j.integration.telegram.receive.impl;

import javax.inject.Inject;

import com.pengrad.telegrambot.model.Update;

import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveHandler;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveMessageFactory;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.MessageReceiver;

public class TelegramReceiveHandlerImpl implements TelegramReceiveHandler {

	@Inject
	protected MessageReceiver messageReceiver;

	@Inject
	protected TelegramReceiveMessageFactory telegramReceiveMessageFactory;

	@Override
	public void handleUpdateMessage(final Update update) {
		final ReceiveMessage receiveMessage = telegramReceiveMessageFactory.createReceiveMessage(update);
		messageReceiver.receive(receiveMessage);
	}
}
