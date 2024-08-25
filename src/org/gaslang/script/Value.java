package org.gaslang.script;

import static org.gaslang.script.ValueType.*;

import java.util.*;

import org.gaslang.script.api.*;
import org.gaslang.script.exception.ValueMatchingTypeError;
import org.gaslang.script.run.*;

public abstract class Value<T>
{
	protected T value;
	private final ValueType valueType;
	
	public Value(T arg0) {
		this(arg0, OBJECT);
	}

	public Value(T arg0, ValueType arg1) {
		this.value = arg0;
		this.valueType = arg1;
	}
	
	public T jValue() {
		return value;
	}
	
	public ValueType getValueType() {
		return valueType;
	}
	
	public boolean matchValueType(ValueType arg0) {
		return valueType.equals(arg0);
	}

	public boolean matchValueTypeOrThrow(ValueType arg0) throws ValueMatchingTypeError {
		if (matchValueType(arg0)) return true;
		else throw new ValueMatchingTypeError();
	}
	
	protected boolean matchPrimitiveType(Value<?> arg0, String arg1) {	
		return arg0 instanceof ScriptModule 
				&& ((ScriptModule)arg0).getName().equalsIgnoreCase(arg1);
	}
	
	public boolean isNull() {
		return false;
	}
	
	/*
	 * @Returns true because it exists
	 */
	public Boolean asBoolean() {
		return true;
	}
	
	public String asString() {
		return Objects.toString(value);
	}
	
	public Number asNumber() {
		throw new RuntimeException();
	}
	
	/*
	 * @Operator !
	 */
	public Value<?> inverse() {
		throw new RuntimeException();
	}

	/*
	 * @Operator -
	 */
	public Value<?> negate() {
		throw new RuntimeException();
	}

	/*
	 * @Operator +
	 */
	public Value<?> add(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator -
	 */
	public Value<?> sub(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator /
	 */
	public Value<?> div(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator *
	 */
	public Value<?> mul(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator %
	 */
	public Value<?> mod(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator wears of
	 */
	public BooleanValue wears(Value<?> arg0) {
		return ScriptAPI.FALSE;
	}

	/*
	 * @Operator instance of or "is"
	 */
	public BooleanValue instanceOf(Value<?> arg0) {
		return ScriptAPI.FALSE;
	}

	/*
	 * @Operator ==
	 */
	public BooleanValue equals(Value<?> arg0) {
		return new BooleanValue(Objects.equals(value, arg0.value));
	}

	/*
	 * @Operator !=
	 */
	public BooleanValue notEquals(Value<?> arg0) {
		//return new BooleanValue(!arg0.value.equals(value));
		return new BooleanValue(!Objects.equals(value, arg0.value));
	}

	/*
	 * @Operator >
	 */
	public BooleanValue moreThan(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator <
	 */
	public BooleanValue lessThan(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator <=
	 */
	public BooleanValue equalsOrLess(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator >=
	 */
	public BooleanValue equalsOrMore(Value<?> arg0) {
		throw new RuntimeException();
	}

	/*
	 * @Operator this[arg0] = arg1
	 */
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		throw new RuntimeException();
	}
	/*
	 * @Operator this[arg0]
	 */
	
	public Value<?> index(Value<?> arg0) {
		throw new RuntimeException(getClass().getSimpleName());
	}

	/*
	 * @Operator this(arg0)
	 */
	public Value<?> call(Tuple arg0) {
		throw new RuntimeException();
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Value && ((Value)o).value.equals(value);
	}
	@Override
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	@Override
	public String toString() {
		return asString();
	}
}
