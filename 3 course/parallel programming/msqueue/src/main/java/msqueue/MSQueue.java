package msqueue;

import kotlinx.atomicfu.AtomicRef;

public class MSQueue implements Queue {
    private final AtomicRef<Node> head, tail;

    public MSQueue() {
        final Node dummy = new Node(0);
        head = new AtomicRef<>(dummy);
        tail = new AtomicRef<>(dummy);
    }

    @Override
    public void enqueue(int x) {
        final Node node = new Node(x);
        Node curTail;
        while (true) {
            curTail = tail.getValue();
            final Node next = curTail.next.getValue();
            if (curTail == tail.getValue()) {
                if (next == null) {
                    if (curTail.next.compareAndSet(null, node)) {
                        break;
                    }
                } else {
                    tail.compareAndSet(curTail, next);
                }
            }
        }
        tail.compareAndSet(curTail, node);
    }

    @Override
    public int dequeue() {
        while (true) {
            final Node curHead = head.getValue(), curTail = tail.getValue(), next = curHead.next.getValue();
            if (curHead == head.getValue()) {
                if (curHead == curTail) {
                    if (next == null) {
                        return Integer.MIN_VALUE;
                    }
                    tail.compareAndSet(curTail, next);
                } else {
                    if (head.compareAndSet(curHead, next)) {
                        return next.x;
                    }
                }
            }
        }
    }

    @Override
    public int peek() {
        final Node next = head.getValue().next.getValue();
        return next == null ? Integer.MIN_VALUE : next.x;
    }

    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(int x) {
            this.next = new AtomicRef<>(null);
            this.x = x;
        }
    }
}