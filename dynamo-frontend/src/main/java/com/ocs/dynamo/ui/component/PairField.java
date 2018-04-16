package com.ocs.dynamo.ui.component;

import org.apache.commons.lang3.tuple.Pair;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;

public class PairField<L, R> extends CustomField<Pair<L, R>> {

	private Field<L> left;
	private Field<R> right;
	private Component middle;
	
	public PairField(Field<L> left, Field<R> right) {
		this(left, right, null);
	}

	public PairField(Field<L> left, Field<R> right, Component middle) {
		if (left == null || right == null) {
			throw new IllegalArgumentException();
		}
		this.left = left;
		this.right = right;
		this.middle = middle;
		ValueChangeListener propagator = new EventPropagator();
		left.addValueChangeListener(propagator);
		right.addValueChangeListener(propagator);
	}
	
	private class EventPropagator implements ValueChangeListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2613566569836700937L;

		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			PairField.this.fireValueChange(false);
		}
	}
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8008364520978553938L;

	@Override
	protected Component initContent() {
		HorizontalLayout root = new HorizontalLayout();
		root.setHeight(null);
		root.setWidth(null);
		root.addComponent(left);
		if (middle != null) {
			root.addComponent(middle);
		}
		root.addComponent(right);

		return root;
	}

	@Override
	protected void setInternalValue(Pair<L, R> newFieldValue) {
		if (newFieldValue != null) {
			left.setValue(newFieldValue.getLeft());
			right.setValue(newFieldValue.getRight());
		} else {
			left.setValue(null);
			right.setValue(null);
		}
	}

	@Override
	protected Pair<L, R> getInternalValue() {
		return Pair.of(left.getValue(), right.getValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Pair<L, R>> getType() {
		return (Class<Pair<L, R>>) (Class<?>) Pair.class;
	}
}
