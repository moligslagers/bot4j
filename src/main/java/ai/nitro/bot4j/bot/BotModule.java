/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.bot;

import com.google.inject.AbstractModule;

import ai.nitro.bot4j.bot.impl.BotImpl;

public class BotModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Bot.class).to(BotImpl.class);
	}

}
