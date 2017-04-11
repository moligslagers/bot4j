/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain;

public class Participant {

	protected String id;

	protected Platform platform;

	public String getId() {
		return id;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setPlatform(final Platform platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "id=[" + id + "], platform=[" + platform + "]";
	}
}
