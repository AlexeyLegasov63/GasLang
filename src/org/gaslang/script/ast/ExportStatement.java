package org.gaslang.script.ast;

import org.gaslang.script.Accessible;
import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ExportStatement implements Statement
{
	public Expression expression;
	public String exportAlias;

	public ExportStatement(Expression expression, String exportAlias) {
		this.expression = expression;
		this.exportAlias = exportAlias;
	}

	@Override
	public void execute(GasRuntime gr) {
		if (expression instanceof FunctionExpression) {
			if (exportAlias == null) throw new RuntimeException();
			
			GasRuntime.GLOBAL_VALUES.put(exportAlias, expression.eval(gr));
			
			return;
		}
		
		if (!(expression instanceof Accessible)) throw new RuntimeException();
		
		Accessible accessible = (Accessible)expression;
		
		String index = accessible.index(gr);
		
		if (index == null) throw new RuntimeException();
		
		GasRuntime.GLOBAL_VALUES.put(exportAlias == null ? index : exportAlias, expression.eval(gr));
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
