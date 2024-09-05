package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ExportStatement implements Statement
{
	public Script script;
	public Position position;
	public Expression expression;
	public String exportAlias;

	public ExportStatement(Position position, Script script, Expression expression, String exportAlias) {
		this.position = position;
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
		if (!(expression instanceof Accessible accessible)) throw new RuntimeException();
		var indexLiteral = accessible.index(gr);
		if (indexLiteral == null) throw gr.error(expression.getPosition(), "Unknown literal index");
		gr.getScript().export(exportAlias == null ? indexLiteral.getLiteral() : exportAlias, expression.eval(gr));
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
