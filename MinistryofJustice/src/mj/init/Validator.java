package mj.init;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public interface Validator<T extends Node> {
	boolean isValid(T paramT);

	static <T extends javafx.scene.control.CheckBox> Validator<T> createEmptyValidator(T owner) {
		return new Validator<T>() {

			public boolean isValid(T n) {
				return n.isSelected();
			}
		};
	}

	static <T extends javafx.scene.control.ChoiceBox<?>> Validator<T> createEmptyValidator(T owner) {
		return new Validator<T>() {

			public boolean isValid(T n) {
				return (n.getValue() != null);
			}
		};
	}

	static <T extends javafx.scene.control.Slider> Validator<T> createEmptyValidator(T owner) {
		return new Validator<T>() {

			public boolean isValid(T n) {
				return (n.getValue() != Double.MIN_VALUE);
			}
		};
	}

	static <T extends javafx.scene.control.Spinner<?>> Validator<T> createEmptyValidator(T owner) {
		return new Validator<T>() {

			public boolean isValid(T n) {
				Object value = n.getValue();
				Validation.Type type = (Validation.Type) n.getProperties().get("SyntheticaFX.Validation.Type");
				if (n instanceof javafx.scene.control.Spinner && n.isEditable() && type == Validation.Type.EAGER) {

					value = n.getEditor().getText();
					return (value == null) ? false : ((value.toString().trim().length() != 0));
				}
				return (value != null);
			}
		};
	}

	static <T extends javafx.scene.control.ComboBoxBase<?>> Validator<T> createEmptyValidator(T owner) {
		return new Validator<T>() {

			@SuppressWarnings("rawtypes")
			public boolean isValid(T n) {
				Object value = n.getValue();
				Validation.Type type = (Validation.Type) n.getProperties().get("SyntheticaFX.Validation.Type");
				if (n instanceof ComboBox && n.isEditable() && type == Validation.Type.EAGER) {
					value = ((ComboBox) n).getEditor().getText();
				} else if (n instanceof DatePicker && n.isEditable() && type == Validation.Type.EAGER) {
					value = ((DatePicker) n).getEditor().getText();
				}
				return (value == null) ? false : ((value.toString().trim().length() != 0));
			}
		};
	}

	static <T extends javafx.scene.control.TextInputControl> Validator<T> createEmptyValidator(T owner) {
		return new Validator<T>() {

			public boolean isValid(T n) {
				String text = n.getText();
				return (text == null) ? false : ((text.trim().length() != 0));
			}
		};
	}
}