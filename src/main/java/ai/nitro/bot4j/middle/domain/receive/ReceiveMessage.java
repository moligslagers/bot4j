/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive;

import java.util.ArrayList;
import java.util.List;

import ai.nitro.bot4j.middle.domain.AbstractMessage;
import ai.nitro.bot4j.middle.domain.Session;
import ai.nitro.bot4j.middle.domain.receive.payload.AbstractReceivePayload;

public class ReceiveMessage extends AbstractMessage {

	protected List<AbstractReceivePayload> payloads = new ArrayList<AbstractReceivePayload>();

	protected Session session;

	public void addPayload(final AbstractReceivePayload payload) {
		payload.setReceiveMessage(this);
		payloads.add(payload);
	}

	public void addPayloads(final List<AbstractReceivePayload> payloads) {
		for (final AbstractReceivePayload payload : payloads) {
			addPayload(payload);
		}
	}

	public List<AbstractReceivePayload> getPayloads() {
		return payloads;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractReceivePayload> List<T> getPayloadsWithType(final Class<T> clazz) {
		final List<T> result = new ArrayList<T>();

		for (final AbstractReceivePayload payload : payloads) {
			if (payload.getClass().isAssignableFrom(clazz)) {
				result.add((T) payload);
			}
		}

		return result;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(final Session session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return super.toString() + ", payloads=[" + payloads + "]";
	}

}
