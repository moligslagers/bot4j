package ai.nitro.bot4j.integration.telegram.receive.webhook.impl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetWebhook;

import ai.nitro.bot4j.integration.telegram.config.TelegramConfig;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveHandler;
import ai.nitro.bot4j.integration.telegram.receive.webhook.TelegramWebhook;

public class TelegramWebhookImpl implements TelegramWebhook {

	private final static Logger LOG = LogManager.getLogger(TelegramWebhookImpl.class);

	@Inject
	protected TelegramBot bot;

	@Inject
	protected TelegramConfig telegramConfig;

	@Inject
	protected TelegramReceiveHandler telegramReceiveHandler;

	private void handleException(final Exception e) {
		LOG.error(e.getMessage(), e);
	}

	@Inject
	protected void init() {
		final SetWebhook request = new SetWebhook().url(telegramConfig.getWebhookUrl());
		bot.execute(request);
	}

	@Override
	public String post(final HttpServletRequest req, final HttpServletResponse res) {
		LOG.info("received Message");
		final String result = "";
		try {
			final Update update = BotUtils.parseUpdate(req.getReader());
			telegramReceiveHandler.handleUpdateMessage(update);
		} catch (final Exception e) {
			handleException(e);
		}
		return result;
	}
}
