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

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;

public class ButtonsSendPayload extends AbstractSendPayload {

	protected final List<AbstractSendButton> buttons = new ArrayList<AbstractSendButton>();

	protected String title;

	public ButtonsSendPayload() {
		super(Type.BUTTONS);
	}

	public void addButton(final AbstractSendButton button) {
		buttons.add(button);
	}

	public List<AbstractSendButton> getButtons() {
		return buttons;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
}
