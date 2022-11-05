export class Node<K, V> {
    next?: Node<K, V>;
    prev?: Node<K, V>;

    constructor(public key: K, public value: V) {}
}
