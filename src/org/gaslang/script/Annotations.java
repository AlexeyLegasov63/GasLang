package org.gaslang.script;

import java.util.ArrayList;
import java.util.HashMap;

public class Annotations
{
	private final HashMap<String, Annotation> annotations;
	
	public Annotations(HashMap<String, Annotation> annotations) {
		this.annotations = annotations;
	}
	public Annotations() {
		this(new HashMap<>());
	}

	public HashMap<String, Annotation> getAnnotations() {
		return annotations;
	}

	public Annotation get(String annotationName) {
		return annotations.get(annotationName);
	}

	@Override
	public String toString() {
		return "[" + annotations + "]";
	}
}
