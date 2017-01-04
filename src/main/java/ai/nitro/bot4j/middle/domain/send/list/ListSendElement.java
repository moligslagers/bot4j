/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.list;

import ai.nitro.bot4j.middle.domain.send.button.AbstractSendButton;

public class ListSendElement {

	protected AbstractSendButton button;

	protected String imageUrl;

	protected String subTitle;

	protected String title;

	protected String url;

	public AbstractSendButton getButton() {
		return button;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public void setButton(final AbstractSendButton button) {
		this.button = button;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setSubTitle(final String subTitle) {
		this.subTitle = subTitle;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}
