package org.gaslang.script;

import java.util.HashMap;

public class ArrayValue extends Value<HashMap<Integer, Value<?>>> implements Array
{
	public ArrayValue(HashMap<Integer, Value<?>> arg0) {
		super(arg0, ValueType.ARRAY);
	}
	
	public void clear() {
		jValue().clear();
	}

	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "array"));
	}
	
	@Override
	public String asString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append('{');
		for (Value<?> value : jValue().values()) {
			buffer.append(String.format(" %s ", value));
		}
		buffer.append('}');
		return buffer.toString();
	}
	
	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.NUMBER);
		return jValue().getOrDefault(arg0.asNumber().intValue(), NullValue.NIL_VALUE);
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		arg0.matchValueTypeOrThrow(ValueType.NUMBER);
		int index = arg0.asNumber().intValue();
		
		if (arg1.matchValueType(ValueType.NONE)) {
			return jValue().remove(index);
		}
		return jValue().put(index, arg1);
	}

	@Override
	public int getSize() {
		return jValue().size();
	}

	@Override
	public HashMap<Integer, Value<?>> getMap() {
		return jValue();
	}
}
