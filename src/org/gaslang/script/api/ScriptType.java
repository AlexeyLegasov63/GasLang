package org.gaslang.script.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.gaslang.script.Annotations;
import org.gaslang.script.Arguments;
import org.gaslang.script.MaskValue;
import org.gaslang.script.NullValue;
import org.gaslang.script.ObjectValue;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.ValueType;
import org.gaslang.script.run.GasRuntime;

public class ScriptType extends Value<HashMap<String, Value<?>>>
{
	private final String objectName;
	private final Arguments objectArguments;
	private final Annotations objectAnnotations;
	private final ArrayList<MaskValue> objectMasks;
	
	public ScriptType(String name, Annotations annotations,  ArrayList<MaskValue> masks, Arguments arguments) {
		super(new HashMap<>());

		this.objectAnnotations = annotations;
		this.objectArguments = arguments;
		this.objectName = name;
		this.objectMasks = masks;
	}

	public String getName() {
		return objectName;
	}
	
	public ArrayList<MaskValue> getMasks() {
		return objectMasks;
	}

	@Override
	public Value<?> call(Tuple args) {
		
		HashMap<String, Value<?>> stack = new HashMap<>(), thisStack = jValue();

		for (int i = 0, l = objectArguments.getArguments().size(); i < l; i++) {
			stack.put(objectArguments.getArguments().get(i).getName(), args.getValue(i, NullValue.NIL_VALUE));
		}
		
		return new ObjectValue(stack, this);
	}
	
	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		arg1.matchValueTypeOrThrow(ValueType.FUNCTION);
		
		if (jValue().replace(arg0.asString(), arg1) != null) 
			throw new RuntimeException("Functions duplicate: " + arg0.asString());
		
		jValue().put(arg0.asString(), arg1);
		
		return arg1;
	}
	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		return jValue().get(arg0.asString());
	}
}
