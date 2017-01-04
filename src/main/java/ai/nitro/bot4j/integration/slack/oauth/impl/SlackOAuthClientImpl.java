/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.slack.oauth.impl;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.seratch.jslack.api.methods.Methods;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.api.methods.response.oauth.Bot;
import com.github.seratch.jslack.api.methods.response.oauth.BotOAuthAccessResponse;
import com.github.seratch.jslack.common.http.SlackHttpClient;

import ai.nitro.bot4j.integration.slack.oauth.SlackOAuthClient;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Response;

public class SlackOAuthClientImpl implements SlackOAuthClient {

	private final static Logger LOG = LogManager.getLogger(SlackOAuthClientImpl.class);

	@Override
	public String getBotAccessToken(final String code, final String clientId, final String clientSecret)
			throws IOException, SlackApiException {
		// https://api.slack.com/docs/slack-button#
		final OAuthAccessRequest oAuthAccessRequest = OAuthAccessRequest.builder().clientId(clientId)
				.clientSecret(clientSecret).code(code).build();
		final BotOAuthAccessResponse oauthAccessResponse = oauthAccess(oAuthAccessRequest);

		LOG.info(oauthAccessResponse);

		final String result;

		if (!oauthAccessResponse.isOk()) {
			LOG.warn(oauthAccessResponse.getError());
			result = null;
		} else if (oauthAccessResponse.getBot() == null) {
			LOG.warn("bot access information missing. was 'bot' selected when creating the button?");
			result = null;
		} else if (oauthAccessResponse.getBot().getBotAccessToken() == null) {
			LOG.warn("bot access token missing");
			result = null;
		} else {
			final Bot bot = oauthAccessResponse.getBot();
			result = bot.getBotAccessToken();
		}

		return result;
	}

	protected BotOAuthAccessResponse oauthAccess(final OAuthAccessRequest req) throws IOException, SlackApiException {
		final FormBody.Builder form = new FormBody.Builder();

		setIfNotNull("client_id", req.getClientId(), form);
		setIfNotNull("client_secret", req.getClientSecret(), form);
		setIfNotNull("code", req.getCode(), form);
		setIfNotNull("redirect_uri", req.getRedirectUri(), form);

		final SlackHttpClient slackHttpClient = new SlackHttpClient();
		final Response response = slackHttpClient.postForm("https://slack.com/api/" + Methods.OAUTH_ACCESS,
				form.build());
		final BotOAuthAccessResponse oauthAccessResponse = SlackHttpClient.buildJsonResponse(response,
				BotOAuthAccessResponse.class);
		return oauthAccessResponse;
	}

	protected void setIfNotNull(final String name, final Object value, final FormBody.Builder form) {
		if (value != null) {
			form.add(name, String.valueOf(value));
		}
	}

	protected void setIfNotNull(final String name, final Object value, final MultipartBody.Builder form) {
		if (value != null) {
			form.addFormDataPart(name, String.valueOf(value));
		}
	}
}
