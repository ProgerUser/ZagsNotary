package mj.widgets;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mj.app.main.Main;
import mj.dbutil.DBUtil;

public class KeyBoard {

	public KeyBoard() {
		Main.logger = Logger.getLogger(getClass());
	}

	@FXML
	private Button B1;

	@FXML
	private Button B2;

	@FXML
	private Button Shift;

	@FXML
	private Button B3;

	@FXML
	private Button B4;

	@FXML
	private Button B5;

	@FXML
	private Button B6;

	@FXML
	private Button B7;

	@FXML
	private Button B8;

	@FXML
	private Button B9;

	@FXML
	private Button B10;

	@FXML
	private Button B11;

	@FXML
	private Button B12;

	@FXML
	private Button B13;

	@FXML
	private Button B14;

	@FXML
	private Button B15;

	@FXML
	private Button B16;

	@FXML
	private Button B17;

	@FXML
	private Button B18;

	@FXML
	private Button B19;

	@FXML
	private Button B20;

	@FXML
	private Button B21;

	@FXML
	private Button B22;

	@FXML
	private Button B23;

	@FXML
	private Button B24;

	@FXML
	private Button B25;

	@FXML
	private Button B26;

	@FXML
	private Button B27;

	@FXML
	private Button B28;

	@FXML
	private Button B29;

	@FXML
	private Button B30;

	@FXML
	private Button B31;

	@FXML
	private Button B32;

	@FXML
	private Button B33;

	@FXML
	private Button B34;

	@FXML
	private Button B35;

	@FXML
	private Button B36;

	@FXML
	private Button B37;

	@FXML
	private Button B38;

	@FXML
	private Button B39;

	@FXML
	private Button B40;

	TextField field;
	Scene scene;

	@FXML
	void B1(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B1.getText());
		}
	}

	@FXML
	void B10(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B10.getText());
		}
	}

	@FXML
	void B11(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B11.getText());
		}
	}

	@FXML
	void B12(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B12.getText());
		}
	}

	@FXML
	void B13(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B13.getText());
		}
	}

	@FXML
	void B14(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B14.getText());
		}
	}

	@FXML
	void B15(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B15.getText());
		}
	}

	@FXML
	void B16(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B16.getText());
		}
	}

	@FXML
	void B17(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B17.getText());
		}
	}

	@FXML
	void B18(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B18.getText());
		}
	}

	@FXML
	void B19(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B19.getText());
		}
	}

	@FXML
	void B2(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B2.getText());
		}
	}

	@FXML
	void B20(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B20.getText());
		}
	}

	@FXML
	void B21(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B21.getText());
		}
	}

	@FXML
	void B22(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B22.getText());
		}
	}

	@FXML
	void B23(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B23.getText());
		}
	}

	@FXML
	void B24(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B24.getText());
		}
	}

	@FXML
	void B25(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B25.getText());
		}
	}

	@FXML
	void B26(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B26.getText());
		}
	}

	@FXML
	void B27(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B27.getText());
		}
	}

	@FXML
	void B28(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B28.getText());
		}
	}

	@FXML
	void B29(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B29.getText());
		}
	}

	@FXML
	void B3(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B3.getText());
		}
	}

	@FXML
	void B30(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B30.getText());
		}
	}

	@FXML
	void B31(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B31.getText());
		}
	}

	@FXML
	void B32(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B32.getText());
		}
	}

	@FXML
	void B33(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B33.getText());
		}
	}

	@FXML
	void B34(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B34.getText());
		}
	}

	@FXML
	void B35(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B35.getText());
		}
	}

	@FXML
	void B36(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B36.getText());
		}
	}

	@FXML
	void B37(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B37.getText());
		}
	}

	@FXML
	void B38(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B38.getText());
		}
	}

	@FXML
	void B39(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B39.getText());
		}
	}

	@FXML
	void B4(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B4.getText());
		}
	}

	@FXML
	void B40(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B40.getText());
		}
	}

	@FXML
	void B5(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B5.getText());
		}
	}

	@FXML
	void B6(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B6.getText());
		}
	}

	@FXML
	void B7(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B7.getText());
		}
	}

	@FXML
	void B8(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B8.getText());
		}
	}

	@FXML
	void B9(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText() + B9.getText());
		}
	}

	@FXML
	void Del(ActionEvent event) {
		if (scene.focusOwnerProperty().get() instanceof TextField) {
			TextField focusedTextArea = (TextField) scene.focusOwnerProperty().get();
			focusedTextArea.setText(focusedTextArea.getText().substring(0, focusedTextArea.getText().length() - 1));
		}
	}

	@FXML
	void Shift(ActionEvent event) {
		if (Shift.getText().contains("↑")) {
			Shift.setText("Shift");
			B1.setText(B1.getText().toLowerCase());
			B2.setText(B2.getText().toLowerCase());
			B3.setText(B3.getText().toLowerCase());
			B4.setText(B4.getText().toLowerCase());
			B5.setText(B5.getText().toLowerCase());
			B6.setText(B6.getText().toLowerCase());
			B7.setText(B7.getText().toLowerCase());
			B8.setText(B8.getText().toLowerCase());
			B9.setText(B9.getText().toLowerCase());
			B10.setText(B10.getText().toLowerCase());
			B11.setText(B11.getText().toLowerCase());
			B12.setText(B12.getText().toLowerCase());
			B13.setText(B13.getText().toLowerCase());
			B14.setText(B14.getText().toLowerCase());
			B15.setText(B15.getText().toLowerCase());
			B16.setText(B16.getText().toLowerCase());
			B17.setText(B17.getText().toLowerCase());
			B18.setText(B18.getText().toLowerCase());
			B19.setText(B19.getText().toLowerCase());
			B20.setText(B20.getText().toLowerCase());
			B21.setText(B21.getText().toLowerCase());
			B22.setText(B22.getText().toLowerCase());
			B23.setText(B23.getText().toLowerCase());
			B24.setText(B24.getText().toLowerCase());
			B25.setText(B25.getText().toLowerCase());
			B26.setText(B26.getText().toLowerCase());
			B27.setText(B27.getText().toLowerCase());
			B28.setText(B28.getText().toLowerCase());
			B29.setText(B29.getText().toLowerCase());
			B30.setText(B30.getText().toLowerCase());
			B31.setText(B31.getText().toLowerCase());
			B32.setText(B32.getText().toLowerCase());
			B33.setText(B33.getText().toLowerCase());
			B34.setText(B34.getText().toLowerCase());
			B35.setText(B35.getText().toLowerCase());
			B36.setText(B36.getText().toLowerCase());
			B37.setText(B37.getText().toLowerCase());
			B38.setText(B38.getText().toLowerCase());
			B39.setText(B39.getText().toLowerCase());
			B40.setText(B40.getText().toLowerCase());
		} else {
			Shift.setText("Shift↑");
			B1.setText(B1.getText().toUpperCase());
			B2.setText(B2.getText().toUpperCase());
			B3.setText(B3.getText().toUpperCase());
			B4.setText(B4.getText().toUpperCase());
			B5.setText(B5.getText().toUpperCase());
			B6.setText(B6.getText().toUpperCase());
			B7.setText(B7.getText().toUpperCase());
			B8.setText(B8.getText().toUpperCase());
			B9.setText(B9.getText().toUpperCase());
			B10.setText(B10.getText().toUpperCase());
			B11.setText(B11.getText().toUpperCase());
			B12.setText(B12.getText().toUpperCase());
			B13.setText(B13.getText().toUpperCase());
			B14.setText(B14.getText().toUpperCase());
			B15.setText(B15.getText().toUpperCase());
			B16.setText(B16.getText().toUpperCase());
			B17.setText(B17.getText().toUpperCase());
			B18.setText(B18.getText().toUpperCase());
			B19.setText(B19.getText().toUpperCase());
			B20.setText(B20.getText().toUpperCase());
			B21.setText(B21.getText().toUpperCase());
			B22.setText(B22.getText().toUpperCase());
			B23.setText(B23.getText().toUpperCase());
			B24.setText(B24.getText().toUpperCase());
			B25.setText(B25.getText().toUpperCase());
			B26.setText(B26.getText().toUpperCase());
			B27.setText(B27.getText().toUpperCase());
			B28.setText(B28.getText().toUpperCase());
			B29.setText(B29.getText().toUpperCase());
			B30.setText(B30.getText().toUpperCase());
			B31.setText(B31.getText().toUpperCase());
			B32.setText(B32.getText().toUpperCase());
			B33.setText(B33.getText().toUpperCase());
			B34.setText(B34.getText().toUpperCase());
			B35.setText(B35.getText().toUpperCase());
			B36.setText(B36.getText().toUpperCase());
			B37.setText(B37.getText().toUpperCase());
			B38.setText(B38.getText().toUpperCase());
			B39.setText(B39.getText().toUpperCase());
			B40.setText(B40.getText().toUpperCase());
		}
	}

	@FXML
	void Space(ActionEvent event) {
		field.setText(field.getText() + " ");
	}

	@FXML
	private void initialize() {
		try {

		} catch (Exception e) {
			DBUtil.LOG_ERROR(e);
		}
	}

	public void setTextField(TextField field, Scene scene) {
		this.field = field;
		this.scene = scene;
	}

}
