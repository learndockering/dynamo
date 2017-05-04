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
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
/**
 * Converter for converting between a String (representing a week code) and a LocalDate
 * 
 * @author bas.rutten
 *
 */
public class LocalDateWeekCodeConverter implements Converter<String, LocalDate> {

    private static final long serialVersionUID = -3286439685884522679L;

    @Override
    public Result<LocalDate> convertToModel(String value, ValueContext valueContext) {
        return Result.ok(StringUtils.isEmpty(value) ? null : DateUtils.toStartDateOfWeek(value));
    }

    @Override
    public String convertToPresentation(LocalDate value, ValueContext valueContext) {
        return value == null ? null : DateUtils.toWeekCode(value);
    }
}
