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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.Session;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.SessionManager;
import ai.nitro.bot4j.middle.receive.key.SenderKey;

@Singleton
public class SessionManagerImpl implements SessionManager {

	protected final Cache<SenderKey, Session> sessionCache = CacheBuilder.newBuilder()
			.expireAfterAccess(60, TimeUnit.MINUTES).build();

	@Override
	public Session getSession(final ReceiveMessage receiveMessage) {
		final Session result;

		if (receiveMessage == null) {
			result = null;
		} else {
			final Participant sender = receiveMessage.getSender();

			if (sender == null) {
				result = null;
			} else if (sender.getId() == null) {
				result = null;
			} else if (sender.getPlatform() == null) {
				result = null;
			} else {
				final Platform platform = sender.getPlatform();
				final String senderId = sender.getId();
				final SenderKey key = new SenderKey(platform, senderId);
				Session session = sessionCache.getIfPresent(key);

				if (session == null) {
					session = new Session();
					sessionCache.put(key, session);
				}

				result = session;
			}
		}

		return result;
	}

}
