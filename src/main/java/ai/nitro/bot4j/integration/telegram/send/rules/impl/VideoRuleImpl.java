package ai.nitro.bot4j.integration.telegram.send.rules.impl;

import org.apache.logging.log4j.util.Strings;

import com.pengrad.telegrambot.request.SendVideo;

import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.VideoSendPayload;

public class VideoRuleImpl extends AbstractTelegramSendRuleImpl {

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.VIDEO, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final VideoSendPayload videoSendPayload = sendMessage.getPayloadWithType(VideoSendPayload.class);

		final String title = videoSendPayload.getTitle();
		final String videoUrl = videoSendPayload.getVideoUrl();
		final String recipient = sendMessage.getRecipient().getId();

		final SendVideo sendVideoTelegram = new SendVideo(recipient, videoUrl);

		if (!Strings.isBlank(title)) {
			sendVideoTelegram.caption(title);
		}

		LOG.info("sending video {} to recipient {}", sendVideoTelegram, recipient);
		client.execute(sendVideoTelegram);
	}

}
