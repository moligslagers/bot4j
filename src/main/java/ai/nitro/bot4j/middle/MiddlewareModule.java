/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle;

import ai.nitro.bot4j.integration.alexa.domain.AlexaPlatformEnum;
import ai.nitro.bot4j.integration.alexa.send.impl.AlexaMessageSenderImpl;
import ai.nitro.bot4j.integration.facebook.domain.FacebookPlatformEnum;
import ai.nitro.bot4j.integration.facebook.send.impl.FacebookMessageSenderImpl;
import ai.nitro.bot4j.integration.slack.domain.SlackPlatformEnum;
import ai.nitro.bot4j.integration.slack.send.impl.SlackMessageSenderImpl;
import ai.nitro.bot4j.integration.telegram.domain.TelegramPlatformEnum;
import ai.nitro.bot4j.integration.telegram.send.impl.TelegramMessageSenderImpl;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.payload.PostbackPayloadService;
import ai.nitro.bot4j.middle.payload.impl.PostbackPayloadServiceImpl;
import ai.nitro.bot4j.middle.receive.DuplicateMessageFilter;
import ai.nitro.bot4j.middle.receive.MessageReceiver;
import ai.nitro.bot4j.middle.receive.SessionManager;
import ai.nitro.bot4j.middle.receive.impl.DuplicateMessageFilterImpl;
import ai.nitro.bot4j.middle.receive.impl.MessageReceiverImpl;
import ai.nitro.bot4j.middle.receive.impl.SessionManagerImpl;
import ai.nitro.bot4j.middle.repo.StatefulBotProviderService;
import ai.nitro.bot4j.middle.repo.impl.StafefulBotProviderServiceImpl;
import ai.nitro.bot4j.middle.send.MessageSender;
import ai.nitro.bot4j.middle.send.PlatformMessageSender;
import ai.nitro.bot4j.middle.send.impl.MessageSenderImpl;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

public class MiddlewareModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DuplicateMessageFilter.class).to(DuplicateMessageFilterImpl.class);
        bind(MessageReceiver.class).to(MessageReceiverImpl.class);
        bind(MessageSender.class).to(MessageSenderImpl.class);
        bind(PostbackPayloadService.class).to(PostbackPayloadServiceImpl.class);
        bind(SessionManager.class).to(SessionManagerImpl.class);
        bind(StatefulBotProviderService.class).to(StafefulBotProviderServiceImpl.class);

        final MapBinder<Platform, PlatformMessageSender> platformMessageSenderBinder = MapBinder.newMapBinder(binder(),
                Platform.class, PlatformMessageSender.class);
        platformMessageSenderBinder.addBinding(FacebookPlatformEnum.FACEBOOK).to(FacebookMessageSenderImpl.class);
        platformMessageSenderBinder.addBinding(SlackPlatformEnum.SLACK).to(SlackMessageSenderImpl.class);
        platformMessageSenderBinder.addBinding(TelegramPlatformEnum.TELEGRAM).to(TelegramMessageSenderImpl.class);
        platformMessageSenderBinder.addBinding(AlexaPlatformEnum.ALEXA).to(AlexaMessageSenderImpl.class);
    }

}
