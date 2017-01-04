/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send;

import com.restfb.types.send.AbstractButton;
import com.restfb.types.send.PostbackButton;
import com.restfb.types.send.WebButton;

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.button.PostbackSendButton;
import ai.nitro.bot4j.middle.domain.send.button.WebSendButton;

public interface FacebookSendButtonFactory {

	AbstractButton createAbstractButton(AbstractSendButton abstractSendButton);

	PostbackButton createPostbackButton(PostbackSendButton postbackSendButton);

	WebButton creatWebButton(WebSendButton webSendButton);
}
