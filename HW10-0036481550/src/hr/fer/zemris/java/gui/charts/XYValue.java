package hr.fer.zemris.java.gui.charts;

/**
 * {@code XYValue} class represents a pair of two integer values, X and Y.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class XYValue {

    /** Value X. */
    private final int x;
    /** Value Y. */
    private final int y;

    /**
     * Constructs a new {@code XYValue} object with specified X and Y values.
     * 
     * @param x
     *            value X
     * @param y
     *            value Y
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X value.
     * 
     * @return the X value
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y value.
     * 
     * @return the Y value
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the newly constructed {@code XYValue} object out of
     * {@code String} parameter.
     * 
     * @param s
     *            the string representing {@code XYValue} object
     * @return {@code XYValue} object
     */
    public static XYValue valueOf(String s) {
        if (s == null)
            throw new NullPointerException("You cannot pass a null reference as a parameter!");

        String[] tmp = s.trim().split("\\s*,\\s*");

        int x = Integer.valueOf(tmp[0]);
        int y = Integer.valueOf(tmp[1]);

        return new XYValue(x, y);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

}