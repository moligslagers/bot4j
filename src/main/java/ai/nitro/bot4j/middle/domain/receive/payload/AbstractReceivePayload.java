/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.payload;

public abstract class AbstractReceivePayload {

	public enum Type {
		DELIVERY_NOTIFICATION, POSTBACK, QUICK_REPLY, READ_NOTIFICATION, TEXT, URL_ATTACHMENT
	}

	protected final Type type;

	public AbstractReceivePayload(final Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

}
