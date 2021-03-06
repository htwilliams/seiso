/* 
 * Copyright 2013-2015 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expedia.serf.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Data transfer object containing the results following a failed bean validation.
 * 
 * @author Ken van Eyk
 * @author Willie Wheeler
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceValidationError extends ErrorObject {
	private String uri;
	private Map<String, List<String>> fieldErrors;
	
	/**
	 * @param uri
	 *            Resource URI, or attempted resource URI.
	 */
	public ResourceValidationError(@NonNull String uri) {
		super("resource_validation_error", "See fieldErrors for detailed per-field information.");
		this.uri = uri;
		this.fieldErrors = new HashMap<String, List<String>>();
	}
	
	public void addFieldError(String fieldName, String errorMessage) {
		List<String> errorsForName = this.fieldErrors.get(fieldName);
		
		if (errorsForName == null) {
			errorsForName = new ArrayList<String>();
			fieldErrors.put(fieldName, errorsForName);
		}

		errorsForName.add(errorMessage);
	}
}