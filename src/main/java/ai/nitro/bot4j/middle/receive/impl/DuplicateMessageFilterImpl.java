/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive.impl;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import org.apache.logging.log4j.util.Strings;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.DuplicateMessageFilter;
import ai.nitro.bot4j.middle.receive.key.MessageKey;

@Singleton
public class DuplicateMessageFilterImpl implements DuplicateMessageFilter {

	protected final Cache<MessageKey, Boolean> messageCache = CacheBuilder.newBuilder()
			.expireAfterWrite(5, TimeUnit.MINUTES).build();

	@Override
	public boolean isDuplicate(final ReceiveMessage receiveMessage) {
		final boolean result;

		if (receiveMessage == null) {
			result = false;
		} else {
			final String messageId = receiveMessage.getMessageId();
			final Participant sender = receiveMessage.getSender();

			if (Strings.isBlank(messageId)) {
				result = false;
			} else if (sender == null) {
				result = false;
			} else if (sender.getPlatform() == null) {
				result = false;
			} else {
				final Platform platform = sender.getPlatform();
				final MessageKey key = new MessageKey(platform, messageId);
				final Boolean isPresent = messageCache.getIfPresent(key);

				messageCache.put(key, true);
				result = isPresent != null;
			}
		}

		return result;
	}

}
