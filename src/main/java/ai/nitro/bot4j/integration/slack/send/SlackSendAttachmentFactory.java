/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.send;

import com.github.seratch.jslack.api.model.ActionAttachment;
import com.github.seratch.jslack.api.model.Attachment;

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.button.PostbackSendButton;
import ai.nitro.bot4j.middle.domain.send.button.WebSendButton;

public interface SlackSendAttachmentFactory {

	Attachment createAttachment(AbstractSendButton button);

	ActionAttachment createAttachment(PostbackSendButton postbackSendButton);

	Attachment createAttachment(WebSendButton webSendButton);

}
