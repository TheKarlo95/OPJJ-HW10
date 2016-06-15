package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;
import java.util.List;

import javax.swing.JComponent;

/**
 * {@code BarChartComponent} class represents a bar chart.
 * <p>
 * This class takes data from {@link BarChart} and displays it on the screen.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see BarChart
 * @see JComponent
 */
public class BarChartComponent extends JComponent {

    /** Serial version UID. */
    private static final long serialVersionUID = 7707677132924104458L;

    /** Gap between the starting point of the axis and edge of the window. */
    private static final int GAP = 25;

    /** Font of the text. */
    private static final Font FONT = new Font(null, Font.PLAIN, 20);

    /** Bar chart data model. */
    private BarChart barChart;

    /** X axis label. */
    private AttributedString xAxisLabel;
    /** Y axis label. */
    private AttributedString yAxisLabel;

    /** Y axis length. */
    private int yAxisLength;
    /** Y axis starting point. */
    private Point yAxisStart;
    /** Y axis ending point. */
    private Point yAxisEnd;

    /** X axis length. */
    private int xAxisLength;
    /** X axis starting point. */
    private Point xAxisStart;
    /** X axis ending point. */
    private Point xAxisEnd;

    /**
     * Constructs a new {@code BarChartComponent} object with specified
     * {@link BarChart} data model.
     * 
     * @param barChart
     *            bar chart data model
     */
    public BarChartComponent(BarChart barChart) {
        if (barChart == null)
            throw new NullPointerException("Bar chart parameter cannot be a null reference!");

        this.barChart = barChart;

        this.xAxisLabel = new AttributedString(barChart.getXAxisLabel());
        this.yAxisLabel = new AttributedString(barChart.getYAxisLabel());

        this.xAxisLabel.addAttribute(TextAttribute.FONT, FONT);
        this.yAxisLabel.addAttribute(TextAttribute.FONT, FONT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        getAxisDimensions();
        drawYAxis(g);
        drawXAxis(g);

        for (XYValue value : barChart.getValues()) {
            if (value.getY() >= 0) {
                drawPositiveBar(g, value);
            } else {
                drawNegativeBar(g, value);
            }
        }
    }

    /**
     * Calculates the starting and ending points of x and y axis as well as
     * their length.
     */
    private void getAxisDimensions() {
        int width = getWidth();
        int height = getHeight();

        yAxisStart = new Point(3 * GAP, height - 3 * GAP);
        yAxisEnd = new Point(3 * GAP, (int) Math.floor(0.1 * height));

        xAxisStart = new Point(3 * GAP, height - 3 * GAP);
        xAxisEnd = new Point((int) Math.floor(0.9 * width), height - 3 * GAP);

        xAxisLength = xAxisEnd.x - xAxisStart.x;
        yAxisLength = yAxisStart.y - yAxisEnd.y;
    }

    /**
     * Draws the y axis.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     */
    private void drawYAxis(Graphics g) {
        g.drawLine(yAxisStart.x, yAxisStart.y, yAxisEnd.x, yAxisEnd.y);

        drawYAxisLabel(g);

        drawYAxisNumbers(g);
    }

    /**
     * Draws the y axis label.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     */
    private void drawYAxisLabel(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform defaultAt = g2d.getTransform();

        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);

        FontMetrics metrics = g.getFontMetrics(FONT);
        g2d.drawString(
                yAxisLabel.getIterator(),
                -getHeight() + (yAxisLength + metrics.charWidth(yAxisLabel.toString().length())) / 2,
                GAP);

        g2d.setTransform(defaultAt);
    }

    /**
     * Draws the y axis numbers and horizontal lines.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     */
    private void drawYAxisNumbers(Graphics g) {
        int yMin = barChart.getYMin();
        int yMax = barChart.getYMax();
        int yDiff = barChart.getYDiff();

        int valueDiff = -yAxisLength / ((yMax - yMin) / yDiff);
        int index = 0;
        for (int i = yMin; i <= yMax; i += yDiff) {
            AttributedString num = new AttributedString("" + i);
            num.addAttribute(TextAttribute.FONT, FONT);
            FontMetrics metrics = g.getFontMetrics(FONT);

            g.drawString(
                    num.getIterator(),
                    2 * GAP,
                    yAxisStart.y + index * valueDiff - valueDiff / 2 - metrics.getAscent() / 2);

            index++;

            if (i != yMax) {
                drawYLine(g, yAxisStart.x, yAxisStart.y + index * valueDiff);
            }
        }
    }

    /**
     * Draws one horizontal line.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     */
    private void drawYLine(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.drawLine(x, y, xAxisEnd.x, y);
        g.setColor(Color.BLACK);
    }

    /**
     * Draws the x axis.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     */
    private void drawXAxis(Graphics g) {
        g.drawLine(xAxisStart.x, xAxisStart.y, xAxisEnd.x, xAxisEnd.y);

        FontMetrics metrics = g.getFontMetrics(FONT);

        int x = xAxisStart.x + (xAxisLength - metrics.stringWidth(xAxisLabel.toString())) / 2;
        int y = getHeight() - GAP + metrics.getAscent();

        g.drawString(xAxisLabel.getIterator(), x, y);

        drawXAxisNumbers(g);
    }

    /**
     * Draws the x axis numbers and horizontal lines.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     */
    private void drawXAxisNumbers(Graphics g) {
        List<XYValue> values = barChart.getValues();

        int min = values.get(0).getX();
        int max = values.get(values.size() - 1).getX();

        int valueDiff = xAxisLength / (max - min + 1);

        for (int i = min; i <= max; i++) {
            AttributedString num = new AttributedString("" + i);
            num.addAttribute(TextAttribute.FONT, FONT);

            int index = i - min;

            g.drawString(num.getIterator(), xAxisStart.x + index * valueDiff + valueDiff / 2, xAxisStart.y + GAP);
            index++;

            drawXLine(g, xAxisStart.x + index * valueDiff, xAxisStart.y);
        }
    }

    /**
     * Draws one vertical line.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     */
    private void drawXLine(Graphics g, int x, int y) {
        g.setColor(Color.GRAY);
        g.drawLine(x, y, x, yAxisEnd.y);
        g.setColor(Color.BLACK);
    }

    /**
     * Draws bars if value has positive Y.
     * <p>
     * Using with negative Y is not recommended.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     * @param value
     *            x and y pair
     */
    private void drawPositiveBar(Graphics g, XYValue value) {
        g.setColor(Color.ORANGE);

        int yMin = barChart.getYMin();
        int yMax = barChart.getYMax();
        double yDiff = barChart.getYDiff();

        List<XYValue> values = barChart.getValues();

        int xMin = values.get(0).getX();
        int xMax = values.get(values.size() - 1).getX();

        int xValueDiff = xAxisLength / (xMax - xMin + 1);
        int yValueDiff = (int) (yAxisLength / ((yMax - yMin) / yDiff));

        int width = xValueDiff - 1;
        int height = (int) (yValueDiff * ((value.getY() - yMin) / yDiff) - 1);

        g.fillRect(
                xAxisStart.x + (value.getX() - xMin + 1) * xValueDiff - width,
                xAxisStart.y - height,
                width,
                (int) (height + yMin * yValueDiff / yDiff));

        g.setColor(Color.BLACK);
    }

    /**
     * Draws bars if value has negative Y.
     * <p>
     * Using with non-negative Y is not recommended.
     * 
     * @param g
     *            the {@code Graphics} object to paint
     * @param value
     *            x and y pair
     */
    private void drawNegativeBar(Graphics g, XYValue value) {
        g.setColor(Color.ORANGE);

        int yMin = barChart.getYMin();
        int yMax = barChart.getYMax();
        double yDiff = barChart.getYDiff();

        List<XYValue> values = barChart.getValues();

        int xMin = values.get(0).getX();
        int xMax = values.get(values.size() - 1).getX();

        int xValueDiff = xAxisLength / (xMax - xMin + 1);
        int yValueDiff = (int) (yAxisLength / ((yMax - yMin) / yDiff));

        int width = xValueDiff - 1;
        int height = (int) (yValueDiff * (Math.abs(value.getY()) / yDiff) - 1);

        g.fillRect(
                xAxisStart.x + (value.getX() - xMin + 1) * xValueDiff - width,
                (int) (xAxisStart.y - Math.abs(yMin) * yValueDiff / yDiff),
                width,
                (int) (height));

        g.setColor(Color.BLACK);
    }
}
