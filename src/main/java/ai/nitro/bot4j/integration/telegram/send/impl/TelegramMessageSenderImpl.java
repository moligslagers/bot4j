/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.send.impl;

import java.util.Set;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.nitro.bot4j.integration.telegram.domain.TelegramPlatformEnum;
import ai.nitro.bot4j.integration.telegram.send.TelegramMessageSender;
import ai.nitro.bot4j.integration.telegram.send.rules.TelegramSendRule;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.send.SendMessage;

public class TelegramMessageSenderImpl implements TelegramMessageSender {

	final static Logger log = LogManager.getLogger(TelegramMessageSenderImpl.class);

	@Inject
	protected Set<TelegramSendRule> rules;

	@Override
	public Platform getPlatform() {
		return TelegramPlatformEnum.TELEGRAM;
	}

	@Override
	public boolean send(final SendMessage sendMessage) {
		boolean result = false;

		if (rules != null) {
			for (final TelegramSendRule rule : rules) {
				final boolean applies = rule.applies(sendMessage);

				if (applies) {
					log.info("applying send rule {}", rule);

					rule.apply(sendMessage);
					result = true;
					break;
				}
			}
		}

		return result;
	}

}
