package org.gaslang.script;

import java.util.HashMap;

public class TableValue extends Value<HashMap<Value<?>, Value<?>>> implements Lengthable, Iterable, PrimitiveValue
{
	private TableValue metatable;
	
	public TableValue(HashMap<Value<?>, Value<?>> arg0) {
		this(arg0, null);
	}

	public TableValue(HashMap<Value<?>, Value<?>> arg0, TableValue arg1) {
		super(arg0);
		this.metatable = arg1;
	}
	
	public void clear() {
		jValue().clear();
	}

	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "table"));
	}
	
	@Override
	public String asString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append('{');
		for (Value<?> value : jValue().keySet()) {
			buffer.append(String.format(" %s = %s ", value, jValue().get(value)));
		}
		buffer.append('}');
		return buffer.toString();
	}
	
	@Override
	public Value<?> index(Value<?> arg0) {
		return jValue().get(arg0);
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		return jValue().put(arg0, arg1);
	}

	@Override
	public int getSize() {
		return jValue().size();
	}

	@Override
	public HashMap<?, ?> getMap() {
		return jValue();
	}
}
