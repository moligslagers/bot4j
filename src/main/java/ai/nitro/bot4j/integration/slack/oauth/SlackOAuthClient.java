/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.oauth;

import java.io.IOException;

import com.github.seratch.jslack.api.methods.SlackApiException;

public interface SlackOAuthClient {

	String getBotAccessToken(String code, String clientId, String clientSecret) throws IOException, SlackApiException;
}
