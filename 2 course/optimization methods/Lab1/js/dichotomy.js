import {func, stats} from "./main.js";

export {dichotomy}

export const s = 0.00000001

function dichotomy(a, b, eps) {
    let x1, x2, f1, f2, k = 1;
    do {
        x1 = (a + b - s) / 2;
        x2 = (a + b + s) / 2;
        f1 = func(x1);
        f2 = func(x2);
        stats.push([k, ...[a, b, b - a, x1, x2, f1, f2].map(el => el.toFixed(10))])
        if (f1 <= f2) {
            b = x2;
        } else {
            a = x1;
        }
        k++;
    } while ((b - a) / 2 > eps);
    return (a + b) / 2;
}