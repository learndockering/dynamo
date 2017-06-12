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
package com.ocs.dynamo.converter;

import java.util.Date;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.ocs.dynamo.utils.DateUtils;

/**
 * For converting a LocalTime to a Date for persisting
 * 
 * @author bas.rutten
 *
 */
@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalTime locTime) {
		return locTime == null ? null : DateUtils.toLegacyTime(locTime);
	}

	@Override
	public LocalTime convertToEntityAttribute(Date date) {
		return date == null ? null : DateUtils.toLocalTime(date);
	}

}