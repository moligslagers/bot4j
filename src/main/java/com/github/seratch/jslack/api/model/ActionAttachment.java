/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package com.github.seratch.jslack.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * https://api.slack.com/docs/message-attachments
 */
public class ActionAttachment extends Attachment {

	protected List<Action> actions = new ArrayList<>();

	protected String callbackId;

	public ActionAttachment() {
		super(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
	}

	public List<Action> getActions() {
		return actions;
	}

	public String getCallbackId() {
		return callbackId;
	}

	public void setActions(final List<Action> actions) {
		this.actions = actions;
	}

	public void setCallbackId(final String callbackId) {
		this.callbackId = callbackId;
	}

}
