package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

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
