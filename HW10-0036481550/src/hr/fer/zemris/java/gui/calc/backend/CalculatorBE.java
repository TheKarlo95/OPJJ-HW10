package hr.fer.zemris.java.gui.calc.backend;

import java.util.EmptyStackException;
import java.util.Stack;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * {@code CalcualtorBE} class represents the back-end component of the
 * calculator application.
 * 
 * @author Karlo Vrbić
 * @see Calculator
 */
public class CalculatorBE {

    /** Stack for storing numbers. */
    private Stack<String> stack;

    /** Current value. */
    private String currentValue;
    /** Value used previously to this one. */
    private String lastValue;
    /** Binary operator. */
    private BinaryOperations operator;

    /** Flag that indicates whether the calculator is in inverted mode. */
    private boolean inverted;

    /**
     * Flag that indicates whether the calculator has a pending binary operation
     * that needs to be done.
     */
    private boolean binaryOperation;

    /** Flag that indicates if floating point number is being entered. */
    private boolean floatNumber;

    /**
     * Constructs a new {@code CalculatorBE} object.
     */
    public CalculatorBE() {
        this.inverted = false;
        this.stack = new Stack<>();
        this.currentValue = "0";
    }

    /**
     * Returns the current value.
     * 
     * @return the current value
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * Processes the pressed button.
     * 
     * @param button
     *            the button name
     */
    public void buttonPressed(String button) {
        if (button.matches("^\\d$")) {
            digitButton(button);
        } else if (button.equals(".")) {
            dotButton();
        } else if (button.equals("+/-")) {
            negativeToggleButton();
        } else if (UnaryOperations.isValidOperator(button)) {
            unaryOperationButton(button);
        } else if (BinaryOperations.isValidOperator(button)) {
            binaryOperationButton(button);
        } else if (button.equals("clr")) {
            clearButton();
        } else if (button.equals("res")) {
            resetButton();
        } else if (button.equals("push")) {
            pushButton();
        } else if (button.equals("pop")) {
            popButton();
        } else if (button.equals("pop")) {
            invertButton();
        }
    }

    /**
     * Processes the pressed digit button.
     * 
     * @param button
     *            the button name
     */
    public void digitButton(String button) {
        if (!button.matches("^\\d$"))
            throw new IllegalArgumentException("Specified button \"" + button + "\" is not a digit!");

        if (currentValue.equals("0") && button.equals("0"))
            return;

        if (binaryOperation && !floatNumber) {
            currentValue = "0";
        }

        if (currentValue.equals("0")) {
            currentValue = button;
        } else {
            currentValue += button;
        }
    }

    /**
     * Processes the dot button.
     */
    public void dotButton() {
        if (currentValue.contains("."))
            throw new IllegalStateException("You cannot add a '.' to number if it's already added!");

        currentValue += '.';
        floatNumber = true;
    }

    /**
     * Processes the dot button.
     */
    public void negativeToggleButton() {
        if (currentValue.startsWith("-")) {
            currentValue = currentValue.substring(1);
        } else if (!currentValue.equals("0")) {
            currentValue = "-" + currentValue;
        }
    }

    /**
     * Processes the buttons for unary operators.
     * 
     * @param button
     *            the button name
     */
    public void unaryOperationButton(String button) {
        if (!UnaryOperations.isValidOperator(button))
            throw new IllegalArgumentException("Specified operator \"" + button + "\" is not a valid operator!");

        if (binaryOperation) {
            doBinaryOperation();
        }

        Double result = null;

        if (!inverted) {
            result = UnaryOperations.getUnaryOperation(button).doOperation(Double.parseDouble(currentValue));
        } else {
            result = UnaryOperations.getUnaryOperation(button).invert().doOperation(Double.parseDouble(currentValue));
        }

        currentValue = format(result);
    }

    /**
     * Processes the buttons for binary operators.
     * 
     * @param button
     *            the button name
     */
    public void binaryOperationButton(String button) {
        if (!BinaryOperations.isValidOperator(button))
            throw new IllegalArgumentException("Specified operator \"" + button + "\" is not a valid operator!");

        if (binaryOperation) {
            doBinaryOperation();
        }

        lastValue = currentValue;

        if (!inverted) {
            operator = BinaryOperations.getBinaryOperation(button);
        } else {
            operator = BinaryOperations.getBinaryOperation(button).invert();
        }

        binaryOperation = true;
    }

    /**
     * Processes the clear button.
     */
    public void clearButton() {
        currentValue = "0";
        inverted = false;
        floatNumber = false;
    }

    /**
     * Processes the reset button.
     */
    public void resetButton() {
        clearButton();

        stack.clear();
        lastValue = null;
        binaryOperation = false;
    }

    /**
     * Processes the push button.
     */
    public void pushButton() {
        stack.push(currentValue);
    }

    /**
     * Processes the pop button.
     */
    public void popButton() {
        try {
            currentValue = stack.pop();
        } catch (EmptyStackException e) {

        }
    }

    /**
     * Processes the invert checkbox.
     */
    public void invertButton() {
        inverted = !inverted;
    }

    /**
     * Processes the equal button.
     */
    public void equalButton() {
        doBinaryOperation();
    }

    /**
     * Executes the binary operation when both of the operands are ready.
     */
    private void doBinaryOperation() {
        if (currentValue == null || lastValue == null || operator == null)
            return;

        Double result = operator.doOperation(Double.parseDouble(lastValue), Double.parseDouble(currentValue));

        currentValue = format(result);
        lastValue = null;
        operator = null;
        binaryOperation = false;
        floatNumber = false;
    }

    /**
     * Converts double to {@code String}.
     * 
     * @param num
     *            number
     * @return {@code String} representation of a number
     */
    public static String format(double num) {
        if (num == (long) num)
            return String.format("%d", (long) num);
        else
            return String.format("%s", num);
    }

}
