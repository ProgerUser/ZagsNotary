package mj.report;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			// The class name
			// The file will have the same name
			// and we will also use it to put it as temporary directory name
			final String className = "javademo";

			// A temporary directory where the java code and class will be located
			Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);
			Files.createDirectories(temp);

			// Creation of the java source file
			// You could also extends the SimpleJavaFileObject object as shown in the doc.
			// See SimpleJavaFileObject at
			// https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html
			Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
			System.out.println("The java source file is loacted at " + javaSourceFile);
			String code = "public class " + className + " {" + "public static void run(String name) {\n"
					+ "       System.out.println(name); \n" + "    }" + "}";
			Files.write(javaSourceFile, code.getBytes());

			// ненужная проверка, как оказалось
			{
				// Verification of the presence of the compilation tool archive
				final String toolsJarFileName = "tools.jar";
				final String javaHome = System.getProperty("java.home");
				Path toolsJarFilePath = Paths.get(javaHome, "lib", toolsJarFileName);
				if (!Files.exists(toolsJarFilePath)) {
					System.out.println("The tools jar file (" + toolsJarFileName + ") could not be found at ("
							+ toolsJarFilePath + ").");
				}
			}
			// The compile part
			// Definition of the files to compile
			File[] files1 = { javaSourceFile.toFile() };
			// Get the compiler
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			// Get the file system manager of the compiler
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			// Create a compilation unit (files)
			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(files1));
			// A feedback object (diagnostic) to get errors
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			// Compilation unit can be created and called only once
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
					compilationUnits);
			// The compile task is called
			task.call();
			// Printing of any compile problems
			for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
				System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource());
			}

			// Close the compile resources
			fileManager.close();

			// String parameter
			@SuppressWarnings("rawtypes")
			Class[] paramString = new Class[1];
			paramString[0] = String.class;

			// Now that the class was created, we will load it and run it
			ClassLoader classLoader = Main.class.getClassLoader();
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { temp.toUri().toURL() }, classLoader);
			Class<?> javaDemoClass = urlClassLoader.loadClass(className);
			Method method = javaDemoClass.getMethod("run", paramString);
			method.invoke(null, "SAID");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
