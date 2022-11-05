import hash from 'object-hash';

const HASH_START = 666;
const HASH_SHIFT = 13;
const INITIAL_CAPACITY = 100;

type StorageItem<K, V> = { key: K; value: V };
type Storage<K, V> = StorageItem<K, V>[];

export class HashMap<K, V> {
    private readonly storage!: Storage<K, V>[];
    private _size!: number;

    constructor(capacity: number = INITIAL_CAPACITY) {
        this.storage = new Array<Storage<K, V>>(capacity);
        this._size = 0;
    }

    get(key: K): V | undefined {
        const index = this.position(key);
        const item = this.storage[index]?.find(item => item.key === key);

        return item?.value;
    }

    has(key: K): boolean {
        return typeof this.get(key) !== 'undefined';
    }

    remove(key: K): void {
        const index = this.position(key);
        const items = this.storage[index];
        if (!items) {
            return;
        }

        const itemIndex = items.findIndex(item => item.key === key);
        if (itemIndex === -1) {
            return;
        }

        items.splice(itemIndex, 1);
        this._size--;
    }

    put(key: K, value: V): void {
        const index = this.position(key);

        this.storage[index] ||= [];

        this.has(key) && this.remove(key);

        this.storage[index]!.push({ key, value });
        this._size++;
    }

    get size(): number {
        return this._size;
    }

    private position(key: K): number {
        const getStringHash = (str: string) => {
            let sum = HASH_START;

            for (let i = 0; i < str.length; i++) {
                sum = (sum << HASH_SHIFT) + sum + str.charCodeAt(i);
            }

            return sum;
        };

        const getNormalHash = (key: K) => {
            switch (typeof key) {
                case 'number':
                    return key;
                case 'boolean':
                    return Number(key);
                case 'string':
                    return getStringHash(key);
                case 'object':
                    return getStringHash(hash(key));
                default:
                    return 0;
            }
        };

        return getNormalHash(key) % this.storage.length;
    }
}
