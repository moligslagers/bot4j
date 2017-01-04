/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.config.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.integration.slack.config.SlackConfig;

@Singleton
public class SlackConfigImpl implements SlackConfig {

	protected static final String ACCESS_TOKEN_KEY = "access_token";

	protected static final String CLIENT_ID_KEY = "client_id";

	protected static final String CLIENT_SECRET_KEY = "client_secret";

	protected static final Logger LOG = LogManager.getLogger(SlackConfigImpl.class);

	protected static final String SLACK_PROPERTIES_FILENAME = "/slack.properties";

	protected static final String USERNAME_KEY = "username";

	protected String accessToken;

	protected String clientId;

	protected String clientSecret;

	protected final Properties configFile = new Properties();

	protected String username;

	public SlackConfigImpl() {
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
	public String getClientId() {
		if (clientId == null) {
			clientId = configFile.getProperty(CLIENT_ID_KEY);
		}

		return clientId;
	}

	@Override
	public String getClientSecret() {
		if (clientSecret == null) {
			clientSecret = configFile.getProperty(CLIENT_SECRET_KEY);
		}

		return clientSecret;
	}

	@Override
	public String getUsername() {
		if (username == null) {
			username = configFile.getProperty(USERNAME_KEY);
		}

		return username;
	}

	protected void loadProperties() {
		try {
			final InputStream resourceStream = getClass().getResourceAsStream(SLACK_PROPERTIES_FILENAME);
			configFile.load(resourceStream);
		} catch (final Exception e) {
			LOG.warn("Could not load {}", SLACK_PROPERTIES_FILENAME);
		}
	}

}
