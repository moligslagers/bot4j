/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.template.impl;

import java.text.MessageFormat;
import java.util.Random;

import org.apache.logging.log4j.util.Strings;

import ai.nitro.bot4j.template.TemplateService;

public class TemplateServiceImpl implements TemplateService {

	protected final Random random = new Random();

	@Override
	public String format(final String template, final Object... params) {
		final String result = MessageFormat.format(template, params);
		return result;
	}

	@Override
	public String formatText(final String text) {
		final String result = !Strings.isBlank(text) ? text : "";
		return result;
	}

	@Override
	public String random(final String[] strings) {
		final int index = random.nextInt(strings.length);
		final String result = strings[index];
		return result;
	}

}
