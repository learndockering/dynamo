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

import com.ocs.dynamo.utils.DateUtils;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import java.time.LocalDate;
import java.util.Date;

/**
 * Vaadin converter for converting a legacy date to a LocalDate
 * 
 * @author bas.rutten
 *
 */
public class LocalDateToDateConverter implements Converter<Date, LocalDate> {

	private static final long serialVersionUID = -830307549693107753L;

	@Override
	public Result<LocalDate> convertToModel(Date date, ValueContext valueContext) {
		return Result.ok(DateUtils.toLocalDate(date));
	}

	@Override
	public Date convertToPresentation(LocalDate localDate, ValueContext valueContext) {
		return DateUtils.toLegacyDate(localDate);
	}
}
