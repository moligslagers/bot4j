/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.payload;

public class QuickReplyReceivePayload extends AbstractReceivePayload {

	protected String payload;

	public QuickReplyReceivePayload() {
		super(Type.QUICK_REPLY);
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(final String payload) {
		this.payload = payload;
	}
}
