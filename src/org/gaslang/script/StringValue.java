package org.gaslang.script;

import org.gaslang.script.api.ScriptModule;

public class StringValue extends Value<String> implements PrimitiveValue
{
	public StringValue(String arg0) {
		super(arg0, ValueType.STRING);
	}

	@Override
	public Value<?> add(Value<?> arg0) {
		String self = jValue();
		Object unchecked = arg0.jValue();
		return new StringValue(self + unchecked);
	}
	
	@Override
	public Value<?> mul(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.NUMBER);
		var builder = new StringBuilder();
		for (int i = 0; i < arg0.asNumber().intValue(); i++) {
			builder.append(jValue());
		}
		return new StringValue(builder.toString());
	}

	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.NUMBER);
		var index = arg0.asNumber().intValue();
		assert index > 0 && index < jValue().length();
		return new CharacterValue(jValue().charAt(index));
	}

	@Override
	public String toString() {
		return String.format("\"%s\"", jValue());
	}
	
	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "string"));
	}
}
