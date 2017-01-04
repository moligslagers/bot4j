/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.payload;

import java.util.ArrayList;
import java.util.List;

import ai.nitro.bot4j.middle.domain.send.bubble.Bubble;

public class BubbleSendPayload extends AbstractSendPayload {

	protected List<Bubble> bubbles = new ArrayList<Bubble>();

	public BubbleSendPayload() {
		super(Type.BUBBLE);
	}

	public void addBubble(final Bubble bubble) {
		bubbles.add(bubble);
	}

	public List<Bubble> getBubbles() {
		return bubbles;
	}

}
