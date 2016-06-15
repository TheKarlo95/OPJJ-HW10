package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * {@code PrimListModel} class is a implementation of a {@link ListModel}
 * interface and provides prime numbers to JList.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see ListModel
 */
public class PrimListModel implements ListModel<Integer> {

    /** List of all listeners. */
    private List<ListDataListener> listeners;

    /** List of all previous prime numbers. */
    private List<Integer> cache;

    /** Current prime number. */
    private int current;

    /**
     * Constructs a new {@code PrimListModel} object with starting prime 1.
     */
    public PrimListModel() {
        cache = new ArrayList<>();
        listeners = new ArrayList<>();

        current = 1;
        cache.add(1);
    }

    /**
     * Returns the next prime number and notifies the listeners.
     * 
     * @return the next prime number
     */
    public int next() {
        int i = current;

        while (true) {
            i++;

            if (isPrime(i)) {
                current = i;
                cache.add(i);

                int index = cache.size() - 1;

                listeners.forEach(
                        x -> x.intervalAdded(
                                new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index)));

                return i;
            }
        }
    }

    /**
     * Checks if specified number is a prime number.
     * 
     * @param number
     *            the number which this method will check
     * @return {@code true} if specified number is a prime number; {@code false}
     *         otherwise
     */
    private static boolean isPrime(int number) {
        if (number % 2 == 0 && number != 2)
            return false;

        for (int i = 3; i * i <= number; i += 2)
            if (number % i == 0)
                return false;

        return true;
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return cache.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        if (l == null)
            throw new NullPointerException("You cannot add a null reference as ListDataListener!");

        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        if (l == null)
            throw new NullPointerException("You cannot remove a null reference as ListDataListener!");

        listeners.remove(l);
    }
}
