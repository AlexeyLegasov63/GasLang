package org.gaslang.io;

import java.io.File;
import java.io.FileFilter;

public class ScriptFileFilter implements FileFilter
{
	@Override
	public boolean accept(File pathname) {
		return pathname != null 
				&& pathname.getName().endsWith(".gs");
	}
}
