import {stats, func} from "./main.js";

export {fibonacci}

const fibs = [
    1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597,
    2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418,
    317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465,
    14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296,
    433494437, 701408733, 1134903170, 1836311903
];

function fibonacci(a, b, eps) {
    let anchor = (b - a) / eps;
    let n = -1;
    for (let i = 2; i < fibs.length; i++) {
        if (fibs[i] > anchor) {
            n = i;
            break;
        }
    }
    if (n === -1) {
        alert("Error")
        return -1;
    }
    let x1 = a + (fibs[n - 2] / fibs[n]) * (b - a);
    let x2 = a + (fibs[n - 1] / fibs[n]) * (b - a);
    for (let k = 1; k < n - 1; k++) {
        let f1 = func(x1);
        let f2 = func(x2);
        stats.push([k, ...[a, b, b - a, x1, x2, f1, f2].map(el => el.toFixed(10))])
        if (f1 > f2) {
            a = x1;
            x1 = x2;
            x2 = a + (fibs[n - k - 1] / fibs[n - k]) * (b - a);
        } else {
            b = x2;
            x2 = x1;
            x1 = a + (fibs[n - k - 2] / fibs[n - k]) * (b - a);
        }
    }
    x2 = x1 + eps;
    if (Math.abs(func(x1) - func(x2)) < eps) {
        a = x1;
    } else {
        b = x2;
    }
    return (a + b) / 2;
}
