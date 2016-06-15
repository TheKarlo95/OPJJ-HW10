package hr.fer.zemris.java.gui.calc.backend;

import java.util.function.UnaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * {@code UnaryOperations} is a enum class that represents operations that will
 * be performed upon one operand of the same type and are supported by
 * {@link Calculator} class.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public enum UnaryOperations {

    /** Sine operation. Returns sin(n) when done. */
    SIN(n -> Math.sin(n)),
    /** Inverse sine operation. Returns arcsin(n) when done. */
    ASIN(n -> Math.asin(n)),
    /** Cosine operation. Returns cos(n) when done. */
    COS(n -> Math.cos(n)),
    /** Inverse cosine operation. Returns arccos(n) when done. */
    ACOS(n -> Math.acos(n)),
    /** Tangent operation. Returns tan(n) when done. */
    TAN(n -> Math.tan(n)),
    /** Inverse tangent operation. Returns arctan(n) when done. */
    ATAN(n -> Math.atan(n)),
    /** Sine operation. Returns sin(n) when done. */
    CTG(n -> Math.tan(1 / n)),
    /** Cotangent operation. Returns ctg(n) when done. */
    ACTG(n -> Math.atan(1 / n)),
    /**
     * Logarithm operation with base 10. Returns log<sub>10</sub>(n) when done.
     */
    LOG(n -> Math.log10(n)),
    /**
     * Exponentiation operation with base 10. Returns 10<sup>n</sup> when done.
     */
    EXP_10(n -> Math.pow(n, 10)),
    /**
     * Logarithm operation with base e. Returns log<sub>e</sub>(n) when done.
     */
    LN(n -> Math.log(n)),
    /**
     * Exponentiation operation with base e. Returns e<sup>n</sup> when done.
     */
    EXP_E(n -> Math.pow(n, Math.E)),
    /**
     * Inverse operation. Returns 1 / n.
     */
    INVERSE(n -> 1 / n);

    /** Operation that will be performed upon one operand of the same type. */
    private final UnaryOperator<Double> operation;

    /**
     * Constructs a newly allocated {@code UnaryOperations} object with
     * specified {@code operation} parameter.
     * 
     * @param operation
     *            Operation that will be performed upon one operand of the
     *            {@link Double} type
     */
    private UnaryOperations(UnaryOperator<Double> operation) {
        this.operation = operation;
    }

    /**
     * Returns a {@code UnaryOperations} object that is represented by specified
     * {@code operator} parameter.
     * 
     * @param name
     *            {@code String} representing an operation
     * @return {@code UnaryOperations} object that is represented by specified
     *         {@code operator} parameter
     * @throws IllegalArgumentException
     *             if operator doesn't equal to "sin", "cos", "tan", "ctg",
     *             "log" or "ln"
     */
    public static UnaryOperations getUnaryOperation(String name) {
        name = name.trim().toUpperCase();

        if (name.equals(SIN.name())) {
            return SIN;
        } else if (name.equals(COS.name())) {
            return COS;
        } else if (name.equals(TAN.name())) {
            return TAN;
        } else if (name.equals(CTG.name())) {
            return CTG;
        } else if (name.equals(LOG.name())) {
            return LOG;
        } else if (name.equals(LN.name())) {
            return LN;
        } else if (name.equals("1 / x")) {
            return INVERSE;
        } else {
            throw new IllegalArgumentException("Specified operator \"" + name + "\" is not a valid operator!");
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
        name = name.trim().toUpperCase();

        if (name.equals("1 / X"))
            return true;

        for (UnaryOperations operation : values()) {
            if (operation.name().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a {@link UnaryOperator} object representing operation.
     * 
     * @return a {@link UnaryOperator} object representing operation
     */
    public UnaryOperator<Double> getOperation() {
        return operation;
    }

    /**
     * Applies this operation to the given arguments.
     * 
     * @param n
     *            the number
     * @return the result of the operation
     */
    public double doOperation(double n) {
        return operation.apply(n);
    }

    /**
     * Returns a {@code UnaryOperations} object that does inverted operation.
     * 
     * @return a {@code BinaryOperations} object that does inverted operation
     */
    public UnaryOperations invert() {
        switch (this) {
            case SIN:
                return ASIN;
            case COS:
                return ACOS;
            case TAN:
                return ATAN;
            case CTG:
                return ACTG;
            case LOG:
                return EXP_10;
            case LN:
                return EXP_E;
            case ASIN:
                return SIN;
            case ACOS:
                return COS;
            case ATAN:
                return TAN;
            case ACTG:
                return CTG;
            case EXP_10:
                return LOG;
            case EXP_E:
                return LN;
            case INVERSE:
                return INVERSE;
            default:
                return null;
        }
    }

}
