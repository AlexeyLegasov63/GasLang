package org.gaslang.script.ast;

import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class BreakStatement extends RuntimeException implements Statement
{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute(GasRuntime runtime) {
		throw this;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
