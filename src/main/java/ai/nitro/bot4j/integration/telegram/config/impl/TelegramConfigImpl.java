/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.config.impl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.integration.telegram.config.TelegramConfig;

public class TelegramConfigImpl implements TelegramConfig {

	protected static final String ACCESS_TOKEN_KEY = "access_token";

	protected static final Logger LOG = LogManager.getLogger(TelegramConfigImpl.class);

	protected static final String TELEGRAM_PROPERTIES_FILENAME = "/telegram.properties";

	protected static final String WEBHOOK_URL = "webhook_url";

	protected String accessToken;

	protected final Properties configFile = new Properties();

	protected String webhookUrl;

	public TelegramConfigImpl() {
		loadProperties();
	}

	@Override
	public String getAccessToken() {
		if (accessToken == null) {
			accessToken = configFile.getProperty(ACCESS_TOKEN_KEY);
		}

		return accessToken;
	}

	@Override
	public String getWebhookUrl() {
		if (webhookUrl == null) {
			webhookUrl = configFile.getProperty(WEBHOOK_URL);
		}

		return webhookUrl;
	}

	protected void loadProperties() {
		try {
			final InputStream resourceStream = getClass().getResourceAsStream(TELEGRAM_PROPERTIES_FILENAME);
			configFile.load(resourceStream);
		} catch (final Exception e) {
			LOG.warn("Could not load {}", TELEGRAM_PROPERTIES_FILENAME);
		}
	}

}
