/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.payload;

public class TypingSendPayload extends AbstractSendPayload {

	public enum Typing {
		OFF, ON
	}

	protected Typing typing;

	public TypingSendPayload() {
		super(Type.TYPING);
	}

	public Typing getTyping() {
		return typing;
	}

	public void setTyping(final Typing typing) {
		this.typing = typing;
	}

}
