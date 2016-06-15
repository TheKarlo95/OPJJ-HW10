package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * {@code BarChart} class represents a data model containg all the information
 * for the {@link BarChartComponent} object.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see BarChartComponent
 */
public class BarChart {

    /** X and Y pairs. */
    private List<XYValue> values;
    /** Label of the x axis. */
    private final String xAxisLabel;
    /** Label of the y axis. */
    private final String yAxisLabel;
    /** Minimum y value. */
    private final int yMin;
    /** Maximum y value. */
    private final int yMax;
    /** Difference between two adjacent y values on y axis. */
    private final int yDiff;

    /**
     * Constructs a new {@code BarChart} object with specified parameters.
     * 
     * @param values
     *            x and y pairs
     * @param xAxisLabel
     *            label of the x axis
     * @param yAxisLabel
     *            label of the y axis
     * @param yMin
     *            minimum y value
     * @param yMax
     *            maximum y value
     * @param yDiff
     *            difference between two adjacent y values on y axis
     */
    public BarChart(List<XYValue> values, String xAxisLabel, String yAxisLabel, int yMin, int yMax, int yDiff) {
        this.values = values;
        this.values.sort((v1, v2) -> Integer.compare(v1.getX(), v2.getX()));
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.yMin = yMin;
        this.yMax = yMax;
        this.yDiff = yDiff;
    }

    /**
     * Returns the values.
     * 
     * @return the values
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Returns the label of the x axis.
     * 
     * @return the label of the x axis
     */
    public String getXAxisLabel() {
        return xAxisLabel;
    }

    /**
     * Returns the label of the y axis.
     * 
     * @return the label of the y axis
     */
    public String getYAxisLabel() {
        return yAxisLabel;
    }

    /**
     * Returns the minimum y value.
     * 
     * @return the minimum y value
     */
    public int getYMin() {
        return yMin;
    }

    /**
     * Returns the maximum y value.
     * 
     * @return the maximum y value
     */
    public int getYMax() {
        return yMax;
    }

    /**
     * Returns the difference between two adjacent y values on y axis.
     * 
     * @return the difference between two adjacent y values on y axis
     */
    public int getYDiff() {
        return yDiff;
    }

}
