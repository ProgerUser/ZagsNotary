package ru.psv.mj.report;

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

import ru.psv.mj.msg.Msg;

public class Main {
	
	public class MjDynamRepExc extends Exception { 
	    /**
		 * 
		 */
		private static final long serialVersionUID = 999;

		public MjDynamRepExc(String errorMessage) {
	        super(errorMessage);
	    }
	}
	
	@SuppressWarnings("resource")
	public void DunamicRep() {
		try {
			// ��� ������
			// � ����� ����� ����� �� ���
			// � �� ����� ����� ������������ ���, ����� ��������� ��� ��� ��� ����������
			// ��������
			final String className = "MjReportDocx";

			// ��������� �������, � ������� ����� ���������� java-��� � �����
			Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);
			Files.createDirectories(temp);

			// �������� ��������� ����� java
			// �� ����� ������ ��������� ������ SimpleJavaFileObject, ��� �������� �
			// ���������.
			// ��. SimpleJavaFileObject �
			// https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html
			Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
			System.out.println("�������� ��� java: " + javaSourceFile);
			String code = "public class " + className + " {" + "public static void run(String name) {\n"
					+ "       System.out.println(name); \n" + "    }" + "}";
			Files.write(javaSourceFile, code.getBytes());

			// �������� ��������, ��� ���������
			{
				// �������� ������� � ������ ����������� ����������
				final String toolsJarFileName = "tools.jar";
				final String javaHome = System.getProperty("java.home");
				Path toolsJarFilePath = Paths.get(javaHome, "lib", toolsJarFileName);
				if (!Files.exists(toolsJarFilePath)) {
					Msg.Message("The tools jar file (" + toolsJarFileName + ") could not be found at ("
							+ toolsJarFilePath + ").");
				}
			}
			// ������������� �����
			// ����������� ������ ��� ����������
			File[] files1 = { javaSourceFile.toFile() };
			// �������� ����������
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			// �������� ��������� �������� ������� �����������
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			// ������� ���� ���������� (�����)
			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(files1));
			// ������ �������� ����� (�����������) ��� ��������� ������
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			// ������� ���������� ����� ���� ������� � ������� ������ ���� ���
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
					compilationUnits);
			// ���������� ������ ����������
			task.call();
			// ������ ����� ������� � �����������
			for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
				throw new MjDynamRepExc(diagnostic.getLineNumber() + " " + diagnostic.getSource());
			}
			// ��������� ������� ����������
			fileManager.close();

			// ��������� ��������
			@SuppressWarnings("rawtypes")
			Class[] paramString = new Class[1];
			paramString[0] = String.class;

			// ������, ����� ����� ��� ������, �� �������� ��� � ��������
			ClassLoader classLoader = Main.class.getClassLoader();
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { temp.toUri().toURL() }, classLoader);
			Class<?> javaDemoClass = urlClassLoader.loadClass(className);
			Method method = javaDemoClass.getMethod("run", paramString);
			method.invoke(null, "SAID");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
