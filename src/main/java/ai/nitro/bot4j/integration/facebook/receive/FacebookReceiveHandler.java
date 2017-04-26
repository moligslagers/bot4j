/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.integration.facebook.receive;

import com.restfb.types.webhook.messaging.MessagingItem;

public interface FacebookReceiveHandler {

	void handleMessagingItem(MessagingItem messagingItem, Long botId);

}
