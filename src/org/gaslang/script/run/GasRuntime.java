package org.gaslang.script.run;

import org.gaslang.script.Script;
import org.gaslang.script.Value;
import org.gaslang.script.ast.Position;
import org.gaslang.script.exception.RunError;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GasRuntime
{
	public static final HashMap<String, Value<?>> GLOBAL_VALUES = new HashMap<>();
	private LinkedList<CallInfo> callbacks = new LinkedList<>();
	private final Script script;
	private ScriptStack stack;

	public GasRuntime(GasRuntime parent) {
		this(parent.stack, parent.script);
		this.callbacks = parent.callbacks;
	}
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
		return GLOBAL_VALUES.get(name);
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

	public RunError error(Position position, String message) {
		return new RunError(String.format("Threw an exception at [%s:%s] in %s. Message: %s", position.row(), position.column(), position.script(), message));
	}

	public void append(GasRuntime gasRuntime) {
		callbacks = gasRuntime.callbacks;
	}

	public void enter(Position position, String name) {
		callbacks.push(new CallInfo(position, name));
	}

	public void exit() {
		callbacks.pop();
	}

	public LinkedList<CallInfo> getCallbacks() {
		return new LinkedList<>(callbacks);
	}

	@Override
	public String toString() {
		return "GasRuntime " + stack;
	}

	public record CallInfo(Position position, String name) {
		@Override
		public String toString() {
			return "CallInfo{" +
					"position=" + position +
					", name='" + name + '\'' +
					'}';
		}
	}
}
