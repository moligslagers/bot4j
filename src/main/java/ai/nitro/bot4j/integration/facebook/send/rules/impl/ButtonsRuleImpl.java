/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.rules.impl;

import javax.inject.Inject;

import com.restfb.types.send.ButtonTemplatePayload;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.TemplateAttachment;

import ai.nitro.bot4j.integration.facebook.send.FacebookSendButtonFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.ButtonsSendPayload;

public class ButtonsRuleImpl extends AbstractFacebookSendRuleImpl {

	@Inject
	protected FacebookSendButtonFactory facebookSendButtonFactory;

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.BUTTONS, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage) {
		final ButtonsSendPayload buttonsSendPayload = sendMessage.getPayloadWithType(ButtonsSendPayload.class);
		final ButtonTemplatePayload payload = new ButtonTemplatePayload(buttonsSendPayload.getTitle());

		for (final AbstractSendButton button : buttonsSendPayload.getButtons()) {
			payload.addButton(facebookSendButtonFactory.createAbstractButton(button));
		}

		final TemplateAttachment templateAttachment = new TemplateAttachment(payload);
		final Message message = new Message(templateAttachment);

		final IdMessageRecipient recipient = createIdMessageRecipient(sendMessage.getRecipient());
		publish(message, recipient);
	}

}
