/*
 * Copyright (C) 2017, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NlpContext {

	protected Double confidence;

	protected String intent;

	protected Map<String, List<String>> namedEntities = new HashMap<String, List<String>>();

	public void addNamedEntity(final String name, final String value) {
		assureNamedEntityList(name);

		namedEntities.get(name).add(value);
	}

	protected void assureNamedEntityList(final String name) {
		if (namedEntities.get(name) == null) {
			namedEntities.put(name, new ArrayList<String>());
		}
	}

	public Double getConfidence() {
		return confidence;
	}

	public String getIntent() {
		return intent;
	}

	public Map<String, List<String>> getNamedEntities() {
		return namedEntities;
	}

	public void setConfidence(final Double confidence) {
		this.confidence = confidence;
	}

	public void setIntent(final String intent) {
		this.intent = intent;
	}

	public void setNamedEntities(final Map<String, List<String>> namedEntities) {
		this.namedEntities = namedEntities;
	}

	@Override
	public String toString() {
		return "intent=[" + intent + "], confidence=[" + confidence + "]";
	}
}
