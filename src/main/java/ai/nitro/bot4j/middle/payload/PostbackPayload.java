/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.payload;

import com.google.gson.annotations.SerializedName;

public class PostbackPayload {

	@SerializedName("Name")
	public String name;

	@SerializedName("Payload")
	public String[] payload;

	@Override
	public String toString() {
		return "name=[" + name + "], payload=[" + payload + "]";
	}
}
