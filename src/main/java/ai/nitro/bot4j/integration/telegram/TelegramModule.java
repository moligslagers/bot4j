package ai.nitro.bot4j.integration.telegram;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

import ai.nitro.bot4j.integration.telegram.config.TelegramConfig;
import ai.nitro.bot4j.integration.telegram.config.impl.TelegramConfigImpl;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveHandler;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceiveMessageFactory;
import ai.nitro.bot4j.integration.telegram.receive.TelegramReceivePayloadFactory;
import ai.nitro.bot4j.integration.telegram.receive.impl.TelegramReceiveHandlerImpl;
import ai.nitro.bot4j.integration.telegram.receive.impl.TelegramReceiveMessageFactoryImpl;
import ai.nitro.bot4j.integration.telegram.receive.impl.TelegramReceivePayloadFactoryImpl;
import ai.nitro.bot4j.integration.telegram.receive.webhook.TelegramWebhook;
import ai.nitro.bot4j.integration.telegram.receive.webhook.impl.TelegramWebhookImpl;
import ai.nitro.bot4j.integration.telegram.send.TelegramMessageSender;
import ai.nitro.bot4j.integration.telegram.send.TelegramSendInlineKeyboardFactory;
import ai.nitro.bot4j.integration.telegram.send.impl.TelegramMessageSenderImpl;
import ai.nitro.bot4j.integration.telegram.send.impl.TelegramSendInlineKeyboardFactoryImpl;
import ai.nitro.bot4j.integration.telegram.send.rules.TelegramSendRule;
import ai.nitro.bot4j.integration.telegram.send.rules.impl.BubbleRuleImpl;
import ai.nitro.bot4j.integration.telegram.send.rules.impl.ButtonsRuleImpl;
import ai.nitro.bot4j.integration.telegram.send.rules.impl.ImageRuleImpl;
import ai.nitro.bot4j.integration.telegram.send.rules.impl.NativeRuleImpl;
import ai.nitro.bot4j.integration.telegram.send.rules.impl.TextRuleImpl;
import ai.nitro.bot4j.integration.telegram.send.rules.impl.VideoRuleImpl;

public class TelegramModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TelegramWebhook.class).to(TelegramWebhookImpl.class);
		bind(TelegramConfig.class).to(TelegramConfigImpl.class);
		bind(TelegramMessageSender.class).to(TelegramMessageSenderImpl.class);
		bind(TelegramSendInlineKeyboardFactory.class).to(TelegramSendInlineKeyboardFactoryImpl.class);
		bind(TelegramReceiveHandler.class).to(TelegramReceiveHandlerImpl.class);
		bind(TelegramReceiveMessageFactory.class).to(TelegramReceiveMessageFactoryImpl.class);
		bind(TelegramReceivePayloadFactory.class).to(TelegramReceivePayloadFactoryImpl.class);
		bind(TelegramWebhook.class).to(TelegramWebhookImpl.class);

		final Multibinder<TelegramSendRule> telegramSendRuleBinder = Multibinder.newSetBinder(binder(),
				TelegramSendRule.class);

		telegramSendRuleBinder.addBinding().to(BubbleRuleImpl.class);
		telegramSendRuleBinder.addBinding().to(ButtonsRuleImpl.class);
		telegramSendRuleBinder.addBinding().to(ImageRuleImpl.class);
		telegramSendRuleBinder.addBinding().to(NativeRuleImpl.class);
		telegramSendRuleBinder.addBinding().to(TextRuleImpl.class);
		telegramSendRuleBinder.addBinding().to(VideoRuleImpl.class);
	}

	@Provides
	protected TelegramBot provideTelegramClient(final TelegramConfig config) {
		final String telegramAccessToken = config.getAccessToken();
		final TelegramBot client = TelegramBotAdapter.build(telegramAccessToken);
		return client;
	}

}
