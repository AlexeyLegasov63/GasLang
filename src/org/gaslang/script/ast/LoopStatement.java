package org.gaslang.script.ast;

import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class LoopStatement implements Statement
{
	public Statement block;
	
	public LoopStatement(Statement block) {
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
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
