package org.gaslang.script;

public record Annotation(String name, Value<?> value) {

	@Override
	public String toString() {
		return "@" + name + " = " + value;
	}

}
