package com.avenqo.cucumber.example.appl.parts;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class CalculatorPart {

	@Inject
	private MPart part;
	private CalculatorComposite calculatorComposite;
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new FillLayout());
		calculatorComposite = new CalculatorComposite(parent);
		calculatorComposite.create();
		parent.layout();
	}

	@Focus
	public void setFocus() {
		calculatorComposite.setFocus();
	}

	@Persist
	public void save() {
		part.setDirty(false);
	}

}