package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;

public interface Accessible
{
	Value<?> get(GasRuntime gr);
	
	Value<?> set(GasRuntime gr, Value<?> v);
	
	Value<?> insert(GasRuntime gr, Value<?> v);
	
	String index(GasRuntime gr);
	
	default boolean isEmpty(GasRuntime gr) {
		return false;
	}
	
	default boolean isLiteral(GasRuntime gr) {
		return index(gr) != null;
	}
}
