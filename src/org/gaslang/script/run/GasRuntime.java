package org.gaslang.script.run;

import org.gaslang.script.Script;
import org.gaslang.script.Value;

import java.util.HashMap;

public class GasRuntime
{
	public static final HashMap<String, Value<?>> GLOBAL_VALUES = new HashMap<>();

	private final Script script;
	private ScriptStack stack;

	public GasRuntime(Script script) {
		this(new ScriptStack(null), script);
	}
	public GasRuntime() {
		this(new ScriptStack(null), null);
	}
	public GasRuntime(ScriptStack stack) {
		this(stack, null);
	}
	public GasRuntime(ScriptStack stack,  Script script) {
		this.stack = stack;
		this.script = script;
	}
	
	public boolean isPrimary() {
		return stack.parent == null;
	}
	
	public void push() {
		stack = new ScriptStack(stack);
	}
	
	public void pop() {
		if (stack.parent == null) return; //throw new RuntimeException();
		stack = stack.parent;
	}

	public Script getScript() {
		return script;
	}

	public HashMap<String, Value<?>> listAll() {
		return stack.stack;
	}

	public Value<?> get(String name) {
		return stack.get(name);
	}
	
	public Value<?> getLocal(String name) {
		return stack.getFromLocal(name);
	}
	
	public Value<?> getGlobal(String name) {
		return GasRuntime.GLOBAL_VALUES.get(name);
	}

	public void set(String name, Value<?> value) {
		stack.set(name, value);
	}

	public void setLocal(String name, Value<?> value) {
		stack.set(name, value, true);
	}

	public ScriptStack contains(String name) {
		return stack.contains(name);
	}
	public boolean containsLocal(String name) {
		return stack.containsLocal(name);
	}
	@Override
	public String toString() {
		return "GasRuntime " + stack;
	}
}
