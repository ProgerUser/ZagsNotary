package mj.init;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import mj.app.main.Main;
import mj.msg.Msg;

public class DotNet {

	/**
	 * Конструктор
	 */
	public DotNet() {
		Main.logger = Logger.getLogger(getClass());
//		try {
//			if (System.getProperty("sun.arch.data.model").equals("64")) {
//				Bridge.init(new File(System.getenv("MJ_PATH") + "jni4net.n.w64.v40-0.8.8.0.dll"));
//			} else {
//				Bridge.init(new File(System.getenv("MJ_PATH") + "jni4net.n.w32.v40-0.8.8.0.dll"));
//			}
//			File dll = new File(System.getenv("MJ_PATH") + "MJ_WORD.j4n.dll");
//			Bridge.LoadAndRegisterAssemblyFrom(dll);
//		} catch (Exception e) {
//			Msg.Message(ExceptionUtils.getStackTrace(e));
//			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
//			e.printStackTrace();
//		}
	}

	/**
	 * Вызов c#
	 * 
	 * @return
	 */
	public String OpenAndDelDocx(String filename) {
		String ret = null;
//		try {
//			WORD csdll = new WORD();
//			ret = csdll.Show(filename);
//		} catch (Exception e) {
//			Msg.Message(ExceptionUtils.getStackTrace(e));
//			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
//			e.printStackTrace();
//		}
		return ret;
	}

	/**
	 * Получить индекс для печатной формы
	 * 
	 * @param doc
	 * @return
	 */
	public Integer FileName(String doc) {
		Integer index = 1;
		try {
			List<Integer> doc_num = new ArrayList<Integer>();
			File dir = new File(System.getenv("MJ_PATH") + "OutReports");
			File[] directoryListing = dir.listFiles();
			Arrays.sort(directoryListing, Comparator.comparingLong(File::lastModified).reversed());
			if (directoryListing != null) {
				for (File child : directoryListing) {
					if (child.isFile()) {
						String filename = child.getName().replace(".docx", "").substring(0,
								child.getName().indexOf("~"));
						System.out.println("filename=" + filename);
						if (filename.equals(doc)) {
							Integer num = Integer.valueOf(child.getName().replace(".docx", "").substring(
									child.getName().indexOf("~") + 1, child.getName().replace(".docx", "").length()));
							doc_num.add(num);
							System.out.println("num=" + num);
						}
					}
				}
			}
			if (!doc_num.isEmpty()) {
				Integer max = Collections.max(doc_num);
				index = max + 1;
			}
		} catch (Exception e) {
			Msg.Message(ExceptionUtils.getStackTrace(e));
			Main.logger.error(ExceptionUtils.getStackTrace(e) + "~" + Thread.currentThread().getName());
			e.printStackTrace();
		}
		return index;
	}

}
