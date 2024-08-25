package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class FunctionExpression implements Expression
{
	public boolean isInstanceFunction;
	public Statement statement;
	public Arguments arguments;
	public AnnotationsExpression annotations;
	
	public FunctionExpression(Statement statement, Arguments arguments, AnnotationsExpression annotations, boolean isInstanceFunction) {
		this.statement = statement;
		this.arguments = arguments;
		this.annotations = annotations;
		this.isInstanceFunction = isInstanceFunction;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		return isInstanceFunction ? new InstanceFunctionValue(gr, "Anonymous", annotations.getAnnotations(gr), arguments, statement)
				: new FunctionValue(gr, "Anonymous", annotations.getAnnotations(gr), arguments, statement);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
