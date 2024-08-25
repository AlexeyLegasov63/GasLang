package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

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
