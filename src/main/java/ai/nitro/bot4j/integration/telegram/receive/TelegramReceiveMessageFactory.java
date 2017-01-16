package ai.nitro.bot4j.integration.telegram.receive;

import com.pengrad.telegrambot.model.Update;

import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;

public interface TelegramReceiveMessageFactory {

	ReceiveMessage createReceiveMessage(Update update);

}