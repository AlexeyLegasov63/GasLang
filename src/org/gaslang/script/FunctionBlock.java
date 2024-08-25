package org.gaslang.script;

import org.gaslang.script.run.*;

public interface FunctionBlock
{
	Value<?> execute(Tuple args);
}
