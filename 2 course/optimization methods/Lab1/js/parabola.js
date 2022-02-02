import {stats, func} from "./main.js";

export {parabola}

function parabola(a, b, eps) {
    let k = 1;
    let x1 = a, x2, x3 = b;
    let f1 = func(x1), f2, f3 = func(x3);
    x2 = f1 < f3 ? a + eps : b - eps;
    f2 = func(x2);
    let a0, a1, a2, xi, fi, x;
    a0 = f1;
    a1 = getA1(a0, x1, x2, f2);
    a2 = getA2(a1, x1, x2, x3, f1, f3);
    xi = 0.5 * (x1 + x2 - a1 / a2);
    fi = func(xi);
    do {
        stats.push([k, ...[a0, a1, a2, x1, x2, x3, f1, f2, f3, xi, fi].map(el => el.toFixed(10))])
        if (x1 < xi && xi < x2) {
            if (fi >= f2) {
                x1 = xi;
                f1 = fi;
            } else {
                x3 = x2;
                f3 = f2;
                x2 = xi;
                f2 = fi;
            }

        } else {
            if (f2 >= fi) {
                x1 = x2;
                f1 = f2;
                x2 = xi;
                f2 = fi;
            } else {
                x3 = xi;
                f3 = fi;
            }
        }
        a0 = f1;
        a1 = getA1(a0, x1, x2, f2);
        a2 = getA2(a1, x1, x2, x3, f1, f3);
        x = 0.5 * (x1 + x2 - a1 / a2);
        if (Math.abs(x - xi) < eps) {
            break;
        }
        xi = x;
        fi = func(x);
        k++;
    } while (true);
    return x;
}

function getA1(a0, x1, x2, f2) {
    return (f2 - a0) / (x2 - x1);
}

function getA2(a1, x1, x2, x3, f1, f3) {
    return ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
}