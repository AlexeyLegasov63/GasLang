package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class FunctionExpression extends OperandExpression
{
	public boolean isInstanceFunction;
	public Statement functionBlockStatement;
	public Arguments functionArguments;
	public AnnotationsExpression annotationsExpression;
	
	public FunctionExpression(Position defictionPosition, Statement functionBlockStatement, Arguments functionArguments, AnnotationsExpression annotationsExpression, boolean isInstanceFunction) {
		super(defictionPosition);
		this.functionBlockStatement = functionBlockStatement;
		this.functionArguments = functionArguments;
		this.annotationsExpression = annotationsExpression;
		this.isInstanceFunction = isInstanceFunction;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		var annotations = annotationsExpression.getAnnotations(gasRuntime);
		return isInstanceFunction ? new InstanceFunctionValue(gasRuntime, "Anonymous", annotations, functionArguments, functionBlockStatement)
				: new FunctionValue(gasRuntime, "Anonymous", annotations, functionArguments, functionBlockStatement);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
