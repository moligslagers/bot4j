/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.config.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.integration.facebook.config.FacebookConfig;

@Singleton
public class FacebookConfigImpl implements FacebookConfig {

	protected static final String ACCESS_TOKEN_KEY = "access_token";

	protected static final String FACEBOOK_PROPERTIES_FILENAME = "/facebook.properties";

	protected static final Logger LOG = LogManager.getLogger(FacebookConfigImpl.class);

	protected static final String PAGE_ID_KEY = "page_id";

	protected String accessToken;

	protected final Properties configFile = new Properties();

	protected String pageId;

	public FacebookConfigImpl() {
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
	public String getPageId() {
		if (pageId == null) {
			pageId = configFile.getProperty(PAGE_ID_KEY);
		}

		return pageId;
	}

	protected void loadProperties() {
		try {
			final InputStream resourceStream = getClass().getResourceAsStream(FACEBOOK_PROPERTIES_FILENAME);
			configFile.load(resourceStream);
		} catch (final Exception e) {
			LOG.warn("Could not load {}", FACEBOOK_PROPERTIES_FILENAME);
		}
	}

}
