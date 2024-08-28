package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Visitable;
import org.gaslang.script.visitor.Visitor;

import java.util.HashMap;

public class Nodes implements Visitable
{
	public HashMap<Expression, Expression> nodes;

	public Nodes() {
		this.nodes = new HashMap<>();
	}

	public void add(Expression arg0, Expression arg1) {
		nodes.put(arg0, arg1);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
