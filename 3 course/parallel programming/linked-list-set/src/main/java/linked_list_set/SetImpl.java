package linked_list_set;

import kotlinx.atomicfu.AtomicRef;

public class SetImpl implements Set {
    private static class Node {
        final AtomicRef<Node> next;
        final int x;

        Node(final int x, final Node next) {
            this.next = new AtomicRef<>(next);
            this.x = x;
        }

        Node nextNode() {
            Node curNext = next.getValue();
            if (isRemoved(curNext)) {
                return ((Removed) curNext).node;
            }
            return curNext;
        }

        static boolean  isRemoved(final Node node) {
            return node instanceof Removed;
        }
    }

    static class Removed extends Node {
        final Node node;

        Removed(final Node node) {
            super(0, node);
            this.node = node;
        }
    }

    private static class Window {
        Node left, right;

        public void set(final Node left, final Node right) {
            this.left = left;
            this.right = right;
        }
    }

    private final Node head = new Node(Integer.MIN_VALUE, new Node(Integer.MAX_VALUE, null));

    /**
     * Returns the {@link Window}, where cur.x < x <= next.x
     */
    private Window findWindow(int x) {
        final Window w = new Window();
        Node afterWindow;
        while (true) {
            w.set(head, head.nextNode());
            goUntil(x, w);
            afterWindow = w.right.next.getValue();
            if (Node.isRemoved(afterWindow)) {
                changeNext(w, ((Removed) afterWindow).node);
            } else {
                return w;
            }
        }
    }

    private void goUntil(int x, final Window w) {
        Node afterWindow;
        while (w.right.x < x) {
            afterWindow = w.right.next.getValue();
            if (Node.isRemoved(afterWindow) && changeNext(w, ((Removed) afterWindow).node)) {
                w.set(w.left, ((Removed) afterWindow).node);
            } else {
                w.set(w.right, w.right.nextNode());
            }
        }
    }

    @Override
    public boolean add(int x) {
        while (true) {
            final Window w = findWindow(x);
            if (w.right.x == x) {
                return false;
            }
            if (changeNext(w, new Node(x, w.right))) {
                return true;
            }
        }
    }

    @Override
    public boolean remove(int x) {
        while (true) {
            final Window w = findWindow(x);
            if (w.right.x != x) {
                return false;
            }
            final Node afterWindow = w.right.nextNode();
            if (w.right.next.compareAndSet(afterWindow, new Removed(afterWindow))) {
                changeNext(w, afterWindow);
                return true;
            }
        }
    }

    @Override
    public boolean contains(int x) {
        return findWindow(x).right.x == x;
    }

    private boolean changeNext(final Window w, final Node next) {
        return w.left.next.compareAndSet(w.right, next);
    }
}