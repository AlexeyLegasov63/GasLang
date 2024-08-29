package org.gaslang.script.lib.boot;

import org.gaslang.script.Annotated;
import org.gaslang.script.ArrayValue;
import org.gaslang.script.ScriptValue;
import org.gaslang.script.Value;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

import static org.gaslang.script.api.ScriptAPI.*;

@GasModule(name = "reflect")
public class ReflectModule
{
	@GasFunction
	public Value<?> getAnnotationValue(Annotated annotated, String annotationName) {
		var annotation = annotated.getAnnotations().get(annotationName);

		if (annotation == null) {
			return null;
		}

		return annotation.value();
	}
	@GasFunction
	public ArrayValue getAnnotations(Annotated annotated) {
		var annotations = annotated.getAnnotations().getAnnotations().keySet();
		var names = annotations.toArray(new String[0]);
		return array(names);
	}
	@GasFunction
	public Value<?> getValue(ScriptValue script, String value) {
		return script.jValue().get(value);
	}
	@GasFunction
	public Value<?> getAnnotated(ScriptValue script, String value) {
		var values = script.jValue().getRuntime().listAll().values();
		return instance(List.class,
				values.stream().filter(v -> v instanceof Annotated).toList().toArray(new Value[0]));
	}
}
