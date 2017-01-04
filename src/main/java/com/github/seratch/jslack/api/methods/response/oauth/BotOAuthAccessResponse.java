/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package com.github.seratch.jslack.api.methods.response.oauth;

public class BotOAuthAccessResponse extends OAuthAccessResponse {

	protected Bot bot;

	protected IncomingWebhook incomingWebhook;

	public Bot getBot() {
		return bot;
	}

	public IncomingWebhook getIncomingWebhook() {
		return incomingWebhook;
	}

	void setBot(final Bot bot) {
		this.bot = bot;
	}

	public void setIncomingWebhook(final IncomingWebhook incomingWebhook) {
		this.incomingWebhook = incomingWebhook;
	}

	@Override
	public String toString() {
		return "bot=[" + bot + "], incomingWebhook=[" + incomingWebhook + "]";
	}
}
