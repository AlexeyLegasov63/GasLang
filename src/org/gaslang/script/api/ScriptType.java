package org.gaslang.script.api;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;

import java.util.ArrayList;
import java.util.HashMap;

public class ScriptType extends Value<HashMap<String, Value<?>>> implements Annotated
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
	public Value<?> call(GasRuntime gasRuntime, Tuple args) {
		
		HashMap<String, Value<?>> stack = new HashMap<>(), thisStack = jValue();

		for (int i = 0, l = objectArguments.getArguments().size(); i < l; i++) {
			stack.put(objectArguments.getArguments().get(i).name(), args.getValue(i, NullValue.NIL_VALUE));
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

	@Override
	public Annotations getAnnotations() {
		return objectAnnotations;
	}
}
