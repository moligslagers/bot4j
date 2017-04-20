/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.receive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.junit.Test;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.integration.facebook.domain.FacebookPlatformEnum;
import ai.nitro.bot4j.integration.slack.domain.SlackPlatformEnum;
import ai.nitro.bot4j.middle.domain.Participant;
import ai.nitro.bot4j.middle.domain.Session;
import ai.nitro.bot4j.middle.domain.receive.ReceiveMessage;

public class SessionManagerTest extends TestBase {

	@Inject
	protected SessionManager sessionManager;

	@Test
	public void testGetSession() throws Exception {
		final Participant sender = new Participant();
		sender.setId("1");
		sender.setPlatform(FacebookPlatformEnum.FACEBOOK);

		final ReceiveMessage message = new ReceiveMessage();
		message.setSender(sender);

		assertNull(message.getSession());

		final Session session = sessionManager.getSession(message);
		assertNotNull(session);
	}

	@Test
	public void testGetSessionNull() throws Exception {
		final Session session = sessionManager.getSession(null);
		assertNull(session);
	}

	@Test
	public void testGetSessionTwiceFacebook() throws Exception {
		final Participant sender = new Participant();
		sender.setId("2");
		sender.setPlatform(FacebookPlatformEnum.FACEBOOK);

		final Session session1;

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setSender(sender);

			session1 = sessionManager.getSession(message);
			assertNotNull(session1);
		}

		{
			final ReceiveMessage message = new ReceiveMessage();
			message.setSender(sender);

			final Session session2 = sessionManager.getSession(message);
			assertNotNull(session2);
			assertEquals(session1, session2);
		}
	}

	@Test
	public void testGetSessionTwiceFacebookSlack() throws Exception {
		final String participantId = "3";
		final Session session1;

		{
			final Participant sender = new Participant();
			sender.setId(participantId);
			sender.setPlatform(FacebookPlatformEnum.FACEBOOK);

			final ReceiveMessage message = new ReceiveMessage();
			message.setSender(sender);

			session1 = sessionManager.getSession(message);
			assertNotNull(session1);
		}

		{
			final Participant sender = new Participant();
			sender.setId(participantId);
			sender.setPlatform(SlackPlatformEnum.SLACK);

			final ReceiveMessage message = new ReceiveMessage();
			message.setSender(sender);

			final Session session2 = sessionManager.getSession(message);
			assertNotNull(session2);

			assertNotEquals(session1, session2);
		}
	}
}
