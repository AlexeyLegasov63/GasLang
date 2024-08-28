package org.gaslang.script.lib.accessors;

import org.gaslang.script.Tuple;
import org.gaslang.script.Value;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.gaslang.script.api.ScriptAPI.NULL;
import static org.gaslang.script.api.ScriptAPI.value;

public class StrictAccessor extends MethodAccessor
{
	public StrictAccessor(Method nativeMethod, Object objectInstance) {
		super(nativeMethod, objectInstance);
	}

	@Override
	public Value<?> execute(Tuple tuple) {
		try {
			int arrayLength = argumentsTypes.length;
			
			if (arrayLength == 1 && argumentsTypes[0].equals(Tuple.class)) {
				return value(nativeMethod.invoke(objectInstance, tuple));
			}
			
			if (arrayLength != tuple.getSize()) {
				throw new RuntimeException();
			}
			
			Value<?>[] rawArguments = tuple.getValuesAsArray(arrayLength);
			Object[] arguments = new Object[arrayLength];
			
			for (int i = 0; i < arrayLength; i++) {
				Class<?> paramClass = argumentsTypes[i];
				Type paramType = nativeMethod.getGenericParameterTypes()[i];
				arguments[i] = paramClass.cast(tryCastValue(rawArguments[i], paramClass, paramType));
			}
			
			return value(nativeMethod.invoke(objectInstance, arguments));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return NULL;
	}
	
}
