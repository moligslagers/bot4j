/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.payload;

import java.util.ArrayList;
import java.util.List;

public class DeliveryNotificationReceivePayload extends AbstractReceivePayload {

	protected List<String> messageIds = new ArrayList<String>();

	protected String watermark;

	public DeliveryNotificationReceivePayload() {
		super(Type.DELIVERY_NOTIFICATION);
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public String getWatermark() {
		return watermark;
	}

	public void setMessageIds(final List<String> messageIds) {
		this.messageIds = messageIds;
	}

	public void setWatermark(final String watermark) {
		this.watermark = watermark;
	}

}
