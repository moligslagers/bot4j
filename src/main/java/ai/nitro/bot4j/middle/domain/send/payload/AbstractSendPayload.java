/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.payload;

public abstract class AbstractSendPayload {

	public enum Type {
		BUBBLE, BUTTONS, IMAGE, LIST, QUICK_REPLIES, TEXT, TYPING, VIDEO
	}

	protected final Type type;

	public AbstractSendPayload(final Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

}
