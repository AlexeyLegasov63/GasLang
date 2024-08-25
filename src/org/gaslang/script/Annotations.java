package org.gaslang.script;

import java.util.ArrayList;

public class Annotations
{
	private final ArrayList<Annotation> annotations;
	
	public Annotations(ArrayList<Annotation> annotations) {
		this.annotations = annotations;
	}
	public Annotations() {
		this(new ArrayList<>());
	}

	public ArrayList<Annotation> getAnnotations() {
		return annotations;
	}
	@Override
	public String toString() {
		return "[" + annotations + "]";
	}
}
