package org.gaslang.script.ast;

import org.gaslang.script.MaskValue;
import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

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
