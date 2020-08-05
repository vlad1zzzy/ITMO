package queue;


public abstract class  AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);


    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();


    public Object dequeue() {
        assert size > 0;
        Object value = element();
        dequeueImpl();
        size--;
        return value;
    }

    protected abstract void dequeueImpl();


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        assert size > 0;
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public String toStr() {
        if (size == 0)
            return "[]";
        StringBuilder str = new StringBuilder("[");
        save();
        str.append(element());
        for (int i = 1; i < size; i++) {
            dequeueImpl();
            str.append(", ").append(element());
        }
        saved();
        str.append("]");
        return str.toString();
    }

    protected abstract void save();

    protected abstract void saved();

    public Object[] toArray() {
//        if (size == 0)
//            return new Object[]{};
//        save();
        Object[] arr = new Object[size];
        for (int i = 0; i < size; i++) {
            arr[i] = dequeue();
            enqueue(arr[i]);
        }
//        saved();
        return arr;
    }
}