/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package com.github.seratch.jslack.api.methods.response.oauth;

public class Bot {

	protected String botAccessToken;

	protected String botUserId;

	public String getBotAccessToken() {
		return botAccessToken;
	}

	public String getBotUserId() {
		return botUserId;
	}

	public void setBotAccessToken(final String botAccessToken) {
		this.botAccessToken = botAccessToken;
	}

	public void setBotUserId(final String botUserId) {
		this.botUserId = botUserId;
	}

	@Override
	public String toString() {
		return "botAccessToken=[" + botAccessToken + "], botUserId=[" + botUserId + "]";
	}
}
