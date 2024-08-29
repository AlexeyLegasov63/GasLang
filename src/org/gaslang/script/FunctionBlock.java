package org.gaslang.script;

public interface FunctionBlock
{
	Value<?> execute(Tuple args);

	default String desiredArgs() {
		return "";
	}
}
