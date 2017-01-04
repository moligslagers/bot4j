/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.integration.slack.config.SlackConfig;

public class SlackConfigTest extends TestBase {

	@Inject
	protected SlackConfig slackConfig;

	@Test
	public void testAccessToken() throws Exception {
		final String accessToken = slackConfig.getAccessToken();
		assertNotNull(accessToken);
	}
}
