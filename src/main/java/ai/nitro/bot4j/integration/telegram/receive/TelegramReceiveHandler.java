package ai.nitro.bot4j.integration.telegram.receive;

import com.pengrad.telegrambot.model.Update;

public interface TelegramReceiveHandler {

	void handleUpdateMessage(Update update);
}