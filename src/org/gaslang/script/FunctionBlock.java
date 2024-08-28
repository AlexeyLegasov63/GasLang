package org.gaslang.script;

public interface FunctionBlock
{
	Value<?> execute(Tuple args);
}
