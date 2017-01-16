package ai.nitro.bot4j.integration.telegram.send.rules;

import ai.nitro.bot4j.middle.domain.send.SendMessage;

public interface TelegramSendRule {

	boolean applies(SendMessage sendMessage);

	void apply(SendMessage sendMessage);
}
