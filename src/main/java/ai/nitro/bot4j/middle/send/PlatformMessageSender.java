/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.send;

import ai.nitro.bot4j.middle.domain.Platform;
import ai.nitro.bot4j.middle.domain.send.SendMessage;

public interface PlatformMessageSender {

	Platform getPlatform();

	boolean send(SendMessage sendMessage);

	boolean send(SendMessage sendMessage, Long botId);
}
