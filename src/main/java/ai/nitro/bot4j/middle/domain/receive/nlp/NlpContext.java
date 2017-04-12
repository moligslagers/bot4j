/*
 * Copyright (C) 2017, nitro.ai
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package ai.nitro.bot4j.middle.domain.receive.nlp;

import java.util.List;
import java.util.Map;

public interface NlpContext {

	void addNamedEntity(String name, String value);

	Double getConfidence();

	String getIntent();

	Map<String, List<String>> getNamedEntities();

	void setConfidence(Double confidence);

	void setIntent(String intent);

	void setNamedEntities(Map<String, List<String>> namedEntities);
}
