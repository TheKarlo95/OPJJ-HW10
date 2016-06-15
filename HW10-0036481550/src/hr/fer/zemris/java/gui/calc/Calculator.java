package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.backend.CalculatorBE;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * {@code Calculator} class represents a simple calculator whcih user can use to
 * do simple math.
 * <p>
 * This class is also a start of the program and contains only information about
 * the looks of a GUI. Command-line arguments aren't used.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see CalculatorBE
 * @see JFrame
 */
public class Calculator extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = -2205159739020563099L;

    /** Window width. */
    private static final int WINDOW_WIDTH = 700;
    /** Window height. */
    private static final int WINDOW_HEIGHT = 300;

    /** Object that does all operations of a calculator. */
    private CalculatorBE calc;

    /** Label used as a display. */
    private JLabel display;

    /**
     * Constructs a new {@code Calculator} object.
     */
    public Calculator() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Calculator");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        initGUI();
    }

    /**
     * Initializes a GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(10));

        calc = new CalculatorBE();
        display = new JLabel(calc.getCurrentValue());

        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.ORANGE);
        display.setOpaque(true);
        display.setFont(new Font("SansSerif", Font.PLAIN, 26));
        cp.add(display, RCPosition.valueOf(1, 1));

        addDigitButtons();
        addUnaryOperatorButtons();
        addBinaryOperatorButtons();
        addOtherButtons();
    }

    /**
     * Updates the display with current information.
     */
    private void updateGUI() {
        display.setText(calc.getCurrentValue());
    }

    /**
     * Adds clear, reset, push, pop, invert, equals, dot and button for negating
     * numbers to GUI.
     */
    private void addOtherButtons() {
        Container cp = getContentPane();

        JButton clr = new JButton("clr");
        clr.addActionListener(e -> {
            calc.clearButton();
            updateGUI();
        });
        cp.add(clr, RCPosition.valueOf(1, 7));

        JButton res = new JButton("res");
        res.addActionListener(e -> {
            calc.resetButton();
            updateGUI();
        });
        cp.add(res, RCPosition.valueOf(2, 7));

        JButton push = new JButton("push");
        push.addActionListener(e -> {
            calc.pushButton();
            updateGUI();
        });
        cp.add(push, RCPosition.valueOf(3, 7));

        JButton pop = new JButton("pop");
        pop.addActionListener(e -> {
            calc.popButton();
            updateGUI();
        });
        cp.add(pop, RCPosition.valueOf(4, 7));

        JCheckBox invert = new JCheckBox("inv");
        invert.addActionListener(e -> {
            calc.invertButton();
            updateGUI();
        });
        cp.add(invert, RCPosition.valueOf(5, 7));

        JButton equals = new JButton("=");
        equals.addActionListener(e -> {
            calc.equalButton();
            updateGUI();
        });
        cp.add(equals, RCPosition.valueOf(1, 6));

        JButton negativeToogle = new JButton("+/-");
        negativeToogle.addActionListener(e -> {
            calc.negativeToggleButton();
            updateGUI();
        });
        cp.add(negativeToogle, RCPosition.valueOf(5, 4));

        JButton dot = new JButton(".");
        dot.addActionListener(e -> {
            calc.dotButton();
            updateGUI();
        });
        cp.add(dot, RCPosition.valueOf(5, 5));
    }

    /**
     * Adds buttons representing digits.
     */
    private void addDigitButtons() {
        ActionListener digitListener = e -> {
            calc.digitButton(((JButton) e.getSource()).getText());
            updateGUI();
        };

        Container cp = getContentPane();

        JButton[] buttons = new JButton[10];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("" + i);
            buttons[i].addActionListener(digitListener);
        }

        cp.add(buttons[0], RCPosition.valueOf(5, 3));
        cp.add(buttons[1], RCPosition.valueOf(4, 3));
        cp.add(buttons[2], RCPosition.valueOf(4, 4));
        cp.add(buttons[3], RCPosition.valueOf(4, 5));
        cp.add(buttons[4], RCPosition.valueOf(3, 3));
        cp.add(buttons[5], RCPosition.valueOf(3, 4));
        cp.add(buttons[6], RCPosition.valueOf(3, 5));
        cp.add(buttons[7], RCPosition.valueOf(2, 3));
        cp.add(buttons[8], RCPosition.valueOf(2, 4));
        cp.add(buttons[9], RCPosition.valueOf(2, 5));
    }

    /**
     * Adds buttons for unary operators.
     */
    private void addUnaryOperatorButtons() {
        ActionListener unaryOperatorListener = e -> {
            calc.unaryOperationButton(((JButton) e.getSource()).getText());
            updateGUI();
        };

        JButton[] buttons = new JButton[7];

        buttons[0] = new JButton("1 / x");
        buttons[1] = new JButton("log");
        buttons[2] = new JButton("ln");
        buttons[3] = new JButton("sin");
        buttons[4] = new JButton("cos");
        buttons[5] = new JButton("tan");
        buttons[6] = new JButton("ctg");

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(unaryOperatorListener);
        }

        Container cp = getContentPane();

        cp.add(buttons[0], RCPosition.valueOf(2, 1));
        cp.add(buttons[1], RCPosition.valueOf(3, 1));
        cp.add(buttons[2], RCPosition.valueOf(4, 1));
        cp.add(buttons[3], RCPosition.valueOf(2, 2));
        cp.add(buttons[4], RCPosition.valueOf(3, 2));
        cp.add(buttons[5], RCPosition.valueOf(4, 2));
        cp.add(buttons[6], RCPosition.valueOf(5, 2));
    }

    /**
     * Adds buttons for binaryoperators.
     */
    private void addBinaryOperatorButtons() {
        ActionListener binaryOperatorListener = e -> {
            calc.binaryOperationButton(((JButton) e.getSource()).getText());
            updateGUI();
        };

        JButton[] buttons = new JButton[5];

        buttons[0] = new JButton("+");
        buttons[1] = new JButton("-");
        buttons[2] = new JButton("×");
        buttons[3] = new JButton("÷");
        buttons[4] = new JButton("<html>x<sup>n</sup></html>");

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(binaryOperatorListener);
        }

        Container cp = getContentPane();

        cp.add(buttons[0], RCPosition.valueOf(5, 6));
        cp.add(buttons[1], RCPosition.valueOf(4, 6));
        cp.add(buttons[2], RCPosition.valueOf(3, 6));
        cp.add(buttons[3], RCPosition.valueOf(2, 6));
        cp.add(buttons[4], RCPosition.valueOf(5, 1));
    }

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        SwingUtilities.invokeLater(() -> calc.setVisible(true));
    }
}
