package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.io.File;
import java.util.ArrayList;

public class Script implements Visitable
{
	public final ArrayList<Statement> statements;
	
	private final GasRuntime runtime;
	private final File file;
	
	public Script(File file) {
		this.statements = new ArrayList<>();
		this.runtime = new GasRuntime();
		this.file = file;
		runtime.set("script", new ScriptValue(this));
	}

	public File getFile() {
		return file;
	}

	public Script require(String directory) {
		var file = new File(this.file.getParent() + directory);
		return this;
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
