package org.gaslang.script;

public class NumberValue extends Value<Number> implements PrimitiveValue
{
	public NumberValue(Number arg0) {
		super(arg0, ValueType.NUMBER);
	}

	@Override
	public Number asNumber() {
		return jValue();
	}
	
	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "number"));
	}
	
	@Override
	public Value<?> negate() {
		return new NumberValue(-jValue().doubleValue());
	}
	
	@Override
	public Value<?> add(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.doubleValue() + y.doubleValue());
	}

	/*
	 * @Operator -
	 */
	@Override
	public Value<?> sub(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.doubleValue() - y.doubleValue());
	}

	/*
	 * @Operator /
	 */
	@Override
	public Value<?> div(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.doubleValue() / y.doubleValue());
	}

	/*
	 * @Operator *
	 */
	@Override
	public Value<?> mul(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.doubleValue() * y.doubleValue());
	}

	/*
	 * @Operator %
	 */
	@Override
	public Value<?> mod(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.doubleValue() % y.doubleValue());
	}


	/*
	 * @Operator &
	 */
	public Value<?> and(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.intValue() & y.intValue());
	}
	/*
	 * @Operator ^
	 */
	public Value<?> xor(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.intValue() ^ y.intValue());
	}
	/*
	 * @Operator |
	 */
	public Value<?> or(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.intValue() | y.intValue());
	}
	/*
	 * @Operator <<
	 */
	public Value<?> lshift(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.intValue() << y.intValue());
}
	/*
	 * @Operator >>
	 */
	public Value<?> rshift(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.intValue() >> y.intValue());
	}
	/*
	 * @Operator >>>
	 */
	public Value<?> urshift(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new NumberValue(x.intValue() >>> y.intValue());
	}

	/*
	 * @Operator >
	 */
	public BooleanValue moreThan(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new BooleanValue(x.doubleValue() > y.doubleValue());
	}

	/*
	 * @Operator <
	 */
	public BooleanValue lessThan(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new BooleanValue(x.doubleValue() < y.doubleValue());
	}

	/*
	 * @Operator <=
	 */
	public BooleanValue equalsOrLess(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new BooleanValue(x.doubleValue() <= y.doubleValue());
	}

	/*
	 * @Operator >=
	 */
	public BooleanValue equalsOrMore(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(getValueType());
		Number x = jValue(), y = (Number) arg0.jValue();
		return new BooleanValue(x.doubleValue() >= y.doubleValue());
	}
}
