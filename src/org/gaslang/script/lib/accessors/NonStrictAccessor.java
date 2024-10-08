package org.gaslang.script.lib.accessors;

import org.gaslang.script.NullValue;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;

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
	public Value<?> execute(GasRuntime gasRuntime, Tuple tuple) {
		try {
			int arrayLength = argumentsTypes.length;
			
			if (arrayLength == 1 && argumentsTypes[0].equals(Tuple.class)) {
				return value(nativeMethod.invoke(objectInstance, tuple));
			}
			
			Value<?>[] rawArguments = tuple.getValuesAsArray(arrayLength);
			Object[] arguments = new Object[arrayLength];
			
			for (int i = 0; i < arrayLength; i++) {
				Class<?> paramClass = argumentsTypes[i];

				if (paramClass.isAssignableFrom(GasRuntime.class)) {
					arguments[i] = gasRuntime;
					continue;
				} else if (paramClass.isAssignableFrom(Tuple.class)) { // VarArgs
					arguments[i] = tuple.subTuple(i);
					break;
				}

				Type paramType = nativeMethod.getGenericParameterTypes()[i];
				Object object = tryCastValue(rawArguments[i], paramClass, paramType);
				if (object instanceof NullValue) {
					arguments[i] = null;
					continue;
				}
				arguments[i] = paramClass.cast(object);
			}

			return value(nativeMethod.invoke(objectInstance, arguments));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return NULL;
	}
}
