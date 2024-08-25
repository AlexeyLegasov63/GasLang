package org.gaslang.script;

public class NullValue extends Value<Number> implements PrimitiveValue
{
	public static final NullValue NIL_VALUE = new NullValue();
	
	private NullValue() {
		super(null, ValueType.NONE);
	}
	
	/*
	 * @Returns false because not exists
	 */
	@Override
	public Boolean asBoolean() {
		return false;
	}
	
	/*
	 * @Operator ==
	 */
	public BooleanValue equals(Value<?> arg0) {
		return new BooleanValue(arg0.isNull());
	}

	/*
	 * @Operator !=
	 */
	public BooleanValue notEquals(Value<?> arg0) {
		return new BooleanValue(!arg0.isNull());
	}
	
	@Override
	public String asString() {
		return "null";
	}

	@Override
	public boolean isNull() {
		return true;
	}
}
