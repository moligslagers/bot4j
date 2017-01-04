/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.payload;

public class ImageSendPayload extends AbstractSendPayload {

	protected String imageUrl;

	protected String title;

	public ImageSendPayload() {
		super(Type.IMAGE);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
