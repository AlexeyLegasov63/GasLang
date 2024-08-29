package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Script implements Visitable
{
	public final ArrayList<Statement> statements;

	private final HashMap<String, Value<?>> exported;
	private final GasRuntime runtime;
	private final File file;
	
	public Script(File file) {
		this.exported = new HashMap<>();
		this.statements = new ArrayList<>();
		this.runtime = new GasRuntime(this);
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

	public void export(String name, Value<?> value) {
		exported.put(name, value);
	}

	public HashMap<String, Value<?>> getExported() {
		return exported;
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

	public GasRuntime getRuntime() {
		return runtime;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
