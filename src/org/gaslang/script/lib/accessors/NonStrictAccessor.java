package org.gaslang.script.lib.accessors;

import org.gaslang.script.NullValue;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.gaslang.script.api.ScriptAPI.NULL;
import static org.gaslang.script.api.ScriptAPI.value;

public class NonStrictAccessor extends MethodAccessor
{
	public NonStrictAccessor(Method nativeMethod, Object moduleInstance) {
		super(nativeMethod, moduleInstance);
	}


	@Override
	public Value<?> execute(Tuple tuple) {
		try {
			int arrayLength = argumentsTypes.length;
			
			if (arrayLength == 1 && argumentsTypes[0].equals(Tuple.class)) {
				return value(nativeMethod.invoke(objectInstance, tuple));
			}
			
			Value<?>[] rawArguments = tuple.getValuesAsArray(arrayLength);
			Object[] arguments = new Object[arrayLength];
			
			for (int i = 0; i < arrayLength; i++) {
				Class<?> paramClass = argumentsTypes[i];
				Type paramType = nativeMethod.getGenericParameterTypes()[i];
				
				Object object = tryCastValue(rawArguments[i], paramClass, paramType);
				if (object instanceof NullValue) {
					arguments[i] = null;
					continue;
				}
				arguments[i] = paramClass.cast(object);
			}
			
			
			//System.out.println(Arrays.toString(nativeMethod.getParameterTypes()));
			//System.out.println(Arrays.toString(arguments));
			
			return value(nativeMethod.invoke(objectInstance, arguments));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return NULL;
	}
}
