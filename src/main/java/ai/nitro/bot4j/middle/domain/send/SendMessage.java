/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send;

import ai.nitro.bot4j.middle.domain.AbstractMessage;
import ai.nitro.bot4j.middle.domain.send.payload.AbstractSendPayload;

public class SendMessage extends AbstractMessage {

	protected AbstractSendPayload payload;

	public AbstractSendPayload getPayload() {
		return payload;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractSendPayload> T getPayloadWithType(final Class<T> clazz) {
		final T result;

		if (payload.getClass().isAssignableFrom(clazz)) {
			result = (T) payload;
		} else {
			result = null;
		}

		return result;
	}

	public void setPayload(final AbstractSendPayload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return super.toString() + ", payload=[" + payload + "]";
	}

}
