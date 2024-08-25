package org.gaslang.script;

import java.util.ArrayList;

import org.gaslang.script.ast.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.Visitor;

public class Script implements Visitable
{
	public final ArrayList<Statement> statements;
	
	private final GasRuntime runtime;
	
	public Script() {
		this.statements = new ArrayList<>();
		this.runtime = new GasRuntime();
		
		runtime.set("script", new ScriptValue(this));
	}

	public void add(Statement statement) {
		statements.add(statement);
	}

	public void execute() {
		for (Statement s : statements) {
			s.execute(runtime);
		}
	}

	public Value<?> get(String name) {
		return runtime.get(name);
	}

	public void set(String name, Value<?> value) {
		runtime.set(name, value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
