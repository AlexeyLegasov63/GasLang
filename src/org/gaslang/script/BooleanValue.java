package org.gaslang.script;

public class BooleanValue extends Value<Boolean> implements PrimitiveValue
{
	public BooleanValue(boolean arg0) {
		super(arg0, ValueType.BOOLEAN);
	}

	@Override
	public Value<?> inverse() {
		return new BooleanValue(!jValue());
	}

	@Override
	public Boolean asBoolean() {
		return jValue();
	}
	
	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "boolean"));
	}
}
