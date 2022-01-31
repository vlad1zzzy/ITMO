/**
 * @author : Kryukov Vladislav
 */
public class Solution implements AtomicCounter {
    private final Node root = new Node(0);
    private final ThreadLocal<Node> last = ThreadLocal.withInitial(() -> root);

    private static class Node {
        private final Consensus<Node> next;
        private final int value;

        private Node(int x) {
            this.next = new Consensus<>();
            this.value = x;
        }
    }

    @Override
    public int getAndAdd(int x) {
        Node node;
        int oldValue;
        do {
            oldValue = last.get().value;
            node = new Node(oldValue + x);
            last.set(last.get().next.decide(node));
        } while (node != last.get());
        return oldValue;
    }
}
