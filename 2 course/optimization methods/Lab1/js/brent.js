import {func, stats} from "./main.js";

export {brent}

function brent(a, b, eps) {
    let k = 1;
    const phi = (3 - Math.sqrt(5)) / 2;
    let x2, x1, x3, xi = 0, f2, f1, f3, fi, d, e, g, tol;
    let accepted;
    x2 = x1 = x3 = a + phi * (b - a);
    f2 = f1 = f3 = func(x2);
    d = e = b - a;
    do {
        accepted = false;
        g = e;
        e = d;
        tol = eps * Math.abs(x2) + eps / 10;
        if (Math.abs(x2 - (a + b) / 2) + (b - a) / 2 <= 2 * tol) {
            break;
        }
        if (threeNotEquals(x2, x3, x1, eps)) {
            xi = minOfParabola(x1, x2, x3, f1, f2, f3);
            if (a <= xi && xi <= b && Math.abs(xi - x2) < g / 2) {
                accepted = true;
                if (xi - a < 2 * tol || b - xi < 2 * tol) {
                    xi = x2 - Math.sign(x2 - (a + b) / 2) * tol;
                }
            }
        }
        if (!accepted) {
            if (x2 < (a + b) / 2) {
                xi = x2 + phi * (b - x2);
                e = b - x2;
            } else {
                xi = x2 - phi * (x2 - a);
                e = x2 - a;
            }
        }
        if (Math.abs(xi - x2) < tol) {
            xi = x2 + Math.sign(xi - x2) * tol;
        }
        d = Math.abs(xi - x2);
        fi = func(xi);
        stats.push([k, ...[a, b, x1, x2, x3, f1, f2, f3, xi, fi].map(el => el.toFixed(10))])
        if (fi <= f2) {
            if (xi >= x2) {
                a = x2;
            } else {
                b = x2;
            }
            x3 = x1;
            x1 = x2;
            x2 = xi;
            f3 = f1;
            f1 = f2;
            f2 = fi;
        } else {
            if (xi >= x2) {
                b = xi;
            } else {
                a = xi;
            }
            if (fi <= f1 ||  equals(x1, x2, eps)) {
                x3 = x1;
                x1 = xi;
                f3 = f1;
                f1 = fi;
            } else if (fi <= f3 || equals(x3, x2, eps) || equals(x3, x1, eps)) {
                x3 = xi;
                f3 = fi;
            }
        }
        k++;
    } while (true);
    return x2;
}

function equals( a,  b,  eps) {
    return Math.abs(a - b) < eps;
}

function threeNotEquals( a,  b,  c,  eps) {
    return !equals(a, b, eps) && !equals(a, c, eps) && !equals(b, c, eps);
}

function minOfParabola(x1, x2, x3, f1, f2, f3) {
    let a0, a1, a2;
    a0 = f1;
    a1 = (f2 - a0) / (x2 - x1);
    a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
    return 0.5 * (x1 + x2 - a1 / a2);
}