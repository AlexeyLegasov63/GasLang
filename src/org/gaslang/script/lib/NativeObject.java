package org.gaslang.script.lib;

import org.gaslang.script.Value;

public interface NativeObject
{
	void set(String name, Value<?> value);
}
