package org.gaslang.script.ast;

import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class CycleStatement implements Statement
{
	public Statement block;
	
	public CycleStatement(Statement block) {
		this.block = block;
	}

	@Override
	public void execute(GasRuntime gr) {
		while (true) {
			try {
				block.execute(gr);
			} catch(ContinueStatement s) {
				continue;
			} catch(BreakStatement s) {
				break;
			}
			break;
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
