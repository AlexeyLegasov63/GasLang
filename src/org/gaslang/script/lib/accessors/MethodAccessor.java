package org.gaslang.script.lib.accessors;

import static org.gaslang.script.api.ScriptAPI.NULL;
import static org.gaslang.script.api.ScriptAPI.value;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import org.gaslang.script.FunctionBlock;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.ValueType;
import org.gaslang.script.exception.ValueMatchingTypeError;
import org.gaslang.script.run.GasRuntime;

abstract class MethodAccessor implements FunctionBlock
{
	protected final Method nativeMethod;
	protected final Object objectInstance;

	protected final Class<?>[] argumentsTypes;
	
	MethodAccessor(Method nativeMethod, Object objectInstance) {
		this.nativeMethod = nativeMethod;
		this.objectInstance = objectInstance;
		this.argumentsTypes = nativeMethod.getParameterTypes();
	}
	
	protected Object tryCastValue(Value<?> value, Class<?> requestedType, Type genericType) {
		if (requestedType.isAssignableFrom(Optional.class)) {
			if (value.matchValueType(ValueType.NONE)) {
				return Optional.empty();
			}
			return Optional.of(getNumber(value.jValue(), (Class<?>)((ParameterizedType)genericType).getActualTypeArguments()[0]));
		} else if (value.matchValueType(ValueType.NONE)) {
			return NULL;
		} else if (requestedType.isAssignableFrom(value.getClass())) {
			return value;
		}
		return tryCastPrimitive(value, requestedType);
	}
	
	protected Object tryCastPrimitive(Value<?> value, Class<?> requestedType) {
		Object jValueObject = value.jValue();
		
		// For different number types: Double, Integer, Float...
		if (jValueObject instanceof Number) {
			return getNumber(jValueObject, requestedType);
		} else if (requestedType.isAssignableFrom(jValueObject.getClass())) {
			return jValueObject;
		}
		throw new ValueMatchingTypeError(String.format("Type matching error: Got %s instead of %s", value.getClass().getSimpleName(), requestedType.getSimpleName())); 
	}
	
	protected Object getNumber(Object jValueObject, Class<?> requestedType) {
		Number numberValue = (Number)jValueObject;

		
		if (requestedType == int.class || requestedType == Integer.class) {
			return numberValue.intValue();
		} else if (requestedType == double.class || requestedType == Double.class) {
			return numberValue.doubleValue();
		} else if (requestedType == float.class || requestedType == Float.class) {
			return numberValue.floatValue();
		} else if (requestedType == long.class || requestedType == Long.class) {
			return numberValue.longValue();
		} else if (requestedType == short.class || requestedType == Short.class) {
			return numberValue.shortValue();
		} else if (requestedType == byte.class || requestedType == Byte.class) {
			return numberValue.byteValue();
		}
		throw new RuntimeException(String.format("%s %s", jValueObject, requestedType));
	}
}
