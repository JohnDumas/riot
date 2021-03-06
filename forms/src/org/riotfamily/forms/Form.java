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
package org.riotfamily.forms;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import org.riotfamily.forms.base.Element;
import org.riotfamily.forms.base.FormElement;
import org.riotfamily.forms.base.FormState;
import org.riotfamily.forms.base.StateEvent;
import org.riotfamily.forms.base.UserInterface;
import org.riotfamily.forms.client.ClientEvent;
import org.riotfamily.forms.client.Update;
import org.riotfamily.forms.value.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class Form {

	private FormElement formElement = new FormElement();
	
	private ConversionService conversionService = ConversionServiceFactory.createDefaultConversionService();
	
	public Form add(Element element) {
		formElement.add(element);
		return this;
	}
	
	public Form addExternal(Element element) {
		formElement.addExternal(element);
		return this;
	}
	
	public FormState createState(Object object, Class<?> type) {
		return formElement.createAndInitState(object, type);
	}
	
	public Object populate(Object object, FormState formState) {
		Value value = new Value(object);
		formState.populate(value);
		return value.get();
	}
	
	public String render(FormState formState) {
		return formState.render();
	}
	
	public FormState getState(HttpSession session, ClientEvent event) {
		return formElement.getState(session, event.getFormId());
	}
	
	public Update dispatchEvent(HttpSession session, ClientEvent event) {
		return dispatchEvent(getState(session, event), event);	
	}
	
	public Update dispatchEvent(FormState formState, ClientEvent event) {
		Element.State state = formState.getElementState(event.getStateId());
		UserInterface ui = new UserInterface(formState);
		invoke(state, event.getHandler(), ui, event.getFileOrValue());
		state.handleStateEvent(new StateEvent(state, event.getHandler(), ui));
		return ui.getUpdate();
	}
	
	private void invoke(Object obj, String name, Object... params) {
		Method method = getMethod(obj.getClass(), name);
		checkAndConvertTypes(params, method.getParameterTypes());				
		ReflectionUtils.invokeMethod(method, obj, params);
	}
	
	private static Method getMethod(Class<?> clazz, String name) {
		Method result = null;
		for (Method method : clazz.getMethods()) {
			if (name.equals(method.getName())) {
				Assert.isNull(result, "Class" + clazz + " must not define more than one method named " + name);
				result = method;
			}
		}
		Assert.notNull(result, "No such method '" + name + "' in " + clazz);
		return result;
	}

	private void checkAndConvertTypes(Object[] params, Class<?>[] types) {
		Assert.isTrue(params.length == types.length, "Wrong number of parameters");
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			if (param != null && !types[i].isInstance(param)) {
				Assert.isTrue(conversionService.canConvert(param.getClass(), types[i]), 
						"Cant convert " + param.getClass() + " into " + types[i]);
				
				params[i] = conversionService.convert(param, types[i]);
			}
		}
	}
	
}
