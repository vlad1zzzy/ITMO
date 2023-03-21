function id_to_node(node, x_first) {
    if (node === 0) {
        return node;
    }

    let res = node;

    while (x_first > 1) {
        x_first >>= 1;
        res <<= 1;
    }

    return res;
}

function node_to_id(node, x_first) {
    if (node === 0) {
        return node;
    }

    let res = node;

    while (x_first > 1) {
        x_first >>= 1;
        res >>= 1;
    }

    return res;
}

describe("qwe", () => {
    it("0 - 1", () => {
        expect(id_to_node(0, 1, 2)).toBe(0);
        expect(id_to_node(1, 1, 2)).toBe(1);
        expect(id_to_node(2, 1, 2)).toBe(2);
        expect(id_to_node(3, 1, 2)).toBe(3);

        expect(node_to_id(0, 1, 2)).toBe(0);
        expect(node_to_id(1, 1, 2)).toBe(1);
        expect(node_to_id(2, 1, 2)).toBe(2);
        expect(node_to_id(3, 1, 2)).toBe(3);

        expect(node_to_id(4, 1, 2)).toBe(2);
        expect(node_to_id(6, 1, 2)).toBe(3);
    });

    it("0 - 2", () => {
        expect(id_to_node(0, 1, 4)).toBe(0);
        expect(id_to_node(1, 1, 4)).toBe(1);
        expect(id_to_node(2, 1, 4)).toBe(2);
        expect(id_to_node(3, 1, 4)).toBe(3);
        expect(id_to_node(4, 1, 4)).toBe(4);
        expect(id_to_node(5, 1, 4)).toBe(5);
        expect(id_to_node(6, 1, 4)).toBe(6);
        expect(id_to_node(7, 1, 4)).toBe(7);

        expect(node_to_id(0, 1, 4)).toBe(0);
        expect(node_to_id(1, 1, 4)).toBe(1);
        expect(node_to_id(2, 1, 4)).toBe(2);
        expect(node_to_id(3, 1, 4)).toBe(3);
        expect(node_to_id(4, 1, 4)).toBe(4);
        expect(node_to_id(5, 1, 4)).toBe(5);
        expect(node_to_id(6, 1, 4)).toBe(6);
        expect(node_to_id(7, 1, 4)).toBe(7);
    });

    it("1 - 2", () => {
        expect(id_to_node(0, 2, 4)).toBe(0);
        expect(id_to_node(1, 2, 4)).toBe(2);
        expect(id_to_node(2, 2, 4)).toBe(4);
        expect(id_to_node(3, 2, 4)).toBe(6);

        expect(node_to_id(0, 2, 4)).toBe(0);
        expect(node_to_id(2, 2, 4)).toBe(1);
        expect(node_to_id(4, 2, 4)).toBe(2);
        expect(node_to_id(6, 2, 4)).toBe(3);
    });

    it("1 - 3", () => {
        expect(id_to_node(0, 2, 8)).toBe(0);
        expect(id_to_node(1, 2, 8)).toBe(2);
        expect(id_to_node(2, 2, 8)).toBe(4);
        expect(id_to_node(3, 2, 8)).toBe(6);
        expect(id_to_node(4, 2, 8)).toBe(8);
        expect(id_to_node(5, 2, 8)).toBe(10);
        expect(id_to_node(6, 2, 8)).toBe(12);
        expect(id_to_node(7, 2, 8)).toBe(14);

        expect(node_to_id(0, 2, 8)).toBe(0);
        expect(node_to_id(2, 2, 8)).toBe(1);
        expect(node_to_id(4, 2, 8)).toBe(2);
        expect(node_to_id(6, 2, 8)).toBe(3);
        expect(node_to_id(8, 2, 8)).toBe(4);
        expect(node_to_id(10, 2, 8)).toBe(5);
        expect(node_to_id(12, 2, 8)).toBe(6);
        expect(node_to_id(14, 2, 8)).toBe(7);
    });
})

