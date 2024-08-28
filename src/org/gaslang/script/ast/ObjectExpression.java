package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptType;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.util.ArrayList;

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
		ArrayList<MaskValue> masks;
		if (this.masks != null) {
			Tuple masksTuple = (Tuple) this.masks.eval(gr);
			masksTuple.assertTypes(ValueType.MASK);
			masks = masksTuple.getValuesAs();
		}
		else {
			masks = new ArrayList<>();
		}
		return new ScriptType(name, annotations.getAnnotations(gr), masks, arguments);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
