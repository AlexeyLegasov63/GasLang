package org.gaslang.script.lib.nativetypes;

import org.gaslang.script.Array;
import org.gaslang.script.BooleanValue;
import org.gaslang.script.Value;
import org.gaslang.script.ValueType;

import java.util.HashMap;

public class StrictArrayValue extends Value<Value<?>[]> implements Array
{
	public StrictArrayValue(Value<?>[] arg0) {
		super(arg0, ValueType.ARRAY);
	}

	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "array"));
	}
	
	@Override
	public String asString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append('{');
		for (Value<?> value : jValue()) {
			buffer.append(String.format(" %s ", value));
		}
		buffer.append('}');
		return buffer.toString();
	}
	
	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.NUMBER);
		int index = arg0.asNumber().intValue();
		
		if (index >= getSize() || index < 0) throw new RuntimeException();
		
		return jValue()[index];
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		arg0.matchValueTypeOrThrow(ValueType.NUMBER);
		int index = arg0.asNumber().intValue();
		
		if (index >= getSize() || index < 0) throw new RuntimeException();
		
		jValue()[index] = arg1;
		
		return arg1;
	}

	@Override
	public int getSize() {
		return jValue().length;
	}

	@Override
	public HashMap<Integer, Value<?>> getMap() {
		HashMap<Integer, Value<?>> map = new HashMap<>();
		
		for (int i = 0, l = getSize(); i < l; i++) {
			map.put(i, jValue()[i]);
		}
		
		return map;
	}
}
