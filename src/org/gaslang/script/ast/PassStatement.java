package org.gaslang.script.ast;

import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class PassStatement implements Statement
{	
	@Override
	public void execute(GasRuntime runtime) {
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
