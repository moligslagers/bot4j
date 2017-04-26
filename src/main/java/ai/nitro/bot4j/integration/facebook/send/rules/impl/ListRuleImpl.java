/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.rules.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import com.restfb.types.send.DefaultAction;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.ListTemplatePayload;
import com.restfb.types.send.ListTemplatePayload.TopElementStyleEnum;
import com.restfb.types.send.ListViewElement;
import com.restfb.types.send.Message;
import com.restfb.types.send.TemplateAttachment;

import ai.nitro.bot4j.integration.facebook.send.FacebookSendButtonFactory;
import ai.nitro.bot4j.middle.domain.send.SendMessage;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.list.ListSendElement;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload.Type;
import ai.nitro.bot4j.middle.domain.send.payload.ListSendPayload;
import ai.nitro.bot4j.middle.domain.send.payload.ListSendPayload.Style;

public class ListRuleImpl extends AbstractFacebookSendRuleImpl {

	@Inject
	protected FacebookSendButtonFactory facebookSendButtonFactory;

	protected void addAction(final ListSendElement listSendElement, final ListViewElement listViewElement) {
		final String url = listSendElement.getUrl();

		if (!Strings.isBlank(url)) {
			listViewElement.setDefaultAction(new DefaultAction(url));
		}
	}

	protected void addButton(final ListSendElement listSendElement, final ListViewElement listViewElement) {
		final AbstractSendButton button = listSendElement.getButton();

		if (button != null) {
			listViewElement.addButton(facebookSendButtonFactory.createAbstractButton(button));
		}
	}

	protected void addButton(final ListSendPayload listSendPayload, final ListTemplatePayload payload) {
		final AbstractSendButton button = listSendPayload.getButton();

		if (button != null) {
			payload.addButton(facebookSendButtonFactory.createAbstractButton(button));
		}
	}

	@Override
	public boolean applies(final SendMessage sendMessage) {
		return hasPayloadType(Type.LIST, sendMessage);
	}

	@Override
	public void apply(final SendMessage sendMessage, Long botId) {
		final ListSendPayload listSendPayload = sendMessage.getPayloadWithType(ListSendPayload.class);

		final List<ListViewElement> listViewElements = new ArrayList<ListViewElement>();

		for (final ListSendElement listSendElement : listSendPayload.getListElements()) {
			final ListViewElement listViewElement = new ListViewElement(listSendElement.getTitle());
			listViewElement.setSubtitle(listSendElement.getSubTitle());
			listViewElement.setImageUrl(listSendElement.getImageUrl());

			addButton(listSendElement, listViewElement);
			addAction(listSendElement, listViewElement);

			listViewElements.add(listViewElement);
		}

		final ListTemplatePayload payload = new ListTemplatePayload(listViewElements);
		setStyle(listSendPayload, payload);
		addButton(listSendPayload, payload);

		final TemplateAttachment templateAttachment = new TemplateAttachment(payload);
		final Message message = new Message(templateAttachment);

		final IdMessageRecipient recipient = createIdMessageRecipient(sendMessage.getRecipient());
		publish(message, recipient, botId);
	}

	protected void setStyle(final ListSendPayload listSendPayload, final ListTemplatePayload payload) {
		final Style style = listSendPayload.getStyle();

		if (Style.COMPACT.equals(style)) {
			payload.setTopElementStyle(TopElementStyleEnum.compact);
		}
	}
}
