package org.gaslang.script.lib;

import org.gaslang.script.FunctionBlock;
import org.gaslang.script.Value;
import org.gaslang.script.lib.ScriptNativeType.InstanceCreator;
import org.gaslang.script.lib.accessors.NonStrictAccessor;
import org.gaslang.script.lib.accessors.StrictAccessor;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.annotation.GasType;
import org.gaslang.script.lib.annotation.GasVariable;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.security.CodeSource;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.gaslang.script.api.ScriptAPI.value;

public class ScriptBootstrap
{
	public GasPackage loadPackage(String packageName)  {
		var natives = new ArrayList<NativeObject>();

		try {
			var classes = getClassesForPackage(packageName);

			for (var someClass : classes) {
				try {
					var gasObject = registerClass(someClass);
					if (gasObject == null) continue;
					natives.add(gasObject);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return new GasPackage(natives);
	}

	public static List<Class<?>> getClassesForPackage(final String pkgName) throws IOException, URISyntaxException {
		final String pkgPath = pkgName.replace('.', '/');
		final URI pkg = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(pkgPath)).toURI();
		final ArrayList<Class<?>> allClasses = new ArrayList<Class<?>>();

		Path root;
		if (pkg.toString().startsWith("jar:")) {
			try {
				root = FileSystems.getFileSystem(pkg).getPath(pkgPath);
			} catch (final FileSystemNotFoundException e) {
				root = FileSystems.newFileSystem(pkg, Collections.emptyMap()).getPath(pkgPath);
			}
		} else {
			root = Paths.get(pkg);
		}

		final String extension = ".class";
		try (final Stream<Path> allPaths = Files.walk(root)) {
			allPaths.filter(Files::isRegularFile).forEach(file -> {
				try {
					var fileName = file.getFileName().toString();
					allClasses.add(Class.forName(pkgName + "." + fileName.replaceAll(extension, "")));
				} catch (final ClassNotFoundException | StringIndexOutOfBoundsException ignored) {
					ignored.printStackTrace();
				}
			});
		}
		return allClasses;
	}

	public NativeObject registerClass(Class<?> type) throws Exception {

		GasType gasTypeInfo = type.getDeclaredAnnotation(GasType.class);
		
		if (gasTypeInfo != null) {
			return registerType(type);
		}
		
		GasModule moduleInfo = type.getDeclaredAnnotation(GasModule.class);
		
		if (moduleInfo != null) {
			return registerModule(type.getDeclaredConstructor().newInstance());
		}
		
		// If it was just a java class
		return null;
	}
	
	public ScriptNativeType registerType(Class<?> type) {
		
		GasType gasTypeInfo = type.getDeclaredAnnotation(GasType.class);
		
		ScriptNativeType gasType = new ScriptNativeType(gasTypeInfo.name().isEmpty() ? type.getSimpleName() : gasTypeInfo.name(), type, gasTypeInfo.shared());
		
		InstanceCreator instanceCreator = () -> {
			Object instance;
			
			try {
				instance = type.getDeclaredConstructor().newInstance();
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
		
		ScriptNativeModule module = new ScriptNativeModule(moduleInfo.name().isEmpty() ? type.getName() : moduleInfo.name(), moduleInfo.shared());
		
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

		String fieldName = fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name();
		boolean isEditable = fieldInfo.editable();
		
		Value<?> fieldValue = value(field.get(instance));
		field.setAccessible(true);
		
		NativeFieldValue gasField = new NativeFieldValue(fieldValue, field, instance, isEditable);
		
		container.set(fieldName, gasField);
	}
	
	public void registerFunction(NativeObject container, Method method, Object instance) {
		if (!method.isAnnotationPresent(GasFunction.class))
			return;
		
		GasFunction functionInfo = method.getDeclaredAnnotation(GasFunction.class);
		
		String functionName = functionInfo.name().isEmpty() ? method.getName() : functionInfo.name();
		boolean isStrict = functionInfo.strict();
		
		method.setAccessible(true);

		FunctionBlock accessor = isStrict ? 
				new StrictAccessor(method, instance) 
				: new NonStrictAccessor(method, instance);

		NativeFunctionValue gasFunction = new NativeFunctionValue(accessor);
		
		container.set(functionName, gasFunction);
	}
}
