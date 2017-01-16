package ai.nitro.bot4j.integration.telegram.send;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;

public interface TelegramSendInlineKeyboardFactory {

	InlineKeyboardButton createInlineKeyboard(AbstractSendButton abstractSendButton);

}
