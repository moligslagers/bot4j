/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.bot.impl;

import javax.inject.Inject;

import ai.nitro.bot4j.bot.Bot;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.domain.receive.payload.AbstractReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.AbstractReceivePayload.Type;
import ai.nitro.bot4j.middle.domain.receive.payload.CoordinateReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.DeliveryNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.PostbackReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.QuickReplyReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.ReadNotificationReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.TextReceivePayload;
import ai.nitro.bot4j.middle.domain.receive.payload.UrlAttachmentReceivePayload;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.payload.ImageSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.TextSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.TypingSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.TypingSendPayload.Typing;
import ai.nitro.bot4j.middle.send.MessageSender;

public class BotImpl implements Bot {

	@Inject
	protected MessageSender messageSender;

	protected void onCoordinate(final CoordinateReceivePayload payload, final Participant sender) throws Exception {

	}

	@Override
	public void onMessage(final ReceiveMessage message) throws Exception {
		final Participant sender = message.getSender();

		for (final AbstractReceivePayload payload : message.getPayloads()) {
			onReceivePayload(payload, sender);
		}
	}

	protected void onReceiveAttachment(final UrlAttachmentReceivePayload payload, final Participant sender) {

	}

	protected void onReceiveDeliveryNotification(final DeliveryNotificationReceivePayload payload,
			final Participant sender) {

	}

	protected void onReceivePayload(final AbstractReceivePayload payload, final Participant sender) throws Exception {
		final Type type = payload.getType();

		switch (type) {
		case COORDINATE:
			onCoordinate((CoordinateReceivePayload) payload, sender);
			break;
		case DELIVERY_NOTIFICATION:
			onReceiveDeliveryNotification((DeliveryNotificationReceivePayload) payload, sender);
			break;
		case POSTBACK:
			onReceivePostback((PostbackReceivePayload) payload, sender);
			break;
		case QUICK_REPLY:
			onReceiveQuickReply((QuickReplyReceivePayload) payload, sender);
			break;
		case READ_NOTIFICATION:
			onReceiveReadNotification((ReadNotificationReceivePayload) payload, sender);
			break;
		case TEXT:
			onReceiveText((TextReceivePayload) payload, sender);
			break;
		case URL_ATTACHMENT:
			onReceiveAttachment((UrlAttachmentReceivePayload) payload, sender);
			break;
		default:
			break;
		}
	}

	protected void onReceivePostback(final PostbackReceivePayload payload, final Participant sender) throws Exception {

	}

	protected void onReceiveQuickReply(final QuickReplyReceivePayload payload, final Participant sender)
			throws Exception {

	}

	protected void onReceiveReadNotification(final ReadNotificationReceivePayload payload, final Participant sender)
			throws Exception {

	}

	protected void onReceiveText(final TextReceivePayload payload, final Participant sender) throws Exception {

	}

	protected void sendImage(final String title, final String imageUrl, final Participant recipient) {
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setRecipient(recipient);

		final ImageSendPayload imageSendPayload = new ImageSendPayload();
		imageSendPayload.setTitle(title);
		imageSendPayload.setImageUrl(imageUrl);
		sendMessage.setPayload(imageSendPayload);

		messageSender.send(sendMessage);
	}

	protected void sendText(final String text, final Participant recipient) {
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setRecipient(recipient);

		final TextSendPayload textSendPayload = new TextSendPayload();
		textSendPayload.setText(text);
		sendMessage.setPayload(textSendPayload);

		messageSender.send(sendMessage);
	}

	protected void sendTypingOff(final Participant recipient) {
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setRecipient(recipient);

		final TypingSendPayload typingSendPayload = new TypingSendPayload();
		typingSendPayload.setTyping(Typing.OFF);
		sendMessage.setPayload(typingSendPayload);

		messageSender.send(sendMessage);
	}

	protected void sendTypingOn(final Participant recipient) {
		final SendMessage sendMessage = new SendMessage();
		sendMessage.setRecipient(recipient);

		final TypingSendPayload typingSendPayload = new TypingSendPayload();
		typingSendPayload.setTyping(Typing.ON);
		sendMessage.setPayload(typingSendPayload);

		messageSender.send(sendMessage);
	}
}
