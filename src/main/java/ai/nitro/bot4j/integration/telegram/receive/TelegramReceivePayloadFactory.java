package ai.nitro.bot4j.integration.telegram.receive;

import com.pengrad.telegrambot.model.Audio;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Sticker;
import com.pengrad.telegrambot.model.Voice;

import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;

public interface TelegramReceivePayloadFactory {

	UrlAttachmentReceivePayload createAudio(Audio audio);

	UrlAttachmentReceivePayload createDocument(Message message);

	UrlAttachmentReceivePayload createPhoto(Message message);

	PostbackReceivePayload createPostback(CallbackQuery callbackQuery);

	UrlAttachmentReceivePayload createSticker(Sticker sticker);

	TextReceivePayload createTextPayload(String text);

	UrlAttachmentReceivePayload createVideo(Message message);

	UrlAttachmentReceivePayload createVoice(Voice voice);

}