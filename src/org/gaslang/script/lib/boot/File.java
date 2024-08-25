package org.gaslang.script.lib.boot;

import java.io.IOException;

import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

@GasType
public class File
{
	java.io.File file;
	
	@GasFunction
	public void constructor(String path) {
		file = new java.io.File(path);
	}

	@GasFunction
	public String getPath() {
		return file.getPath();
	}

	@GasFunction
	public Boolean exists() {
		return file.exists();
	}

	@GasFunction
	public Boolean isDirectory() {
		return file.isDirectory();
	}

	@GasFunction
	public Boolean isFile() {
		return file.isFile();
	}

	@GasFunction
	public Boolean createNewFile() {
		try {
			return file.createNewFile();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@GasFunction
	public Boolean mkdir() {
		return file.mkdir();
	}

	@GasFunction
	public Boolean mkdirs() {
		return file.mkdirs();
	}
}
