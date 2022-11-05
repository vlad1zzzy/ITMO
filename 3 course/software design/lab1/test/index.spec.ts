import { LruCache } from '../src';

describe('lru', () => {
    const capacity = 2;
    const num = 777;
    const str = 'test';
    const obj = {
        str: num
    };

    it('base', () => {
        const cache = new LruCache(capacity);

        expect(cache.capacity).toBe(capacity);
        expect(cache.size).toBe(0);

        cache.put(num, 1);
        expect(cache.size).toBe(1);

        cache.put(str, 2);
        expect(cache.size).toBe(2);

        cache.put(obj, 3);
        expect(cache.size).toBe(2);

        expect(cache.capacity).toBe(capacity);
        expect(cache.size).toBe(capacity);
    });

    it('overflow', () => {
        const cache = new LruCache(capacity);

        cache.put(num, 1);
        cache.put(str, 2);
        cache.put(obj, 3);

        expect(cache.get(num)).toBeUndefined();
        expect(cache.get(str)).toBe(2);
        expect(cache.get(obj)).toBe(3);
    });

    it('same key', () => {
        const cache = new LruCache(capacity);

        cache.put(num, 1);
        expect(cache.get(num)).toBe(1);

        cache.put(str, 2);
        expect(cache.get(str)).toBe(2);

        cache.put(num, 2);
        expect(cache.get(num)).toBe(2);
        expect(cache.get(str)).toBe(2);

        cache.put(num, 3);
        expect(cache.get(num)).toBe(3);
        expect(cache.get(str)).toBe(2);

        cache.put(str, 3);
        expect(cache.get(num)).toBe(3);
        expect(cache.get(str)).toBe(3);

        cache.put(obj, 4);
        expect(cache.get(num)).toBeUndefined();
        expect(cache.get(str)).toBe(3);
        expect(cache.get(obj)).toBe(4);
    });
});