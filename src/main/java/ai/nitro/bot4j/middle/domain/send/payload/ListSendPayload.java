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

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;
import ai.nitro.bot4j.middle.domain.send.list.ListSendElement;

public class ListSendPayload extends AbstractSendPayload {

	public enum Style {
		COMPACT, LARGE
	}

	protected AbstractSendButton button;

	protected List<ListSendElement> listElements = new ArrayList<ListSendElement>();

	protected Style style;

	protected String title;

	public ListSendPayload() {
		super(Type.LIST);
	}

	public void addListElement(final ListSendElement listElement) {
		listElements.add(listElement);
	}

	public AbstractSendButton getButton() {
		return button;
	}

	public List<ListSendElement> getListElements() {
		return listElements;
	}

	public Style getStyle() {
		return style;
	}

	public String getTitle() {
		return title;
	}

	public void setButton(final AbstractSendButton button) {
		this.button = button;
	}

	public void setStyle(final Style style) {
		this.style = style;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
