/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package com.github.seratch.jslack.api.model;

public class Action {

	protected String name;

	protected String style;

	protected String text;

	protected final String type = "button";

	protected String value;

	public String getName() {
		return name;
	}

	public String getStyle() {
		return style;
	}

	public String getText() {
		return text;
	}

	public String getValue() {
		return value;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStyle(final String style) {
		this.style = style;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
