package notary.doc.html.controller;

import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mj.app.main.Main;
import mj.dbutil.DBUtil;
import mj.msg.Msg;
import netscape.javascript.JSObject;

public class HtmlEditorTest {
	public HtmlEditorTest() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private VBox root;
	@FXML
	private WebView webView;

	public class JsToJava {
		public void run(String id) {
			Msg.Message(id);
			webEngine.executeScript("SetValue('" + id + "','sdfsdf')");
		}
	}

	private WebEngine webEngine;

	public void fromDocxToHtmlAndBack() throws Exception {
//        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File("C:\\MJ_\\NT_REP\\ДОВЕРЕННОСТЬ 2020 пенсия.docx"));
//
//        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
//        htmlSettings.setWmlPackage(wordMLPackage);
//
//        String htmlFilePath = "D:/ddd.html";
//        OutputStream os = new java.io.FileOutputStream(htmlFilePath);
//
//        // write html
//        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);

//        // XHTML to docx
//        File xmlFile = new File(htmlFilePath);
//
//        WordprocessingMLPackage docxOut = WordprocessingMLPackage.createPackage();
//
//        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
//        docxOut.getMainDocumentPart().addTargetPart(ndp);
//        ndp.unmarshalDefaultNumbering();
//
//        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(docxOut);
//        XHTMLImporter.setHyperlinkStyle("Hyperlink");
//
//        docxOut.getMainDocumentPart().getContent().addAll(
//                XHTMLImporter.convert(xmlFile, null));
//
//        Docx4J.save(docxOut, new File("D:/"));
	}

	void print() {
		Printer pdfPrinter = null;
		Iterator<Printer> iter = Printer.getAllPrinters().iterator();
		while (iter.hasNext()) {
			Printer printer = iter.next();
			if (printer.getName().endsWith("PDF")) {
				pdfPrinter = printer;
			}
		}
		PrinterJob job = null;
		try {
			// clear margins
			PageLayout layout = pdfPrinter.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
					MarginType.HARDWARE_MINIMUM);
			job = PrinterJob.createPrinterJob(pdfPrinter);
			job.getJobSettings().setPageLayout(layout);
			job.getJobSettings().setJobName("Sample Printing Job");
			webEngine.print(job);
			job.endJob();
		} finally {
			if (job != null) {
				job.endJob();
			}
		}
	}

	@FXML
	void editor(ActionEvent event) {
		try {
//			InputStream is = getClass().getResourceAsStream("/notary/doc/old/controller/Test.html");
//			String inputfilepath = IOUtils.toString(is, StandardCharsets.UTF_8);
//			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
//	        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
//			wordMLPackage.getMainDocumentPart().getContent().addAll( 
//					XHTMLImporter.convert( inputfilepath, null) );
//			wordMLPackage.save(new java.io.File("D:/OUT_from_XHTML.docx"));
//			fromDocxToHtmlAndBack();
			//print();
			Stage stage = new Stage();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/notary/template/html/view/HtmlEditor.fxml"));

			HtmTempEditor controller = new HtmTempEditor();
			loader.setController(controller);

			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.getIcons().add(new Image("/icon.png"));
			stage.setTitle("HTML EDITOR");
			stage.initOwner((Stage) webView.getScene().getWindow());
			stage.setResizable(true);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent paramT) {

				}
			});
			stage.show();
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	void reload(ActionEvent event) {
		try {
			/* Load the web page URL (location of the resource) */
			URL url = HtmlEditorTest.class.getResource("/notary/doc/old/controller/Test.html");
			webEngine = webView.getEngine();
			webEngine.load(url.toExternalForm());
			/*
			 * Set the State listener as well as the name of the JavaScript object and its
			 * corresponding Java object (the class in which methods will be invoked) that
			 * will serve as the bridge for the two objects.
			 */
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> observableValue, State oldState, State newState) {
					if (newState == State.SUCCEEDED) {
						JSObject window = (JSObject) webEngine.executeScript("window");
						/* The two objects are named using the setMember() method. */
						window.setMember("invoke", new JsToJava());
					}
				}
			});
		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	@FXML
	public void initialize() {

	}
}
