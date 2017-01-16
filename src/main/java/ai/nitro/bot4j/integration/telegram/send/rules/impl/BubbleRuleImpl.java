package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

import ai.nitro.bot4j.integration.telegram.send.TelegramSendInlineKeyboardFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.bubble.Bubble;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.BubbleSendPayload;

public class BubbleRuleImpl extends AbstractTelegramSendRuleImpl {

	@Inject
	protected TelegramSendInlineKeyboardFactory telegramSendInlineKeyboardFactory;

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.BUBBLE, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final BubbleSendPayload bubbleSendPayload = sendMessage.getPayloadWithType(BubbleSendPayload.class);

		final Bubble bubble = bubbleSendPayload.getBubbles().get(0);

		final List<InlineKeyboardButton> buttonList = createButtonList(bubble);
		InlineKeyboardButton[] inlineKeyboardButtons = new InlineKeyboardButton[buttonList.size()];
		inlineKeyboardButtons = buttonList.toArray(inlineKeyboardButtons);
		final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(inlineKeyboardButtons);

		final String displayText = createTitle(bubble);
		final String recipient = sendMessage.getRecipient().getId();

		final com.pengrad.telegrambot.request.SendMessage sendMessageTelegram = new com.pengrad.telegrambot.request.SendMessage(
				recipient, displayText).replyMarkup(inlineKeyboardMarkup);

		super.execute(sendMessageTelegram, recipient);
	}

	protected List<InlineKeyboardButton> createButtonList(final Bubble bubble) {
		final List<InlineKeyboardButton> result = new ArrayList<InlineKeyboardButton>();

		for (final AbstractSendButton button : bubble.getButtons()) {
			result.add(telegramSendInlineKeyboardFactory.createInlineKeyboard(button));
		}

		return result;
	}

	protected String createTitle(final Bubble bubble) {
		String title = bubble.getTitle();
		final String text = bubble.getText();

		if (Strings.isBlank(title) && Strings.isBlank(text)) {
			title = "undefined title of bubble";
		}

		final StringBuilder stringBuilder = new StringBuilder();
		final String result = stringBuilder.append(StringUtils.defaultString(title)).append(" ")
				.append(StringUtils.defaultString(text)).toString();
		return result;
	}
}
