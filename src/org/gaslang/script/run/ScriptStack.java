package org.gaslang.script.run;

import org.gaslang.script.Value;

import java.util.HashMap;

public class ScriptStack
{
	private final HashMap<String, Value<?>> stack;
	ScriptStack parent;
	
	public ScriptStack(ScriptStack parent) {
		this.parent = parent;
		this.stack = new HashMap<>();
	}
	
	public Value<?> getFromLocal(String name) {
		if (parent == null) {
			return stack.get(name);
		}
		return parent.getFromLocal(name);
	}
	
	public Value<?> get(String name) {
		if (stack.containsKey(name)) {
			return stack.get(name);
		} else if (parent == null) {
			return GasRuntime.GLOBAL_VALUES.get(name);
		}
		return parent.get(name);
	}
	
	public void set(String name, Value<?> value, boolean putInSelf) {
		if (putInSelf) {
			stack.put(name, value);
			return;
		}
		
		ScriptStack stackWithVar = contains(name);
		
		if (stackWithVar == null) {
			stack.put(name, value);
			return;
		}
		
		stackWithVar.set(name, value, true);
	}
	
	public void set(String name, Value<?> value) {
		if (stack.containsKey(name) || parent == null) {
			stack.put(name, value);
			return;
		}
		parent.set(name, value);
	}
	
	public ScriptStack contains(String name) {
		return stack.containsKey(name) ? this 
				: parent != null ? parent.contains(name) : null;
	}
	public boolean containsLocal(String name) {
		return stack.containsKey(name);
	}

	@Override
	public String toString() {
		return "{" + stack + "}";
	}
}
