/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.send.impl;

import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.integration.facebook.domain.FacebookPlatformEnum;
import ai.nitro.bot4j.integration.facebook.send.FacebookMessageSender;
import ai.nitro.bot4j.integration.facebook.send.rules.FacebookSendRule;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.send.SendMessage;

public class FacebookMessageSenderImpl implements FacebookMessageSender {

	final static Logger LOG = LogManager.getLogger(FacebookMessageSenderImpl.class);

	@Inject
	protected Set<FacebookSendRule> rules;

	@Override
	public Platform getPlatform() {
		return FacebookPlatformEnum.FACEBOOK;
	}

	@Override
	public boolean send(final SendMessage sendMessage) {
		boolean result = false;

		if (rules != null) {
			for (final FacebookSendRule rule : rules) {
				final boolean applies = rule.applies(sendMessage);

				if (applies) {
					LOG.info("applying send rule {}", rule);

					rule.apply(sendMessage);
					result = true;
					break;
				}
			}
		}

		return result;
	}
}
