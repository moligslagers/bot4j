/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j;

import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class TestBase {

	protected final double epsilon = 0.000001;

	protected final String jsonPayload = "{\n" +
			"\t'id': 1, \n" +
			"\t'name': 'ConfiguredBot', \n" +
			"\t'botType': 'ExampleBot', \n" +
			"\t'deploymentDestination': 'http://test.destinati.on/testBot', \n" +
			"\t'facebookSpec': {\n" +
			"\t\t'platformName': 'Facebook', \n" +
			"\t\t'accessToken': 'facebook_token'\n" +
			"\t\t},\n" +
			"\t'slackSpecPayload': {\n" +
			"\t\t'clientSecret': 'slack_secret', \n" +
			"\t\t'platformName': 'Slack', \n" +
			"\t\t'userName': 'slack_user', \n" +
			"\t\t'accessToken': 'slack_token', \n" +
			"\t\t'clientId': 'slack_client'\n" +
			"\t\t}, \n" +
			"\t'telegramSpecPayload': {\n" +
			"\t\t'webhookUrl': 'telegram_webhook', \n" +
			"\t\t'platformName': 'Telegram', \n" +
			"\t\t'accessToken': 'telegram_token'\n" +
			"\t\t}\n" +
			"}\n";

	protected final Injector injector = Guice.createInjector(new Bot4jTestModule());

	@Before
	public void setUp() throws Exception {
		injector.injectMembers(this);


	}

}
