/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.template;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;

import ai.nitro.bot4j.TestBase;
import ai.nitro.bot4j.template.TemplateService;

public class TemplateServiceTest extends TestBase {

	@Inject
	protected TemplateService templateService;

	@Test
	public void testFormat() throws Exception {
		final String template = "This {0} a {1} template";
		final String result = templateService.format(template, "is", "test");
		assertEquals("This is a test template", result);
	}
}
