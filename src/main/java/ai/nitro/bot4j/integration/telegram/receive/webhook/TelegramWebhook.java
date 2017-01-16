/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.telegram.receive.webhook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TelegramWebhook {

	public String post(final HttpServletRequest req, final HttpServletResponse res);

}
