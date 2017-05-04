/*
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.ocs.dynamo.ui.converter;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;


public class LongToDoubleConverter implements Converter<Double, Long> {

	private static final long serialVersionUID = 8666806617896024450L;

	@Override
	public Result<Long> convertToModel(Double value, ValueContext valueContext) {
		return Result.ok(value != null ? value.longValue() : null);
	}

	@Override
	public Double convertToPresentation(Long value, ValueContext valueContext) {
		return value != null ? value.doubleValue() : null;
	}
}
