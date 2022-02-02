import {stats, func} from "./main.js";

export {golden}

function golden(a,  b,  eps) {
    let phi = (1 + Math.sqrt(5)) / 2;
    let resPhi = 2 - phi;
    let x1 = a + resPhi * (b - a);
    let x2 = b - resPhi * (b - a);
    let f1 = func(x1);
    let f2 = func(x2);
    let k = 1;
    do {
        stats.push([k, ...[a, b, b - a, x1, x2, f1, f2].map(el => el.toFixed(10))])
        if (f1 < f2) {
            b = x2;
            x2 = x1;
            f2 = f1;
            x1 = a + resPhi * (b - a);
            f1 = func(x1);
        } else {
            a = x1;
            x1 = x2;
            f1 = f2;
            x2 = b - resPhi * (b - a);
            f2 = func(x2);
        }
        k++;
    } while (Math.abs(b - a) > eps);
    return (x1 + x2) / 2;
}