package hr.fer.zemris.java.gui.layouts;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code RCPosition} class is a representation of a position in a
 * {@link CalcLayout}.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see CalcLayout
 */
public class RCPosition {

    /** Maximum cache size. */
    private static final int MAX_CACHE_SIZE = 8;

    /** Maximum index of a row. */
    public static final int MAX_ROWS = CalcLayout.NROWS;
    /** Maximum index of a column. */
    public static final int MAX_COLS = CalcLayout.NCOLS;

    /** Pattern for checking if given string is of right format. */
    private static final Pattern PATTERN = Pattern
            .compile("^(1),\\s*([167])|([2-5]),\\s*([1-7])$");

    /** Cache for previously instantiated {@code RCPosition} objects */
    private static LinkedHashMap<Integer, RCPosition> CACHE = new LinkedHashMap<>();

    /** The index of row. */
    private final int row;
    /** The index of column. */
    private final int col;

    /**
     * Constructs a newly allocated {@code RCPosition} object that represents
     * the position in {@link CalcLayout} with specified index of row and
     * column.
     * 
     * @param row
     *            the index of row
     * @param col
     *            the index of column
     */
    public RCPosition(int row, int col) {
        if (!PATTERN.matcher(row + ", " + col).matches())
            throw new IllegalArgumentException(String.format(
                    "Valid input is of format \"(1-%d, 1-%d)\"(except \"(1, 2-5)\"). You inputed \"(%d, %d)\"",
                    MAX_ROWS,
                    MAX_COLS,
                    row,
                    col));

        this.row = row;
        this.col = col;
    }

    /**
     * Returns an {@code RCPosition} object with specified row and column index.
     * <p>
     * This method doesn't always returns the new object since all instantiated
     * objects are internally stored so they can be reused. If this method is
     * called with same Parameters more than one time same object will be
     * returned.
     * 
     * @param row
     *            the index of row
     * @param col
     *            the index of column
     * @return the {@code RCPosition} object with the specified row and column
     *         index
     */
    public static RCPosition valueOf(int row, int col) {
        int key = cacheKey(row, col);

        if (CACHE.containsKey(key))
            return CACHE.get(key);

        RCPosition newPosition = new RCPosition(row, col);

        cache(newPosition);

        return newPosition;
    }

    /**
     * Returns an {@code RCPosition} object holding the index of row and column
     * from the specified {@code String}.
     * 
     * @param s
     *            the string to be parsed.
     * @return the {@code RCPosition} object with the row and column index
     *         specified by {@code String} parameter
     */
    public static RCPosition valueOf(String s) {
        Matcher matcher = PATTERN.matcher(s.trim());

        if (!matcher.matches()) {
            throw new IllegalArgumentException("");
        }

        String rowString = matcher.group(1) != null ? matcher.group(1) : matcher.group(3);
        String colString = matcher.group(2) != null ? matcher.group(2) : matcher.group(4);

        int row = Integer.valueOf(rowString);
        int col = Integer.valueOf(colString);

        return valueOf(row, col);
    }

    /**
     * Returns the index of row.
     * 
     * @return the index of row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the index of column.
     * 
     * @return the index of column
     */
    public int getCol() {
        return col;
    }

    /**
     * Indicates whether this {@code RCPosition} object has specified row and
     * column index.
     * 
     * @param row
     *            the index of row
     * @param col
     *            the index of column
     * @return {@code true} if this object has specified row and column index;
     *         {@code false} otherwise
     */
    public boolean equals(int row, int col) {
        return this.row == row && this.col == col;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + col;
        result = prime * result + row;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RCPosition other = (RCPosition) obj;
        if (col != other.col)
            return false;
        if (row != other.row)
            return false;
        return true;
    }

    @Override
    public Object clone() {
        return new RCPosition(this.row, this.col);
    }

    @Override
    public String toString() {
        return row + ", " + col;
    }

    /**
     * Caches specified {@code RCPosition} object and makes sure that there
     * aren't number of cached objects bigger than
     * {@value RCPosition#MAX_CACHE_SIZE}.
     * 
     * @param pos
     *            {@code RCPosition} object that will be cached
     */
    private static void cache(RCPosition pos) {
        if (CACHE.size() >= MAX_CACHE_SIZE) {
            CACHE.remove(CACHE.keySet().iterator().next());
        }

        CACHE.put(cacheKey(pos), pos);
    }

    /**
     * Returns cache key/index for specified row and column.
     * 
     * @param row
     *            the index of row
     * @param col
     *            the index of column
     * @return cache key/index
     */
    private static int cacheKey(int row, int col) {
        return row * MAX_COLS + col;
    }

    /**
     * Returns cache key/index for specified {@code RCPosition} object.
     * 
     * @param pos
     *            {@code RCPosition} object key/index will be returned
     * @return cache key/index
     */
    private static int cacheKey(RCPosition pos) {
        return cacheKey(pos.row, pos.col);
    }

}