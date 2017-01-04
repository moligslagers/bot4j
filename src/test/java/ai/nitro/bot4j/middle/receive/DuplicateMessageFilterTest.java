/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.integration.facebook.domain.FacebookPlatformEnum;
import ai.nitro.bot4j.integration.slack.domain.SlackPlatformEnum;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;
import ai.nitro.bot4j.middle.receive.DuplicateMessageFilter;

public class DuplicateMessageFilterTest extends TestBase {

	@Inject
	protected DuplicateMessageFilter duplicateMessageFilter;

	@Test
	public void testIsDuplicateEmpty() throws Exception {
		final ReceiveMessage message = new ReceiveMessage();
		message.setMessageId("   ");

		final Participant sender = new Participant();
		sender.setPlatform(FacebookPlatformEnum.FACEBOOK);
		message.setSender(sender);

		final boolean isDuplicate1 = duplicateMessageFilter.isDuplicate(message);
		assertFalse(isDuplicate1);

		final boolean isDuplicate2 = duplicateMessageFilter.isDuplicate(message);
		assertFalse(isDuplicate2);
	}

	@Test
	public void testIsDuplicateNull() throws Exception {
		final boolean isPresent1 = duplicateMessageFilter.isDuplicate(null);
		assertFalse(isPresent1);

		final boolean isDuplicate = duplicateMessageFilter.isDuplicate(null);
		assertFalse(isDuplicate);
	}

	@Test
	public void testIsDuplicatePlatformNull() throws Exception {
		final ReceiveMessage message = new ReceiveMessage();
		message.setMessageId("123456789");

		final Participant sender = new Participant();
		sender.setPlatform(null);
		message.setSender(sender);

		final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
		assertFalse(isDuplicate);
	}

	@Test
	public void testIsDuplicateTwiceFacebook() throws Exception {
		final String messageId = "123456789";
		final Platform platform = FacebookPlatformEnum.FACEBOOK;

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setMessageId(messageId);

			final Participant sender = new Participant();
			sender.setPlatform(platform);
			message.setSender(sender);

			final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
			assertFalse(isDuplicate);
		}

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setMessageId(messageId);

			final Participant sender = new Participant();
			sender.setPlatform(platform);
			message.setSender(sender);

			final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
			assertTrue(isDuplicate);
		}
	}

	@Test
	public void testIsDuplicateTwiceFacebookSlack() throws Exception {
		final String messageId = "123456789";

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setMessageId(messageId);

			final Participant sender = new Participant();
			sender.setPlatform(FacebookPlatformEnum.FACEBOOK);
			message.setSender(sender);

			final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
			assertFalse(isDuplicate);
		}

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setMessageId(messageId);

			final Participant sender = new Participant();
			sender.setPlatform(SlackPlatformEnum.SLACK);
			message.setSender(sender);

			final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
			assertFalse(isDuplicate);
		}
	}

	@Test
	public void testIsDuplicateTwiceSlack() throws Exception {
		final String messageId = "123456789";
		final Platform platform = SlackPlatformEnum.SLACK;

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setMessageId(messageId);

			final Participant sender = new Participant();
			sender.setPlatform(platform);
			message.setSender(sender);

			final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
			assertFalse(isDuplicate);
		}

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setMessageId(messageId);

			final Participant sender = new Participant();
			sender.setPlatform(platform);
			message.setSender(sender);

			final boolean isDuplicate = duplicateMessageFilter.isDuplicate(message);
			assertTrue(isDuplicate);
		}
	}

}
