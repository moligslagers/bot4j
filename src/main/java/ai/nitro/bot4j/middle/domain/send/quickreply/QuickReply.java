/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.send.quickreply;

public class QuickReply {

	public enum ContentType {
		LOCATION, TEXT
	}

	protected ContentType contentType;

	protected String imageUrl;

	protected String payload;

	protected String title;

	public ContentType getContentType() {
		return contentType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getPayload() {
		return payload;
	}

	public String getTitle() {
		return title;
	}

	public void setContentType(final ContentType contentType) {
		this.contentType = contentType;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setPayload(final String payload) {
		this.payload = payload;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
