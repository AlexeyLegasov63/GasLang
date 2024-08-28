package org.gaslang.script.ast;

import org.gaslang.script.GasScript;
import org.gaslang.script.Script;
import org.gaslang.script.ScriptValue;
import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ImportStatement implements Statement
{
	public String fileName;
	public String importAlias;

	public ImportStatement(String fileName, String importAlias) {
		this.fileName = fileName;
		this.importAlias = importAlias;
	}

	@Override
	public void execute(GasRuntime gr) {
		try {
			Script importedScript = GasScript.loadScript(fileName);
			
			if (importAlias == null) return;
			
			gr.set(importAlias, new ScriptValue(importedScript));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
