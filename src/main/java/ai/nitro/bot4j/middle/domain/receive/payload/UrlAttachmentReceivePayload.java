/*
 * Copyright (C) 2016, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.payload;

public class UrlAttachmentReceivePayload extends AbstractReceivePayload {

	protected String attachmentType;

	protected String title;

	protected String url;

	public UrlAttachmentReceivePayload() {
		super(Type.URL_ATTACHMENT);
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public void setAttachmentType(final String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}
