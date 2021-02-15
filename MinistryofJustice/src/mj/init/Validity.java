package mj.init;

import com.jyloo.syntheticafx.DecoratorPane;
import com.jyloo.syntheticafx.binding.BooleanObjectBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class Validity<T extends Node> extends ObjectPropertyBase<Boolean> {
	private static final Object DEFAULT_BEAN = null;

	@SuppressWarnings("unused")
	private static final String DEFAULT_NAME = "";

	private T owner;

	private Validator<T> validator;
	DecoratorPane decoPane;
	ChangeListener<Boolean> focusListener;
	ObservableValue<?> property;
	ChangeListener<Object> propertyListener;

	public Validity(T owner, Validator<T> v, Boolean initialValue) {
		super(initialValue);
		this.owner = owner;
		this.validator = v;
	}

	public Object getBean() {
		return DEFAULT_BEAN;
	}

	public String getName() {
		return "";
	}

	public boolean isValid() {
		return this.validator.isValid(this.owner);
	}

	public void revalidate() {
		set(Boolean.valueOf(isValid()));
	}

	public BooleanObjectBinding and(Validity<?> other) {
		return BooleanObjectBinding.and(this, other);
	}

	public BooleanObjectBinding or(Validity<?> other) {
		return BooleanObjectBinding.or(this, other);
	}

	public BooleanObjectBinding not() {
		return BooleanObjectBinding.not(this);
	}

	public BooleanObjectBinding isEqualTo(Validity<?> other) {
		return BooleanObjectBinding.isEqualTo(this, other);
	}

	public BooleanObjectBinding isNotEqualTo(Validity<?> other) {
		return BooleanObjectBinding.isNotEqualTo(this, other);
	}

	public BooleanExpression expression() {
		return BooleanExpression.booleanExpression(this);
	}

	public String toString() {
		Object bean = getBean();
		String name = getName();
		StringBuilder result = new StringBuilder("Validity [");
		if (bean != null)
			result.append("bean: ").append(bean).append(", ");
		if (name != null && !name.equals(""))
			result.append("name: ").append(name).append(", ");
		if (isBound())
			result.append("bound, ");
		result.append("value: ").append(get());
		result.append("]");
		return result.toString();
	}
}
