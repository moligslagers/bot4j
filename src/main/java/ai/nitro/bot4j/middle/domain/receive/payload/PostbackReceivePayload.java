/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.payload;

public class PostbackReceivePayload extends AbstractReceivePayload {

	protected String name;

	protected String[] payload;

	public PostbackReceivePayload() {
		super(Type.POSTBACK);
	}

	public String getName() {
		return name;
	}

	public String[] getPayload() {
		return payload;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPayload(final String[] payload) {
		this.payload = payload;
	}

}
