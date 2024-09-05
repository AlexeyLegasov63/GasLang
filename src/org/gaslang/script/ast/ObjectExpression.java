package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptType;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.util.ArrayList;

public class ObjectExpression extends OperandExpression
{
	public String name;
	public Arguments arguments;
	public AnnotationsExpression annotations;
	public TupleExpression masksTupleExpression;

	public ObjectExpression(Literal nameToken, Arguments arguments, TupleExpression masksTupleExpression, AnnotationsExpression annotations) {
		super(Position.of(nameToken));
		this.name = nameToken.getLiteral();
		this.arguments = arguments;
		this.annotations = annotations;
		this.masksTupleExpression = masksTupleExpression;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		ArrayList<MaskValue> masks;
		if (this.masksTupleExpression != null) {
			Tuple masksTuple = (Tuple) this.masksTupleExpression.eval(gasRuntime);
			masksTuple.assertTypes(ValueType.MASK);
			masks = masksTuple.getValuesAs();
		}
		else {
			masks = new ArrayList<>();
		}
		return new ScriptType(name, annotations.getAnnotations(gasRuntime), masks, arguments);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
