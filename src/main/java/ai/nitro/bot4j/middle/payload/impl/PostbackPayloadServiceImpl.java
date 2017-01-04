/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.payload.impl;

import com.google.gson.Gson;

import ai.nitro.bot4j.middle.payload.PostbackPayload;
import ai.nitro.bot4j.middle.payload.PostbackPayloadService;

public class PostbackPayloadServiceImpl implements PostbackPayloadService {

	@Override
	public PostbackPayload deserialize(final String serializedPayload) {
		final Gson gson = new Gson();
		final PostbackPayload result = gson.fromJson(serializedPayload, PostbackPayload.class);
		return result;
	}

	@Override
	public String serialize(final PostbackPayload postbackPayload) {
		final Gson gson = new Gson();
		final String result = gson.toJson(postbackPayload);
		return result;
	}

}
