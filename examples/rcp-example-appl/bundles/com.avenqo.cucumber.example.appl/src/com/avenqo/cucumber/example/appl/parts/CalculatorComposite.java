package com.avenqo.cucumber.example.appl.parts;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Based on http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/CalculatorbyMichaelSchmidt.htm
 * 
 * @author Heiko
 *
 */
public class CalculatorComposite extends Composite {

	/*
	 * Initialize variables needed for this class.
	 */
	private Text displayText;
	// The three calculator registers.
	private String displayString = "0.";
	private String memoryString = new String();
	private String operatorString = new String();
	// A variable to store the pending calculation
	private char calcChar = ' ';
	// Error strings
	private final String ERROR_STRING = "Error: ";
	private final String NAN_STRING = "Not a Number";
	private final String LONG_STRING = "Number too long";
	private final String INFINITY_STRING = "Infinity";
	// A flag to check if display should be cleared on the next keystroke
	private boolean clearDisplay = true;

	public CalculatorComposite(Composite parent) {
		super(parent, SWT.BORDER);
	}

	@Override
	public boolean setFocus () {
		return displayText.setFocus();
	}
	
	
	public void create() {
		CalculatorComposite container = this;
		final GridLayout calculatorGridLayout = new GridLayout();
		calculatorGridLayout.marginRight = 5;
		calculatorGridLayout.marginLeft = 5;
		calculatorGridLayout.marginBottom = 5;
		calculatorGridLayout.marginTop = 5;
		calculatorGridLayout.marginWidth = 10;
		calculatorGridLayout.marginHeight = 2;
		calculatorGridLayout.numColumns = 4;
		calculatorGridLayout.verticalSpacing = 2;
		calculatorGridLayout.makeColumnsEqualWidth = true;
		calculatorGridLayout.horizontalSpacing = 2;
		container.setLayout(calculatorGridLayout);

		// The display. Note that it has a limit of 30 characters,
		// much greater than the length of a double-precision number.
		displayText = new Text(container, SWT.RIGHT | SWT.BORDER);
		displayText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		displayText.setEditable(false);
		displayText.setDoubleClickEnabled(false);
		displayText.setTextLimit(30);
		displayText.setText(displayString);
		displayText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 4, 1));

		final Button msButton = new Button(container, SWT.NONE);
		msButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateMemory('S');
			}
		});
		msButton.setToolTipText("Save value to Memory");
		msButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		msButton.setText("MS");

		final Button mcButton = new Button(container, SWT.NONE);
		mcButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('D');
			}
		});
		mcButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		mcButton.setToolTipText("Clear Memory");
		mcButton.setText("MC");

		final Button clearButton = new Button(container, SWT.NONE);
		clearButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('C');
			}
		});
		clearButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		clearButton.setToolTipText("Clear all Calculator Registers");
		clearButton.setText("C");

		final Button ceButton = new Button(container, SWT.NONE);
		ceButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('E');
			}
		});
		ceButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		ceButton.setToolTipText("Clear Entry");
		ceButton.setText("CE");

		final Button memAddButton = new Button(container, SWT.NONE);
		memAddButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateMemory('+');
			}
		});
		memAddButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		memAddButton.setToolTipText("Add value to Memory");
		memAddButton.setText("M+");

		final Button mrButton = new Button(container, SWT.NONE);
		mrButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('R');
			}
		});
		mrButton.setToolTipText("Recall value in Memory");
		mrButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		mrButton.setText("MR");

		final Button backButton = new Button(container, SWT.NONE);
		backButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('B');
			}
		});
		backButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		backButton.setToolTipText("Backspace");
		backButton.setText("BACK");

		final Button divideButton = new Button(container, SWT.NONE);
		divideButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateCalc('/');
			}
		});
		divideButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		divideButton.setToolTipText("Divide");
		divideButton.setText("/");

		final Button memSubtractButton = new Button(container, SWT.NONE);
		memSubtractButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateMemory('-');
			}
		});
		memSubtractButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		memSubtractButton.setToolTipText("Subtract value from Memory");
		memSubtractButton.setText("M-");

		final Button inverseButton = new Button(container, SWT.NONE);
		inverseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('I');
			}
		});
		inverseButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		inverseButton.setToolTipText("Inverse of value");
		inverseButton.setText("1/X");

		final Button sqrtButton = new Button(container, SWT.NONE);
		sqrtButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('Q');
			}
		});
		sqrtButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		sqrtButton.setToolTipText("Square Root of value");
		sqrtButton.setText("SQRT");

		final Button multiplyButton = new Button(container, SWT.NONE);
		multiplyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateCalc('*');
			}
		});
		multiplyButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		multiplyButton.setToolTipText("Multiply");
		multiplyButton.setText("*");

		final Button num7Button = new Button(container, SWT.NONE);
		num7Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('7');
			}
		});
		num7Button.setToolTipText("Numeric Pad");
		num7Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num7Button.setText("7");

		final Button num8Button = new Button(container, SWT.NONE);
		num8Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('8');
			}
		});
		num8Button.setToolTipText("Numeric Pad");
		num8Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num8Button.setText("8");

		final Button num9Button = new Button(container, SWT.NONE);
		num9Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('9');
			}
		});
		num9Button.setToolTipText("Numeric Pad");
		num9Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num9Button.setText("9");

		final Button SubtractButton = new Button(container, SWT.NONE);
		SubtractButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateCalc('-');
			}
		});
		SubtractButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		SubtractButton.setToolTipText("Subtract");
		SubtractButton.setText("-");

		final Button num4Button = new Button(container, SWT.NONE);
		num4Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('4');
			}
		});
		num4Button.setToolTipText("Numeric Pad");
		num4Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num4Button.setText("4");

		final Button num5Button = new Button(container, SWT.NONE);
		num5Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('5');
			}
		});
		num5Button.setToolTipText("Numeric Pad");
		num5Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num5Button.setText("5");

		final Button num6Button = new Button(container, SWT.NONE);
		num6Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('6');
			}
		});
		num6Button.setToolTipText("Numeric Pad");
		num6Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num6Button.setText("6");

		final Button AdditionButton = new Button(container, SWT.NONE);
		AdditionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateCalc('+');
			}
		});
		AdditionButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		AdditionButton.setToolTipText("Add");
		AdditionButton.setText("+");

		final Button num1Button = new Button(container, SWT.NONE);
		num1Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('1');
			}
		});
		num1Button.setCapture(true);
		num1Button.setToolTipText("Numeric Pad");
		num1Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num1Button.setText("1");

		final Button num2Button = new Button(container, SWT.NONE);
		num2Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('2');
			}
		});
		num2Button.setToolTipText("Numeric Pad");
		num2Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num2Button.setText("2");

		final Button num3Button = new Button(container, SWT.NONE);
		num3Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('3');
			}
		});
		num3Button.setToolTipText("Numeric Pad");
		num3Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num3Button.setText("3");

		final Button equalsButton = new Button(container, SWT.NONE);
		equalsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateCalc('=');
			}
		});
		equalsButton.setToolTipText("Equals (get result)");
		equalsButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		equalsButton.setText("=");

		final Button num0Button = new Button(container, SWT.NONE);
		num0Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('0');
			}
		});
		num0Button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		num0Button.setToolTipText("Numeric Pad");
		num0Button.setText("0");

		final Button decimalButton = new Button(container, SWT.NONE);
		decimalButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('.');
			}
		});
		decimalButton.setToolTipText("Numeric Pad");
		decimalButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		decimalButton.setText(".");

		final Button signButton = new Button(container, SWT.NONE);
		signButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				updateDisplay('-');
			}
		});
		signButton.setToolTipText("Change sign of value");
		signButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		signButton.setText("+/-");
		new Label(container, SWT.NONE);
		//
	}

	/*
	 * This method updates the display text based on user input.
	 */
	private void updateDisplay(final char keyPressed) {
		char keyVal = keyPressed;
		String tempString = new String();
		boolean doClear = false;

		if (!clearDisplay) {
			tempString = displayString;
		}

		switch (keyVal) {
		case 'B': // Backspace
			if (tempString.length() < 2) {
				tempString = "";
			} else {
				tempString = tempString.substring(0, tempString.length() - 1);
			}
			break;

		case 'C': // Clear
			tempString = "0.";
			operatorString = "";
			calcChar = ' ';
			doClear = true;
			break;

		case 'D': // Clear Memory
			memoryString = "";
			break;

		case 'E': // Clear Entry
			tempString = "0.";
			doClear = true;
			break;

		case 'I': // Inverse
			tempString = doCalc(displayString, "", keyVal);
			doClear = true;
			break;

		case 'Q': // Square Root
			tempString = doCalc(displayString, "", keyVal);
			doClear = true;
			break;

		case 'R': // Recall Memory to Display
			tempString = memoryString;
			doClear = true;
			break;

		case '-': // Change Sign
			if (tempString.startsWith("-")) {
				tempString = tempString.substring(1, tempString.length());
			} else {
				tempString = keyVal + tempString;
			}
			break;

		case '.': // Can't have two decimal points.
			if (tempString.indexOf(".") == -1 && tempString.length() < 29) {
				tempString = tempString + keyVal;
			}
			break;

		case '0': // Don't want 00 to be entered.
			if (!tempString.equals("0") && tempString.length() < 29) {
				tempString = tempString + keyVal;
			}
			break;

		default: // Default case is for the digits 1 through 9.
			if (tempString.length() < 29) {
				tempString = tempString + keyVal;
			}
			break;
		}

		clearDisplay = doClear;
		if (!displayString.equals(tempString)) {
			displayString = tempString;
			displayText.setText(displayString);
		}
	}

	private void updateMemory(final char keyPressed) {
		char keyVal = keyPressed;
		String tempString = new String();

		switch (keyVal) {
		case 'S': // Save to Memory
			tempString = trimString(displayString);
			break;

		case '+': // Add to Memory
			if (memoryString.length() == 0) {
				tempString = trimString(displayString);
			} else {
				tempString = doCalc(memoryString, displayString, '+');
			}
			break;

		case '-': // Subtract from Memory
			if (memoryString.length() == 0) {
				if (displayString.startsWith("-")) {
					tempString = displayString.substring(1, displayString.length());
					tempString = trimString(tempString);
				} else if (displayString.equals("0.0") || displayString.equals("0") || displayString.equals("0.")
						|| displayString.equals("-0.0")) {
					tempString = "0";
				} else {
					tempString = keyVal + displayString;
					tempString = trimString(displayString);
				}
			} else {
				tempString = doCalc(memoryString, displayString, '-');
			}
			break;

		default: // Do nothing - this should never happen.
			break;
		}

		// Do not save invalid entries to memory.
		if (tempString.startsWith(ERROR_STRING)) {
			if (!displayString.equals(tempString)) {
				displayString = tempString;
				displayText.setText(displayString);
			}
		} else {
			memoryString = tempString;
		}
		clearDisplay = true;

	}

	private String doCalc(final String valAString, final String valBString, final char opChar) {
		String resultString = ERROR_STRING + NAN_STRING;
		Double valA = 0.0;
		Double valB = 0.0;
		Double valAnswer = 0.0;

		// Make sure register strings are numbers
		if (valAString.length() > 0) {
			try {
				valA = Double.parseDouble(valAString);
			} catch (NumberFormatException e) {
				return resultString;
			}
		} else {
			return resultString;
		}

		if (opChar != 'Q' && opChar != 'I') {
			if (valBString.length() > 0) {
				try {
					valB = Double.parseDouble(valBString);
				} catch (NumberFormatException e) {
					return resultString;
				}
			} else {
				return resultString;
			}
		}

		switch (opChar) {
		case 'Q': // Square Root
			valAnswer = Math.sqrt(valA);
			break;

		case 'I': // Inverse
			valB = 1.0;
			valAnswer = valB / valA;
			break;

		case '+': // Addition
			valAnswer = valA + valB;
			break;

		case '-': // Subtraction
			valAnswer = valA - valB;
			break;

		case '/': // Division
			valAnswer = valA / valB;
			break;

		case '*': // Multiplication
			valAnswer = valA * valB;
			break;

		default: // Do nothing - this should never happen
			break;

		}

		// Convert answer to string and format it before return.
		resultString = valAnswer.toString();
		resultString = trimString(resultString);
		return resultString;
	}

	/*
	 * This method updates the operator and display strings, and the pending
	 * calculation flag.
	 */
	private void updateCalc(char keyPressed) {
		char keyVal = keyPressed;
		String tempString = displayString;

		/*
		 * If there is no display value, the keystroke is deemed invalid and nothing is
		 * done.
		 */
		if (tempString.length() == 0) {
			return;
		}

		/*
		 * If there is no operator value, only calculation key presses are considered
		 * valid. Check that the display value is valid and if so, move the display
		 * value to the operator. No calculation is done.
		 */
		if (operatorString.length() == 0) {
			if (keyVal != '=') {
				tempString = trimString(tempString);
				if (tempString.startsWith(ERROR_STRING)) {
					clearDisplay = true;
					operatorString = "";
					calcChar = ' ';
				} else {
					operatorString = tempString;
					calcChar = keyVal;
					clearDisplay = true;
				}
			}
			return;
		}

		// There is an operator and a display value, so do the calculation.
		displayString = doCalc(operatorString, tempString, calcChar);

		/*
		 * If '=' was pressed or result was invalid, reset pending calculation flag and
		 * operator value. Otherwise, set new calculation flag so calculations can be
		 * chained.
		 */
		if (keyVal == '=' || displayString.startsWith(ERROR_STRING)) {
			calcChar = ' ';
			operatorString = "";
		} else {
			calcChar = keyVal;
			operatorString = displayString;
		}

		// Set the clear display flag and show the result.
		clearDisplay = true;
		displayText.setText(displayString);
	}

	/*
	 * This method formats a string.
	 */
	private String trimString(final String newString) {
		String tempString = newString;

		// Value is not a number
		if (tempString.equals("NaN")) {
			tempString = ERROR_STRING + NAN_STRING;
			return tempString;
		}
		// Value is infinity
		if (tempString.equals("Infinity") || tempString.equals("-Infinity")) {
			tempString = ERROR_STRING + INFINITY_STRING;
			return tempString;
		}
		// Value is -0
		if (tempString.equals(-0.0)) {
			tempString = "0";
			return tempString;
		}
		// Trim unnecessary trailing .0
		if (tempString.endsWith(".0")) {
			tempString = tempString.substring(0, tempString.length() - 2);
		}
		// String is too long to display
		if (tempString.length() > 28) {
			tempString = ERROR_STRING + LONG_STRING;
		}

		return tempString;
	}
}
