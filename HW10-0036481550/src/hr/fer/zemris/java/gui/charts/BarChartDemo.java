package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * {@code BarChartDemo} class demonstrates the {@link BarChart} and
 * {@link BarChartComponent} class and how they operate.
 * <p>
 * User needs to provide one argument to command-line which tells the program
 * where to find a file containing data about the bar chart.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see BarChart
 * @see BarChartComponent
 */
public class BarChartDemo extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = 6627621413520831345L;

    /** Bar chart component. */
    private BarChartComponent component;

    /** Path to file. */
    private Path path;

    /**
     * Constructs a new {@code BarChartDemo} object with specified
     * {@link BarChartComponent}.
     * 
     * @param comp
     *            bar chart component
     * @param path
     *            the path to file
     * 
     */
    public BarChartDemo(BarChartComponent comp, Path path) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("BarChart");
        setSize(500, 500);
        setLocationRelativeTo(null);
        this.component = comp;
        this.path = path;

        initGUI();
    }

    /**
     * Initializes a GUI.
     */
    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(component, BorderLayout.CENTER);

        JLabel label = new JLabel(path.toAbsolutePath().toString(), SwingConstants.CENTER);

        getContentPane().add(label, BorderLayout.NORTH);
    }

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("You need to pass a parameter indicating a location of a file");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);

        BarChartComponent comp = getChart(path);

        SwingUtilities.invokeLater(() -> {
            new BarChartDemo(comp, path).setVisible(true);
        });
    }

    /**
     * Returns the {@code BarChartComponent} object from the specified file
     * path.
     * 
     * @param path
     *            the path to file
     * @return the {@code BarChartComponent} object from the specified file path
     */
    private static BarChartComponent getChart(Path path) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException ignorable) {
        }

        String xLabel = lines.get(0);
        String yLabel = lines.get(1);

        List<XYValue> values = new ArrayList<>();
        for (String s : lines.get(2).split("\\s+")) {
            values.add(XYValue.valueOf(s));
        }

        int yMin = Integer.valueOf(lines.get(3));
        int yMax = Integer.valueOf(lines.get(4));
        int yDiff = Integer.valueOf(lines.get(5));

        BarChart chart = new BarChart(values, xLabel, yLabel, yMin, yMax, yDiff);

        return new BarChartComponent(chart);
    }

}
