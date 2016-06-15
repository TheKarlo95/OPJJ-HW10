package hr.fer.zemris.java.gui.calc.backend;

import java.util.function.BinaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * {@code BinaryOperations} is a enum class that represents operations that will
 * be performed upon two operands of the same type and are supported by
 * {@link Calculator} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public enum BinaryOperations {

    /** Addition operation. Returns n1 + n2 when done. */
    ADD((n1, n2) -> n1 + n2),
    /** Subtraction operation. Returns n1 - n2 when done. */
    SUB((n1, n2) -> n1 - n2),
    /** Multiplication operation. Returns n1 * n2 when done. */
    MUL((n1, n2) -> n1 * n2),
    /** Division operation. Returns n1 / n2 when done. */
    DIV((n1, n2) -> n1 / n2),
    /** Exponentiatio operation. Returns n1<sup>n2</sup> when done. */
    EXP((n1, n2) -> Math.pow(n1, n2)),
    /** Root operation. Returns n1<sup>1 / n2</sup> when done. */
    ROOT((n1, n2) -> Math.pow(n1, 1 / n2));

    /** Operation that will be performed upon two operands of the same type. */
    private final BinaryOperator<Double> operation;

    /**
     * Constructs a newly allocated {@code BinaryOperations} object with
     * specified {@code operation} parameter.
     * 
     * @param operation
     *            Operation that will be performed upon two operands of the
     *            {@link Double} type
     */
    private BinaryOperations(BinaryOperator<Double> operation) {
        this.operation = operation;
    }

    /**
     * Returns a {@code BinaryOperations} object that is represented by
     * specified {@code operator} parameter.
     * 
     * @param operator
     *            {@code String} representing an operation
     * @return {@code BinaryOperations} object that is represented by specified
     *         {@code operator} parameter
     * @throws IllegalArgumentException
     *             if operator doesn't equal to "+", "-", "*" or "\"
     */
    public static BinaryOperations getBinaryOperation(String operator) {
        switch (operator) {
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "×":
                return MUL;
            case "÷":
                return DIV;
            case "<html>x<sup>n</sup></html>":
                return EXP;
            default:
                throw new IllegalArgumentException("Specified operator \"" + operator + "\" is not a valid operator!");
        }
    }

    /**
     * Checks if specified {@code name} parameter is a valid operator name.
     * 
     * @param name
     *            the name of the operator
     * @return {@code true} if specified {@code name} parameter is a valid
     *         operator name; {@code false} otherwise
     */
    public static boolean isValidOperator(String name) {
        name = name.trim().toLowerCase();

        return name.matches("^\\+|-|×|÷|<html>x<sup>n</sup></html>$");
    }

    /**
     * Returns a {@link BinaryOperator} object representing operation.
     * 
     * @return a {@link BinaryOperator} object representing operation
     */
    public BinaryOperator<Double> getOperation() {
        return operation;
    }

    /**
     * Applies this operation to the given arguments.
     * 
     * @param n1
     *            the first number
     * @param n2
     *            the second number
     * @return the result of the operation
     */
    public double doOperation(double n1, double n2) {
        return operation.apply(n1, n2);
    }

    /**
     * Returns a {@code BinaryOperations} object that does inverted operation.
     * <p>
     * For {@code ADD}, {@code SUB}, {@code MUL} and {@code DIV} this method
     * returns the same object.
     * 
     * @return a {@code BinaryOperations} object that does inverted operation
     */
    public BinaryOperations invert() {
        switch (this) {
            case ADD:
                return ADD;
            case SUB:
                return SUB;
            case MUL:
                return MUL;
            case DIV:
                return DIV;
            case EXP:
                return ROOT;
            case ROOT:
                return EXP;
            default:
                return null;
        }
    }

}
