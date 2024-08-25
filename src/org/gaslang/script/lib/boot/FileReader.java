package org.gaslang.script.lib.boot;

import java.io.FileInputStream;
import java.io.IOException;

import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

@GasType
public class FileReader
{
	private java.io.File file;
	
	@GasFunction
	public void constructor(ScriptNativeObject file) {
		file.matchValueType(File.class);
		this.file = ((File)file.getJavaInstance()).file;
	}

	@GasFunction
	public String read() {
		try (final FileInputStream channel = new FileInputStream(file)) {
			int av = channel.available();
			byte[] input = new byte[av];
			while (channel.read(input) != -1); // stop off
			channel.close();
			return new String(input, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
