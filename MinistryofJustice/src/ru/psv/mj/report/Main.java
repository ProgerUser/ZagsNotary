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
			// Имя класса
			// У файла будет такое же имя
			// и мы также будем использовать его, чтобы поместить его как имя временного
			// каталога
			final String className = "MjReportDocx";

			// Временный каталог, в котором будут находиться java-код и класс
			Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);
			Files.createDirectories(temp);

			// Создание исходного файла java
			// Вы также можете расширить объект SimpleJavaFileObject, как показано в
			// документе.
			// См. SimpleJavaFileObject в
			// https://docs.oracle.com/javase/8/docs/api/javax/tools/JavaCompiler.html
			Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
			System.out.println("Исходный код java: " + javaSourceFile);
			String code = "public class " + className + " {" + "public static void run(String name) {\n"
					+ "       System.out.println(name); \n" + "    }" + "}";
			Files.write(javaSourceFile, code.getBytes());

			// ненужная проверка, как оказалось
			{
				// Проверка наличия в архиве инструмента компиляции
				final String toolsJarFileName = "tools.jar";
				final String javaHome = System.getProperty("java.home");
				Path toolsJarFilePath = Paths.get(javaHome, "lib", toolsJarFileName);
				if (!Files.exists(toolsJarFilePath)) {
					Msg.Message("The tools jar file (" + toolsJarFileName + ") could not be found at ("
							+ toolsJarFilePath + ").");
				}
			}
			// Компилируемая часть
			// Определение файлов для компиляции
			File[] files1 = { javaSourceFile.toFile() };
			// Получаем компилятор
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			// Получаем диспетчер файловой системы компилятора
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			// Создаем блок компиляции (файлы)
			Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(files1));
			// Объект обратной связи (диагностика) для получения ошибок
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			// Единица компиляции может быть создана и вызвана только один раз
			JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null,
					compilationUnits);
			// Вызывается задача компиляции
			task.call();
			// Печать любых проблем с компиляцией
			for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
				throw new MjDynamRepExc(diagnostic.getLineNumber() + " " + diagnostic.getSource());
			}
			// Закрываем ресурсы компиляции
			fileManager.close();

			// Строковый параметр
			@SuppressWarnings("rawtypes")
			Class[] paramString = new Class[1];
			paramString[0] = String.class;

			// Теперь, когда класс был создан, мы загрузим его и запустим
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
