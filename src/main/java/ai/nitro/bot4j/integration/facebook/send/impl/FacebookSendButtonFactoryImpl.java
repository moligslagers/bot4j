/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.impl;

import javax.inject.Inject;

import com.restfb.types.send.AbstractButton;
import com.restfb.types.send.PostbackButton;
import com.restfb.types.send.WebButton;

import ai.nitro.bot4j.integration.facebook.send.FacebookSendButtonFactory;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton.Type;
import ai.nitro.bot4j.middle.domain.send.button.PostbackSendButton;
import ai.nitro.bot4j.middle.domain.send.button.WebSendButton;
import ai.nitro.bot4j.middle.payload.PostbackPayload;
import ai.nitro.bot4j.middle.payload.PostbackPayloadService;

public class FacebookSendButtonFactoryImpl implements FacebookSendButtonFactory {

	@Inject
	protected PostbackPayloadService postbackPayloadService;

	@Override
	public AbstractButton createAbstractButton(final AbstractSendButton abstractSendButton) {
		final Type buttonType = abstractSendButton.getType();
		final AbstractButton result;

		switch (buttonType) {
		case WEB_BUTTON:
			result = creatWebButton((WebSendButton) abstractSendButton);
			break;
		case POSTBACK_BUTTON:
			result = createPostbackButton((PostbackSendButton) abstractSendButton);
			break;
		default:
			result = null;
		}

		return result;
	}

	protected PostbackButton createPostbackButton(final PostbackSendButton postbackSendButton) {
		final String title = postbackSendButton.getTitle();
		final String name = postbackSendButton.getName();
		final String[] payload = postbackSendButton.getPayload();

		final PostbackPayload postbackPayload = new PostbackPayload();
		postbackPayload.name = name;
		postbackPayload.payload = payload;
		final String serializedPayload = postbackPayloadService.serialize(postbackPayload);

		final PostbackButton result = new PostbackButton(title, serializedPayload);
		return result;
	}

	protected WebButton creatWebButton(final WebSendButton webSendButton) {
		final String title = webSendButton.getTitle();
		final String webUrl = webSendButton.getUrl();
		final WebButton result = new WebButton(title, webUrl);
		return result;
	}

}
