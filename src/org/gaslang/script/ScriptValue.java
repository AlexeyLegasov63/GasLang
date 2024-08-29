package org.gaslang.script;

public class ScriptValue extends Value<Script>
{
	public ScriptValue(Script value) {
		super(value);
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		jValue().set(arg0.asString(), arg1);
		return arg1;
	}

	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		return jValue().get(arg0.asString());
	}


	@Override
	public String asString() {
		return "script";
	}
	
}
