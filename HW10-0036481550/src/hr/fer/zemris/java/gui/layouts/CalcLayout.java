package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * {@code CalcLayout} class is a layout manager that lays out a container's
 * components in a rectangular grid similar to {@code GridLayout} with only
 * difference is that first element(for display) is 5 times bigger than others.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see LayoutManager2
 */
public class CalcLayout implements LayoutManager2 {

    /** Number of rows. */
    static final int NROWS = 5;
    /** Number of columns. */
    static final int NCOLS = 7;

    /** Number of components. */
    private static final int NCOMPONENTS = 31;

    /** Horizontal gap (in pixels) which specifies the space between columns. */
    private final int hgap;
    /** Vertical gap (in pixels) which specifies the space between rows. */
    private final int vgap;

    /** Maximum preferred width of all components. */
    private int maxPrefWidth;
    /** Maximum preferred height of all components. */
    private int maxPrefHeight;

    /** Map containing all components */
    private Map<RCPosition, Component> components;

    /**
     * Constructs a new {@code CalcLayout} with no horizontal and vertical gap.
     */
    public CalcLayout() {
        this(0, 0);
    }

    /**
     * Constructs a new {@code CalcLayout} with the same horizontal and vertical
     * gap.
     * 
     * @param gap
     *            horizontal and vertical gap
     */
    public CalcLayout(int gap) {
        this(gap, gap);
    }

    /**
     * Constructs a new {@code CalcLayout} with the specified horizontal and
     * vertical gap.
     * 
     * @param hgap
     *            horizontal gap
     * @param vgap
     *            vertical gap
     */
    public CalcLayout(int hgap, int vgap) {
        if (hgap < 0)
            throw new IllegalArgumentException("Horizontal gap cannot be a negative number!");

        if (vgap < 0)
            throw new IllegalArgumentException("Vertical gap cannot be a negative number!");

        this.hgap = hgap;
        this.vgap = vgap;
        this.components = new HashMap<>();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        if (comp == null) {
            throw new IllegalArgumentException("Component can't be null.");
        }

        components.values().remove(comp);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        double ratioHeight = (double) parent.getHeight() / (double) preferredLayoutSize(parent).getHeight();
        double ratioWidth = (double) parent.getWidth() / (double) preferredLayoutSize(parent).getWidth();

        int h = (int) (this.maxPrefHeight * ratioHeight);
        int w = (int) (this.maxPrefWidth * ratioWidth);

        for (Entry<RCPosition, Component> entry : components.entrySet()) {
            Component comp = entry.getValue();
            RCPosition position = entry.getKey();

            if (position.equals(1, 1)) {
                comp.setBounds(insets.left, insets.top, 5 * w + 4 * hgap, h);
            } else {
                comp.setBounds(
                        (position.getCol() - 1) * (w + hgap) + insets.left,
                        (position.getRow() - 1) * (h + vgap) + insets.top,
                        w,
                        h);
            }
        }
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (comp == null)
            throw new NullPointerException("Component cannot be null reference!");

        if (constraints == null)
            throw new NullPointerException("Constraint cannot be null reference!");

        if (components.size() == NCOMPONENTS)
            throw new IllegalArgumentException("Layout elready contains maximum number of components. ");

        RCPosition pos = null;

        if (constraints instanceof RCPosition) {
            pos = (RCPosition) constraints;
        } else if (constraints instanceof String) {
            pos = RCPosition.valueOf((String) constraints);
        } else {
            throw new IllegalArgumentException("Constraint must be instance of RCPosition!");
        }

        if (components.containsKey(pos))
            throw new IllegalArgumentException("You cannot add two or more components on the same position!");

        maxPrefHeight = Math.max(maxPrefHeight, comp.getPreferredSize().height);
        maxPrefWidth = Math.max(maxPrefWidth, comp.getPreferredSize().width);

        components.put(pos, comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getLayoutSize(parent, x -> x.getPreferredSize());
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getLayoutSize(parent, x -> x.getMinimumSize());
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return getLayoutSize(target, x -> x.getMaximumSize());
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    /**
     * Calculates the preferred, minimum or maximum size dimensions for the
     * specified container, given the components it contains.
     * 
     * @param parent
     *            the container to be laid out
     * @param func
     *            function indicating whether this method should calculate
     *            preferred, minimum or maximum size
     * @return preferred, minimum or maximum size
     */
    private Dimension getLayoutSize(Container parent, Function<Component, Dimension> func) {
        int w = 0;
        int h = 0;

        for (Entry<RCPosition, Component> entry : components.entrySet()) {
            Component comp = entry.getValue();

            h = Math.max(h, func.apply(comp).height);

            if (entry.getKey().equals(1, 1))
                continue;

            w = Math.max(w, func.apply(comp).width);
        }

        Insets ins = parent.getInsets();

        w = w * NCOLS + (NROWS - 1) * hgap + ins.left + ins.right;
        h = h * NROWS + (NROWS - 1) * vgap + ins.bottom + ins.top;

        return new Dimension(w, h);
    }

}
