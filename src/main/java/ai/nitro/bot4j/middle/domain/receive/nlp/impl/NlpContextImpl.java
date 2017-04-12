/*
 * Copyright (C) 2017, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.nlp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.nitro.bot4j.middle.domain.receive.nlp.NlpContext;

public class NlpContextImpl implements NlpContext {

	protected Double confidence;

	protected String intent;

	protected Map<String, List<String>> namedEntities = new HashMap<String, List<String>>();

	@Override
	public void addNamedEntity(final String name, final String value) {
		assureNamedEntityList(name);

		namedEntities.get(name).add(value);
	}

	protected void assureNamedEntityList(final String name) {
		if (namedEntities.get(name) == null) {
			namedEntities.put(name, new ArrayList<String>());
		}
	}

	@Override
	public Double getConfidence() {
		return confidence;
	}

	@Override
	public String getIntent() {
		return intent;
	}

	@Override
	public Map<String, List<String>> getNamedEntities() {
		return namedEntities;
	}

	@Override
	public void setConfidence(final Double confidence) {
		this.confidence = confidence;
	}

	@Override
	public void setIntent(final String intent) {
		this.intent = intent;
	}

	@Override
	public void setNamedEntities(final Map<String, List<String>> namedEntities) {
		this.namedEntities = namedEntities;
	}
}
