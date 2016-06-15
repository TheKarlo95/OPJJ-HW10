package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * {@code PrimDemo} class demonstrates the {@link PrimListModel} class and how
 * it operates.
 * 
 * @author Karlo Vrbić
 *
 */
public class PrimDemo extends JFrame {

    /** Serial version UID. */
    private static final long serialVersionUID = -1305147476023758530L;

    /** Window width. */
    private static final int WINDOW_WIDTH = 500;
    /** Window height. */
    private static final int WINDOW_HEIGHT = 500;

    /** Prime list model. */
    private PrimListModel model;

    /**
     * Constructs a new {@code PrimDemo} object.
     */
    public PrimDemo() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PrimDemo");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        model = new PrimListModel();

        initGUI();
    }

    /**
     * Initializes a GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(x -> model.next());
        cp.add(nextButton, BorderLayout.SOUTH);

        JPanel panel = new JPanel(new GridLayout(1, 2));

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        list1.setVisible(true);
        list2.setVisible(true);

        list1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        list2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        list1.setFont(new Font(null, Font.PLAIN, 26));
        list2.setFont(new Font(null, Font.PLAIN, 26));

        panel.add(new JScrollPane(list1));
        panel.add(new JScrollPane(list2));

        cp.add(panel);
    }

    /**
     * Starting point of a program.
     * 
     * @param args
     *            Command-line argument
     */
    public static void main(String[] args) {
        PrimDemo primDemo = new PrimDemo();

        SwingUtilities.invokeLater(() -> primDemo.setVisible(true));
    }
}
