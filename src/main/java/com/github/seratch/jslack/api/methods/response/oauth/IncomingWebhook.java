/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package com.github.seratch.jslack.api.methods.response.oauth;

public class IncomingWebhook {

	protected String channel;

	protected String configurationUrl;

	protected String url;

	public String getChannel() {
		return channel;
	}

	public String getConfigurationUrl() {
		return configurationUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}

	public void setConfigurationUrl(final String configurationUrl) {
		this.configurationUrl = configurationUrl;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "channel=[" + channel + "], url=[" + url + "], configurationUrl=[" + configurationUrl + "]";
	}
}
