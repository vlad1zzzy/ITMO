import { ok } from 'assert';

import { HashMap } from './HashMap';
import { Node } from './Node';

export class LruCache<K, V> {
    private head?: Node<K, V>;
    private tail?: Node<K, V>;
    private cache = new HashMap<K, Node<K, V>>();

    constructor(private _capacity: number = Infinity) {
        ok(_capacity >= 0, `Expected non negative capacity, but found "${_capacity}"`);
    }

    get(key: K): V | undefined {
        const node = this.cache.get(key);
        if (typeof node === 'undefined') {
            return;
        }

        this.update(node);

        ok(this.size <= this.capacity, `HashMap overflow, (${this.size} > ${this.capacity})`);
        ok(this.head, `Head is not defined`);
        ok(this.head.key === key, `Head key=${this.head.key} and getter key=${key} does not equals`);

        return node.value;
    }

    put(key: K, value: V): void {
        const oldSize = this.size;
        const oldNode = this.cache.get(key);
        const newNode = new Node(key, value);

        this.update(newNode);

        if (typeof oldNode === 'undefined' && this.size === this.capacity) {
            ok(this.tail, `Tail is not defined`);
            this.cache.remove(this.tail.key);
        } else {
            ok(this.size === oldSize, `Size has changed (${oldSize} -> ${this.size})`);
        }

        this.cache.put(key, newNode);

        ok(this.size <= this.capacity, `HashMap overflow, (${this.size} > ${this.capacity})`);
        ok(this.size >= oldSize, `Size has decreased (${oldSize} -> ${this.size})`);
        ok(this.head, `Head is not defined`);
        ok(this.head.key === key, `Head key and key=${key} aren't equals, (${this.head.key})`);
        ok(this.head.value === value, `The value does not saved to head, (${this.head.value})`);
    }

    get capacity(): number {
        return this._capacity;
    }

    get size(): number {
        return this.cache.size;
    }

    private update(node: Node<K, V>): void {
        node.prev = this.head;
        delete node.next;

        this.head = node;

        this.tail ??= node;
    }
}
