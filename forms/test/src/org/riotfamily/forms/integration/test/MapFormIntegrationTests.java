/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.riotfamily.forms.integration.test;

import java.util.Map;

import javax.annotation.Resource;

public class MapFormIntegrationTests extends AbstractFormIntegrationTests {

	@Resource
	private Map<String, Object> testMap;
	
	@Override
	protected String getControllerMapping() {
		return "form/map";
	}

	@Override
	protected Object getFieldValue(String field) {
		return testMap.get(field);
	}

}
