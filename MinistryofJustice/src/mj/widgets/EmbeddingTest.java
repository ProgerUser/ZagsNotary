package mj.widgets;

import java.awt.Canvas;
import java.io.File;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class EmbeddingTest extends Canvas {
	private static final long serialVersionUID = 1L;

	public void initOleViewer(String target) {
		Display display = new Display();
		Shell shell = SWT_AWT.new_Shell(display, this);
		shell.setLayout(new FillLayout());

		OleFrame oleFrame = new OleFrame(shell, SWT.NONE);

		OleClientSite oleControlSite = new OleClientSite(oleFrame, SWT.NONE, "Word.Document", new File(target));
		oleControlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		shell.setSize(800, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void main(String[] args) {
		JFrame jFrame = new JFrame("Embedding Test");
		jFrame.setVisible(true);

		EmbeddingTest viewer = new EmbeddingTest();
		jFrame.add(viewer);
		jFrame.setSize(600, 600);

		viewer.initOleViewer("C:/Untitled.FR12.docx");
	}
}