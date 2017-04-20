/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive.key;

import ai.nitro.bot4j.middle.domain.Platform;

public class MessageKey {

	protected final String messageId;

	protected final Platform platform;

	public MessageKey(final Platform platform, final String messageId) {
		this.platform = platform;
		this.messageId = messageId;
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
			final MessageKey other = (MessageKey) obj;

			if (!platform.equals(other.platform)) {
				result = false;
			} else if (!messageId.equals(other.messageId)) {
				result = false;
			} else {
				result = true;
			}
		}

		return result;
	}

	@Override
	public int hashCode() {
		final int result = messageId.hashCode() + platform.hashCode();
		return result;
	}

}
