package test;
/*
 * Copyright 2015 Sudipto Chandra.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.util.logging.*;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.web.*;
import javafx.stage.*;

/**
 * @author Sudipto Chandra.
 */
public class CustomHTMLEditorTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CustomHTMLEditor htmlEditor = new CustomHTMLEditor();
        htmlEditor.setMaxHeight(Double.MAX_VALUE);
        htmlEditor.setMaxWidth(Double.MAX_VALUE);
        htmlEditor.setMinWidth(0);
        htmlEditor.setMinHeight(0);
        HBox.setHgrow(htmlEditor, Priority.ALWAYS);
        VBox.setVgrow(htmlEditor, Priority.ALWAYS);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", 14f));

        TabPane root = new TabPane();
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        root.getTabs().add(new Tab("   Visual   ", htmlEditor));
        root.getTabs().add(new Tab("   HTML   ", textArea));

        root.getSelectionModel().selectedIndexProperty().addListener((event) -> {
            textArea.setText(
                    htmlEditor.getHtmlText()
                    .replace("<", "\n<")
                    .replace(">", ">\n")
                    .replace("\n\n", "\n")
            );
        });

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("HTML Editor Test!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class CustomHTMLEditor extends HTMLEditor {

    public static final String TOP_TOOLBAR = ".top-toolbar";
    public static final String BOTTOM_TOOLBAR = ".bottom-toolbar";
    public static final String WEB_VIEW = ".web-view";
    private static final String IMPORT_BUTTON_IMAGE = "image.png";

    public CustomHTMLEditor() {
        identifyControls();
        addCustomButtons();
        this.setHtmlText("<html><head /><body /></html>");
    }

    private WebView mWebView;
    private ToolBar mTopToolBar;
    @SuppressWarnings("unused")
	private ToolBar mBottomToolBar;
    private Button mImportImageButton;

    private void identifyControls() {
        Node nod;
        nod = this.lookup(WEB_VIEW);
        if (nod instanceof WebView) {
            mWebView = (WebView) nod;
        }
        nod = this.lookup(TOP_TOOLBAR);
        if (nod instanceof ToolBar) {
            mTopToolBar = (ToolBar) nod;
        }
        nod = this.lookup(BOTTOM_TOOLBAR);
        if (nod instanceof ToolBar) {
            mBottomToolBar = (ToolBar) nod;
        }
    }

    private void addCustomButtons() {
        // add import image button
        ImageView graphic = null;
        try {
            graphic = new ImageView(new Image(
                    getClass().getResourceAsStream(IMPORT_BUTTON_IMAGE)));
        } catch (Exception ex) {
        }
        mImportImageButton = new Button("Import Image", graphic);
        mImportImageButton.setTooltip(new Tooltip("Import Image"));
        mImportImageButton.setOnAction((event) -> {
            onImportButtonAction();
        });

        //add to top toolbar        
        mTopToolBar.getItems().add(mImportImageButton);
        mTopToolBar.getItems().add(new Separator(Orientation.VERTICAL));
    }
    
    private void onImportButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image file");
        FileChooser.ExtensionFilter imageFileFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.bmp", "*.gif", "*.tif", "*.tiff");
        FileChooser.ExtensionFilter allFileFilter = new FileChooser.ExtensionFilter(
                "All Files", "*.*");
        fileChooser.getExtensionFilters().add(imageFileFilter);
        fileChooser.getExtensionFilters().add(allFileFilter);
        fileChooser.setSelectedExtensionFilter(imageFileFilter);
        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            importImageFile(selectedFile);
        }
    }

    private void importImageFile(File file) {
        try {
            //get type and alt-text
            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));

            //get html content
            byte[] data = org.apache.commons.io.FileUtils.readFileToByteArray(file);
            String base64data = java.util.Base64.getEncoder().encodeToString(data);
            String htmlData = String.format(
                    "<img src=\"data:image/%s;base64,%s\" alt=\"%s\">",
                    extension, base64data, fileNameWithoutExtension);

            //insert beside cursor
            String script = String.format("(function(html) {\n"
                    + "    var sel, range;\n"
                    + "    if (window.getSelection) {\n"
                    + "        // IE9 and non-IE\n"
                    + "        sel = window.getSelection();\n"
                    + "        if (sel.getRangeAt && sel.rangeCount) {\n"
                    + "            range = sel.getRangeAt(0);\n"
                    + "            range.deleteContents();\n"
                    + "            // Range.createContextualFragment() would be useful here but is\n"
                    + "            // only relatively recently standardized and is not supported in\n"
                    + "            // some browsers (IE9, for one)\n"
                    + "            var el = document.createElement(\"div\");\n"
                    + "            el.innerHTML = html;\n"
                    + "            var frag = document.createDocumentFragment(), node, lastNode;\n"
                    + "            while ( (node = el.firstChild) ) {\n"
                    + "                lastNode = frag.appendChild(node);\n"
                    + "            }\n"
                    + "            range.insertNode(frag);\n"
                    + "            // Preserve the selection\n"
                    + "            if (lastNode) {\n"
                    + "                range = range.cloneRange();\n"
                    + "                range.setStartAfter(lastNode);\n"
                    + "                range.collapse(true);\n"
                    + "                sel.removeAllRanges();\n"
                    + "                sel.addRange(range);\n"
                    + "            }\n"
                    + "        }\n"
                    + "    } else if (document.selection && document.selection.type != \"Control\") {\n"
                    + "        // IE < 9\n"
                    + "        document.selection.createRange().pasteHTML(html);\n"
                    + "    }\n"
                    + "})(\"%s\");",
                    htmlData.replace("\"", "\\\""));

            mWebView.getEngine().executeScript(script);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}