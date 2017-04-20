/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive.key;

import ai.nitro.bot4j.middle.domain.Platform;

public class SenderKey {

	protected final String senderId;

	protected final Platform platform;

	public SenderKey(final Platform platform, final String senderId) {
		this.platform = platform;
		this.senderId = senderId;
	}

	@Override
	public boolean equals(final Object obj) {
		final boolean result;

		if (this == obj) {
			result = true;
		} else if (obj == null) {
			result = false;
		} else if (getClass() != obj.getClass()) {
			result = false;
		} else {
			final SenderKey other = (SenderKey) obj;

			if (!platform.equals(other.platform)) {
				result = false;
			} else if (!senderId.equals(other.senderId)) {
				result = false;
			} else {
				result = true;
			}
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int result = senderId.hashCode() + platform.hashCode();
		return result;
	}

}
