package org.example;

/**
 * A custom stuck with O(1) contains check.
 * Can't handle duplicates. No over or underflow checks.
 * If the item is removed it no longer counts as contained within the stack.
 * @author Artem Tarnavskyi
 * @version 1.0
 */
public class CustomStack {
    private int pointer = 0;
    private int[] values;
    private int[] contains;
    public CustomStack(int capacity) {
        values = new int[capacity];
        contains = new int[capacity / 32 + 1];
    }
    public void push(int value) {
        values[pointer] = value;
        contains[value / 32] = contains[value / 32] | (1 << (value % 32));
        pointer++;
    }
    public int pop() {
        int value = values[pointer--];
        contains[value / 32] = (contains[value / 32]) & ~(1 << (value % 32));
        return value;
    }
    public boolean contains(int value) {
        return ((contains[value / 32] >> (value % 32)) & 1) == 1;
    }
}
