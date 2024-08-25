package org.gaslang.script.ast;

import java.util.ArrayList;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptType;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class ObjectExpression implements Expression
{
	public String name;
	public Arguments arguments;
	public AnnotationsExpression annotations;
	public TupleExpression masks;

	public ObjectExpression(String name, Arguments arguments, TupleExpression masks, AnnotationsExpression annotations) {
		this.name = name;
		this.arguments = arguments;
		this.annotations = annotations;
		this.masks = masks;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Tuple masks = (Tuple) this.masks.eval(gr);
		masks.assertTypes(ValueType.MASK);
		return new ScriptType(name, annotations.getAnnotations(gr), masks.getValuesAs(), arguments);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
