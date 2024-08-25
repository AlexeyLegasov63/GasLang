package org.gaslang.script;

public class CharacterValue extends Value<Character> implements PrimitiveValue
{
	public CharacterValue(Character arg0) {
		super(arg0, ValueType.CHAR);
	}

	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		return new BooleanValue(matchPrimitiveType(arg0, "char"));
	}
}
