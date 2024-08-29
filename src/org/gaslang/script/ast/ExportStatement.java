package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ExportStatement implements Statement
{
	public Script script;
	public Expression expression;
	public String exportAlias;

	public ExportStatement(Script script, Expression expression, String exportAlias) {
		this.expression = expression;
		this.exportAlias = exportAlias;
		this.script = script;
	}

	@Override
	public void execute(GasRuntime gr) {
		if (expression instanceof TableExpression table && exportAlias == null) {
			for (Expression key : table.values.keySet()) {
				var keyValue = key.eval(gr);
				keyValue.matchValueTypeOrThrow(ValueType.STRING);
				gr.getScript().export(keyValue.asString(), table.values.get(key).eval(gr));
			}
			return;
		}
		if (expression instanceof FunctionExpression) {
			if (exportAlias == null) throw new RuntimeException();
			gr.getScript().export(exportAlias, expression.eval(gr));
			return;
		}
		if (!(expression instanceof Accessible)) throw new RuntimeException();
		Accessible accessible = (Accessible)expression;
		String index = accessible.index(gr);
		if (index == null) throw new RuntimeException();
		gr.getScript().export(exportAlias == null ? index : exportAlias, expression.eval(gr));
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
