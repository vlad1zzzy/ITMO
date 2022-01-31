import java.util.concurrent.atomic.*;

public class Solution implements Lock<Solution.Node> {
    private final Environment env;
    private final AtomicReference<Node> tail = new AtomicReference<>(null);

    public Solution(Environment env) {
        this.env = env;
    }

    @Override
    public Node lock() {
        final Node node = new Node();
        final Node prev = tail.getAndSet(node);
        node.locked.set(true);

        if (prev != null) {
            prev.next.set(node);
            wait(node, Type.LOCK);
        }

        return node;
    }

    @Override
    public void unlock(final Node node) {
        if (node.next.get() == null) {
            if (tail.compareAndSet(node, null)) {
                return;
            }
            wait(node, Type.UNLOCK);
        }

        node.next.get().locked.set(false);
        env.unpark(node.next.get().thread);
    }

    private void wait(final Node node, final Type type) {
        if (type == Type.LOCK) {
            while (node.locked.get()) {
                env.park();
            }
        }

        if (type == Type.UNLOCK) {
            //noinspection StatementWithEmptyBody
            while (node.next.get() == null) ;
        }
    }

    static class Node {
        final Thread thread = Thread.currentThread();
        final AtomicReference<Boolean> locked = new AtomicReference<>(false);
        final AtomicReference<Node> next = new AtomicReference<>();
    }

    private enum Type {
        LOCK, UNLOCK
    }
}
