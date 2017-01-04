/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.payload;

import java.util.ArrayList;
import java.util.List;

import ai.nitro.bot4j.middle.domain.send.quickreply.QuickReply;

public class QuickRepliesSendPayload extends AbstractSendPayload {

	protected List<QuickReply> quickReplies = new ArrayList<QuickReply>();

	protected String text;

	public QuickRepliesSendPayload() {
		super(Type.QUICK_REPLIES);
	}

	public void addQuickReply(final QuickReply quickReply) {
		quickReplies.add(quickReply);
	}

	public List<QuickReply> getQuickReplies() {
		return quickReplies;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

}
