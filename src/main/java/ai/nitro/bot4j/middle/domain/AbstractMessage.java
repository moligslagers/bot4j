/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMessage {

	protected String messageId;

	protected transient final Map<Platform, Object> nativePayload = new HashMap<Platform, Object>();

	protected Participant recipient;

	protected Participant sender;

	public String getMessageId() {
		return messageId;
	}

	public Object getNativePayload(final Platform platform) {
		return nativePayload.get(platform);
	}

	public Participant getRecipient() {
		return recipient;
	}

	public Participant getSender() {
		return sender;
	}

	public void setMessageId(final String messageId) {
		this.messageId = messageId;
	}

	public void setNativePayload(final Platform platform, final Object payload) {
		nativePayload.put(platform, payload);
	}

	public void setRecipient(final Participant recipient) {
		this.recipient = recipient;
	}

	public void setSender(final Participant sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "messageId=[" + messageId + "], recipient=[" + recipient + "], sender=[" + sender + "]";
	}
}
