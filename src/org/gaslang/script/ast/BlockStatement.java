package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.Visitor;

public class BlockStatement implements Statement
{
	public final ArrayList<Statement> statements;
	
	public BlockStatement(ArrayList<Statement> statements) {
		this.statements = statements;
	}
	
	public BlockStatement() {
		this(new ArrayList<>());
	}

	public void add(Statement statement) {
		statements.add(statement);
	}
	
	@Override
	public void execute(GasRuntime runtime) {
		runtime.push();
		try {
			for (Statement statement : statements) {
				statement.execute(runtime);
			}
		} catch(Throwable t) {
			runtime.pop();
			throw t;
		}
		runtime.pop();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
