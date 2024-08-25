package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptType;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class MaskStatement implements Statement
{
	public AnnotationsExpression annotations;
	public String name;

	public MaskStatement(String name, AnnotationsExpression annotations) {
		this.name = name;
		this.annotations = annotations;
	}

	@Override
	public void execute(GasRuntime gr) {
		if (gr.contains(name) != null) {
			throw new RuntimeException();
		}
		gr.set(name, new MaskValue(name, annotations.getAnnotations(gr)));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
