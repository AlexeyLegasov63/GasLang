package org.gaslang.script.lib;

import java.lang.reflect.*;

import org.gaslang.script.*;

public class NativeFieldValue extends Value<Value<?>>
{
	private final Object source;
	private final Field field;
	private final boolean isEditable;
	
	public NativeFieldValue(Value<?> value, Field field, Object source, boolean isEditable) {
		super(value);
		this.source = source;
		this.field = field;
		this.isEditable = isEditable;
	}
	
	public void set(Value<?> arg0) {
		if (!isEditable) throw new RuntimeException();
		try {
			field.set(source, arg0);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Value<?> get() {
		try {
			return (Value<?>) field.get(source);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
