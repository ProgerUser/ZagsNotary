package mj.init;

import com.jyloo.syntheticafx.DecoratorPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class Validation {
	static final String SYNTHETICAFX_VALIDATION_TYPE = "SyntheticaFX.Validation.Type";
	private static final PseudoClass INVALID_PSEUDOCLASS = PseudoClass.getPseudoClass("invalid");

	public enum Type {
		MANUAL,

		LAZY,

		EAGER;
	}

	public static <T extends javafx.scene.control.CheckBox> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator) {
		return installValidator(owner, v, type, decorator, (Pos) null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.CheckBox> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator, Pos position) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.selectedProperty(), type,
				new Decorator(decorator, position), null, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.CheckBox> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Decorator decorator) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.selectedProperty(), type, decorator, null,
				null);
	}

	public static <T extends javafx.scene.control.ChoiceBox<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator) {
		return installValidator(owner, v, type, decorator, (Pos) null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.ChoiceBox<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator, Pos position) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type,
				new Decorator(decorator, position), null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.ChoiceBox<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Decorator decorator) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type, decorator, null,
				null);
	}

	public static <T extends javafx.scene.control.Slider> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator) {
		return installValidator(owner, v, type, decorator, (Pos) null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.Slider> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator, Pos position) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type,
				new Decorator(decorator, position), null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.Slider> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Decorator decorator) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type, decorator, null,
				null);
	}

	public static <T extends javafx.scene.control.Spinner<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator) {
		return installValidator(owner, v, type, decorator, (Pos) null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.Spinner<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator, Pos position) {
		if (owner instanceof javafx.scene.control.Spinner && owner.isEditable() && type == Type.EAGER)
			return (Validity) installValidator((Node) owner, (Validator) v, owner.getEditor().textProperty(), type,
					new Decorator(decorator, position), null, null);
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type,
				new Decorator(decorator, position), null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.Spinner<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Decorator decorator) {
		if (owner instanceof javafx.scene.control.Spinner && owner.isEditable() && type == Type.EAGER)
			return (Validity) installValidator((Node) owner, (Validator) v, owner.getEditor().textProperty(), type,
					decorator, null, null);
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type, decorator, null,
				null);
	}

	public static <T extends javafx.scene.control.ComboBoxBase<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator) {
		return installValidator(owner, v, type, decorator, (Pos) null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.ComboBoxBase<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Node decorator, Pos position) {
		if (owner instanceof ComboBox && owner.isEditable() && type == Type.EAGER)
			return (Validity) installValidator((Node) owner, (Validator) v,
					((ComboBox) owner).getEditor().textProperty(), type, new Decorator(decorator, position), null,
					null);
		if (owner instanceof DatePicker && owner.isEditable() && type == Type.EAGER)
			return (Validity) installValidator((Node) owner, (Validator) v,
					((DatePicker) owner).getEditor().textProperty(), type, new Decorator(decorator, position), null,
					null);
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type,
				new Decorator(decorator, position), null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.ComboBoxBase<?>> Validity<T> installValidator(T owner, Validator<T> v,
			Type type, Decorator decorator) {
		if (owner instanceof ComboBox && owner.isEditable() && type == Type.EAGER)
			return (Validity) installValidator((Node) owner, (Validator) v,
					((ComboBox) owner).getEditor().textProperty(), type, decorator, null, null);
		if (owner instanceof DatePicker && owner.isEditable() && type == Type.EAGER)
			return (Validity) installValidator((Node) owner, (Validator) v,
					((DatePicker) owner).getEditor().textProperty(), type, decorator, null, null);
		return (Validity) installValidator((Node) owner, (Validator) v, owner.valueProperty(), type, decorator, null,
				null);
	}

	public static <T extends javafx.scene.control.TextInputControl> Validity<T> installValidator(T owner,
			Validator<T> v, Type type, Node decorator) {
		return installValidator(owner, v, type, decorator, (Pos) null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.TextInputControl> Validity<T> installValidator(T owner,
			Validator<T> v, Type type, Node decorator, Pos position) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.textProperty(), type,
				new Decorator(decorator, position), null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends javafx.scene.control.TextInputControl> Validity<T> installValidator(T owner,
			Validator<T> v, Type type, Decorator decorator) {
		return (Validity) installValidator((Node) owner, (Validator) v, owner.textProperty(), type, decorator, null,
				null);
	}

	public static <T extends Node> Validity<T> installValidator(T owner, Validator<T> v, ObservableValue<?> property,
			Type type, Decorator decorator, String styleClass, Boolean initialValidity) {
		return (decorator == null)
				? installValidator(owner, v, property, type, null, null, 0.0D, 0.0D, styleClass, initialValidity)
				: installValidator(owner, v, property, type, decorator.getNode(), decorator.getPosition(),
						decorator.getXOffset(), decorator.getYOffset(), styleClass, initialValidity);
	}

	public static <T extends Node> Validity<T> installValidator(final T owner, final Validator<T> v,
			ObservableValue<?> property, Type type, final Node decorator, final Pos position, final double xOffset,
			final double yOffset, final String styleClass, Boolean initialValidity) {
		final Validity<T> validity = new Validity<>(owner, v, initialValidity);
		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {

			public void changed(ObservableValue<? extends Boolean> o, Boolean ov, Boolean nv) {
				owner.pseudoClassStateChanged(Validation.INVALID_PSEUDOCLASS, !nv.booleanValue());

				if (styleClass != null) {

					ObservableList<String> styles = owner.getStyleClass();
					if (nv.booleanValue() && styles.contains(styleClass)) {
						styles.remove(styleClass);
					} else if (!nv.booleanValue() && !styles.contains(styleClass)) {
						styles.add(styleClass);
					}
				}
				if (decorator != null) {

					DecoratorPane decoPane = validity.decoPane;
					if (nv.booleanValue() && decoPane != null) {

						decoPane.uninstall();
						validity.decoPane = null;
					} else if (!nv.booleanValue() && decoPane == null) {

						decoPane = DecoratorPane.install(owner, decorator);
						validity.decoPane = decoPane;
						if (position != null)
							decoPane.setDecoratorPosition(position);
						if (xOffset != 0.0D)
							decoPane.setDecoratorXOffset(xOffset);
						if (yOffset != 0.0D)
							decoPane.setDecoratorYOffset(yOffset);
					}
				}
			}
		};
		validity.addListener(listener);

		owner.getProperties().remove("SyntheticaFX.Validation.Type");

		if (type == Type.LAZY || type == Type.EAGER) {

			owner.getProperties().put("SyntheticaFX.Validation.Type", Type.LAZY);

			Platform.runLater(() -> {
				ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {

					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (newValue.booleanValue())
							return;
						validity.setValue(Boolean.valueOf(v.isValid(owner)));
					}
				};
				owner.focusedProperty().addListener(changeListener);
				validity.focusListener = changeListener;
			});
		}
		if (property != null && type == Type.EAGER) {

			owner.getProperties().put("SyntheticaFX.Validation.Type", Type.EAGER);
			validity.property = property;
			ChangeListener<Object> changeListener = new ChangeListener<Object>() {

				public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
					validity.setValue(Boolean.valueOf(v.isValid(owner)));
				}
			};
			property.addListener(changeListener);
			validity.propertyListener = changeListener;
		}

		return validity;
	}

	public static void uninstallValidator(Node owner, Validity<?> validity) {
		DecoratorPane decoPane = validity.decoPane;
		if (decoPane != null) {

			decoPane.uninstall();
			validity.decoPane = null;
		}

		ObservableValue<?> property = validity.property;
		if (property != null) {

			property.removeListener(validity.propertyListener);
			validity.property = null;
			validity.propertyListener = null;
		}

		owner.focusedProperty().removeListener(validity.focusListener);
		validity.focusListener = null;
	}
}
