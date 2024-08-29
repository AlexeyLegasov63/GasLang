package org.gaslang.script.ast;

import org.gaslang.script.Annotation;
import org.gaslang.script.Annotations;
import org.gaslang.script.Expression;
import org.gaslang.script.Visitable;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.util.HashMap;

public class AnnotationsExpression implements Visitable
{
	public HashMap<String, Expression> nodes;

	public AnnotationsExpression() {
		this.nodes = new HashMap<>();
	}

	public void add(String arg0, Expression arg1) {
		nodes.put(arg0, arg1);
	}
	public Annotations getAnnotations(GasRuntime gr) {
		Annotations values = new Annotations();
		for (String expr : nodes.keySet()) {
			values.getAnnotations().put(expr, new Annotation(expr, nodes.get(expr).eval(gr)));
		}
		return values;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
