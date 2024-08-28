package org.gaslang.script.lib;

import org.gaslang.script.Arguments;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptType;

import java.util.ArrayList;

public class ScriptNativeType extends ScriptType implements NativeObject
{
	private InstanceCreator instanceCreator;
	private Class<?> jClass;
	private boolean isShared;
	
	public ScriptNativeType(String name, Class<?> jClass, boolean isShared) {
		super(name, null, new ArrayList<>(), new Arguments());
		this.jClass = jClass;
		this.isShared = isShared;
	}
	
	void setInstanceCreator(InstanceCreator instanceCreator) {
		this.instanceCreator = instanceCreator;
	}
	
	public Class<?> getJavaClass() {
		return jClass;
	}
	
	public boolean isShared() {
		return isShared;
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		throw new RuntimeException();
	}

	@Override
	public Value<?> call(Tuple args) {
		ScriptNativeObject scriptNativeObject = instanceCreator.create();
		
		scriptNativeObject.callContructor(args);
		
		return scriptNativeObject;
	}

	@Override
	public void set(String fieldName, Value<?> value) {
		jValue().put(fieldName, value);
	}
	
	static interface InstanceCreator
	{
		ScriptNativeObject create();
	}
}
