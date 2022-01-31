package stack;

import kotlinx.atomicfu.AtomicArray;
import kotlinx.atomicfu.AtomicRef;

public class StackImpl implements Stack {
    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x, Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }
    }

    private static final int CAPACITY = 32, ELIMINATION_TIMES = 64, MINIMUM_CHECK_TIMES = 4;

    private static int getPosition() {
        return Math.max(0, (int) (Math.random() * CAPACITY - MINIMUM_CHECK_TIMES));
    }

    private final AtomicRef<Node> head = new AtomicRef<>(null);
    private final AtomicArray<Integer> eliminationArray = new AtomicArray<>(CAPACITY);

    private boolean isEliminated(int position, int x) {
        for (int i = 0; i < ELIMINATION_TIMES; i++) {
            final Integer currentValue = eliminationArray.get(position).getValue();
            if (currentValue == null || currentValue != x) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfCanEliminate(int x) {
        final Integer value = x;
        int position = getPosition();
        for (; position < CAPACITY; position++) {
            if (eliminationArray.get(position).compareAndSet(null, value)) {
                return isEliminated(position, x) || !eliminationArray.get(position).compareAndSet(value, null);
            }
        }
        return false;
    }

    @Override
    public void push(int x) {
        if (checkIfCanEliminate(x)) {
            return;
        }
        while (true) {
            final Node prevHead = head.getValue(), nextHead = new Node(x, prevHead);
            if (head.compareAndSet(prevHead, nextHead)) {
                return;
            }
        }
    }

    private Integer eliminate() {
        int position = getPosition();
        for (; position < CAPACITY; position++) {
            final Integer value = eliminationArray.get(position).getValue();
            if (value != null && eliminationArray.get(position).compareAndSet(value, null)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public int pop() {
        final Integer eliminated = eliminate();
        if (eliminated != null) {
            return eliminated;
        }
        while (true) {
            final Node prevHead = head.getValue();
            if (prevHead == null) {
                return Integer.MIN_VALUE;
            }
            final Node nextHead = prevHead.next.getValue();
            if (head.compareAndSet(prevHead, nextHead)) {
                return prevHead.x;
            }
        }
    }
}
