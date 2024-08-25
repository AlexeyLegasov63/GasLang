package org.gaslang.script.lib;

import java.lang.reflect.*;
import java.net.URL;
import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptModule;
import org.gaslang.script.lib.ScriptNativeType.InstanceCreator;
import org.gaslang.script.lib.accessors.NonStrictAccessor;
import org.gaslang.script.lib.accessors.StrictAccessor;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.annotation.GasType;
import org.gaslang.script.lib.annotation.GasVariable;
import org.gaslang.script.lib.boot.MathModule;
import org.gaslang.script.run.*;

import static org.gaslang.script.api.ScriptAPI.*;

import java.io.*;
import java.lang.annotation.Annotation;

public class ScriptBootstrap
{
	public GasPackage loadPackage(String packageName) {
		URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
		
		File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".class");
		    }
		});
		
		ArrayList<NativeObject> natives = new ArrayList<>();
		
		for (File file : files) {
		    String className = file.getName().replaceAll(".class$", "");
		    try {
				Class<?> cls = Class.forName(packageName + "." + className);
				//if (!cls.isAnnotationPresent(GasModule.class)) continue;
				natives.add(registerClass(cls));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new GasPackage(natives);
	}
	public NativeObject registerClass(Class<?> type) throws Exception {

		GasType gasTypeInfo = type.getDeclaredAnnotation(GasType.class);
		
		if (gasTypeInfo != null) {
			return registerType(type);
		}
		
		GasModule moduleInfo = type.getDeclaredAnnotation(GasModule.class);
		
		if (moduleInfo != null) {
			return registerModule(type.newInstance());
		}
		
		// If it was just a java class
		return null;
	}
	
	public ScriptNativeType registerType(Class<?> type) throws Exception {
		
		GasType gasTypeInfo = type.getDeclaredAnnotation(GasType.class);
		
		ScriptNativeType gasType = new ScriptNativeType(gasTypeInfo.name().equals("") ? type.getSimpleName() : gasTypeInfo.name(), type, gasTypeInfo.shared());
		
		InstanceCreator instanceCreator = () -> {
			Object instance;
			
			try {
				instance = type.newInstance();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			
			ScriptNativeObject nativeObject = new ScriptNativeObject(gasType, instance);
			
			
			for (Method method : type.getDeclaredMethods()) {
				try {
					registerFunction(nativeObject, method, instance);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			for (Field field : type.getDeclaredFields()) {
				try {
					registerField(nativeObject, field, instance);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			return nativeObject;
		};

		gasType.setInstanceCreator(instanceCreator);
		
		return gasType;
	}
	
	public ScriptNativeModule registerModule(Object instance) throws Exception {
		Class<?> type = instance.getClass();
		
		GasModule moduleInfo = type.getDeclaredAnnotation(GasModule.class);
		
		ScriptNativeModule module = new ScriptNativeModule(moduleInfo.name().equals("") ? type.getName() : moduleInfo.name(), moduleInfo.shared());
		
		for (Method method : type.getDeclaredMethods()) {
			registerFunction(module, method, instance);
		}
		
		for (Field field : type.getDeclaredFields()) {
			registerField(module, field, instance);
		}
		
		
		return module;
	}
	
	public void registerField(NativeObject container, Field field, Object instance) throws IllegalArgumentException, IllegalAccessException {
		if (!field.isAnnotationPresent(GasVariable.class))
			return;
		
		GasVariable fieldInfo = field.getDeclaredAnnotation(GasVariable.class);

		String fieldName = fieldInfo.name().equals("") ? field.getName() : fieldInfo.name();
		Boolean isEditable = fieldInfo.editable();
		
		Value<?> fieldValue = value(field.get(instance));
		field.setAccessible(true);
		
		NativeFieldValue gasField = new NativeFieldValue(fieldValue, field, instance, isEditable);
		
		container.set(fieldName, gasField);
	}
	
	public void registerFunction(NativeObject container, Method method, Object instance) {
		if (!method.isAnnotationPresent(GasFunction.class))
			return;
		
		GasFunction functionInfo = method.getDeclaredAnnotation(GasFunction.class);
		
		String functionName = functionInfo.name().equals("") ? method.getName() : functionInfo.name();
		boolean isStrict = functionInfo.strict();
		
		method.setAccessible(true);

		FunctionBlock accessor = isStrict ? 
				new StrictAccessor(method, instance) 
				: new NonStrictAccessor(method, instance);

		NativeFunctionValue gasFunction = new NativeFunctionValue(accessor);
		
		container.set(functionName, gasFunction);
	}
}
