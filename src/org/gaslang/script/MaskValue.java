package org.gaslang.script;

import java.util.ArrayList;

public class MaskValue extends Value<String> implements Annotated
{
	private final Annotations annotations;
	
	public MaskValue(String name, Annotations annotations) {
		super(name, ValueType.MASK);
		this.annotations = annotations;
	}

	public Annotations getAnnotations() {
		return annotations;
	}
}
