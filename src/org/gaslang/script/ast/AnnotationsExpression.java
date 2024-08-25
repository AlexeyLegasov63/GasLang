package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

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
			values.getAnnotations().add(new Annotation(expr, nodes.get(expr).eval(gr)));
		}
		return values;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
