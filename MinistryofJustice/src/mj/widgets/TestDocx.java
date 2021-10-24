package mj.widgets;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestDocx {

	public static void main(String[] args) {
		try {
//			Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler C:\\Untitled.FR12.docx");
//			p.waitFor();

			String line;
			ProcessBuilder builder = new ProcessBuilder("rundll32 url.dll,FileProtocolHandler", "/c",
					"C:\\Untitled.FR12.docx");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream(), "Cp866"));
			while ((line = r.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}
