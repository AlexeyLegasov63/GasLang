package org.gaslang.script;

import java.util.HashMap;

import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.api.ScriptType;
import org.gaslang.script.ast.IndexExpression;

public class ObjectValue extends Value<HashMap<String, Value<?>>> implements PrimitiveValue
{
	protected final ScriptType typeInstance;
	
	public ObjectValue(HashMap<String, Value<?>> value, ScriptType typeInstance) {
		super(value);
		this.typeInstance = typeInstance;
	}
	
	public ScriptType getType() {
		return typeInstance;
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		if (jValue().replace(arg0.asString(), arg1) == null) 
			throw new RuntimeException("There's no such variable");
		return arg1;
	}

	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		Value<?> value = jValue().get(arg0.asString());
		
		if (value == null) {
			return typeInstance.index(arg0);
		}
		
		return value;
	}
	
	private <T extends Value<?>> T tryCallFunction(String functionName, Value<?>... values) {
		Value<?> function = typeInstance.jValue().get(functionName);
		
		if (function == null) {
			throw new RuntimeException(String.format("Operation <%s> cannot be executed for %s", functionName, typeInstance.getName()));
		}
		
		Tuple tuple = Tuple.valueOf(values);

		if (function instanceof InstanceFunctionValue) {
			tuple.getValues().add(0, this);
		}
		
		
		return (T) function.call(tuple);
	}

	@Override
	public Value<?> inverse() {
		return tryCallFunction("inverse");
	}

	@Override
	public Value<?> negate() {
		return tryCallFunction("negate");
	}

	@Override
	public Value<?> add(Value<?> arg0) {
		return tryCallFunction("add", arg0);
	}

	@Override
	public Value<?> sub(Value<?> arg0) {
		return tryCallFunction("sub", arg0);
	}

	@Override
	public Value<?> div(Value<?> arg0) {
		return tryCallFunction("div", arg0);
	}

	@Override
	public Value<?> mul(Value<?> arg0) {
		return tryCallFunction("mul", arg0);
	}

	@Override
	public Value<?> mod(Value<?> arg0) {
		return tryCallFunction("mod", arg0);
	}

	@Override
	public BooleanValue equals(Value<?> arg0) {
		return tryCallFunction("equals", arg0);
	}
	
	@Override
	public BooleanValue notEquals(Value<?> arg0) {
		return tryCallFunction("notEquals", arg0);
	}

	@Override
	public BooleanValue moreThan(Value<?> arg0) {
		return tryCallFunction("moreThan", arg0);
	}

	@Override
	public BooleanValue lessThan(Value<?> arg0) {
		return tryCallFunction("lessThan", arg0);
	}

	@Override
	public BooleanValue equalsOrLess(Value<?> arg0) {
		return tryCallFunction("equalsOrLess", arg0);
	}

	@Override
	public BooleanValue equalsOrMore(Value<?> arg0) {
		return tryCallFunction("equalsOrMore", arg0);
	}
	
	@Override
	public String asString() {
		return tryCallFunction("toString").asString();
	}
	
	@Override
	public BooleanValue wears(Value<?> arg0) {
		MaskValue mask = (MaskValue)arg0;
		return new BooleanValue(typeInstance.getMasks().contains(mask));
	}

	@Override
	public BooleanValue instanceOf(Value<?> arg0) {
		if (arg0 instanceof MaskValue)
			return wears(arg0);
		return new BooleanValue(typeInstance.equals((Object)arg0));
	}
}
