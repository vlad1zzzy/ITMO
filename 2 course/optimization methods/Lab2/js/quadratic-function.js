export class QuadraticFunction {
    constructor(a, b, c, func, funcStr) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.func = func;
        this.funcStr = funcStr;
    }

    findFdkX(k, x) {
        let ans = this.reducedMultiplyVectors(this.a[k], x);
        ans += this.a[k][k] * x[k];
        ans += this.b[k];
        return ans;
    }

    findFx(x) {
        let ans = 0;
        for (let i = 0; i < this.a.length; i++) {
            for (let j = i; j < this.a.length; j++) {
                ans += this.a[i][j] * x[i] * x[j];
            }
        }
        ans += this.reducedMultiplyVectors(this.b, x);
        ans += this.c;
        return ans;
    }

    findG(x, lambda) {
        const newX = new Array(this.a.length);
        for (let i = 0; i < this.a.length; i++) {
            newX[i] = x[i] - lambda * this.findFdkX(i, x);
        }
        return this.findFx(newX);
    }

    findGradient(x, k) {
        const ans = new Array(this.a.length);
        for (let i = 0; i < this.a.length; i++) {
            ans[i] = k * this.findFdkX(i, x);
        }
        return ans;
    }

    dfNormalize(x) {
        const gradient = this.findGradient(x, 1);
        return Math.sqrt(this.reducedMultiplyVectors(gradient, gradient));
    }

    reducedAddVectors(x1, x2, k2) {
        const ans = new Array(x1.length);
        for (let i = 0; i < x1.length; i++) {
            ans[i] = x1[i] + k2 * x2[i];
        }
        return ans;
    }

    reducedMultiplyVectors(x1, x2) {
        let ans = 0;
        for (let i = 0; i < x1.length; i++) {
            ans += x1[i] * x2[i];
        }
        return ans;
    }

   findAp(p) {
        const ans = new Array(this.a.length).fill(0);
        for (let i = 0; i < this.a.length; i++) {
            for (let j = 0; j < this.a.length; j++) {
                if (i === j) {
                    ans[i] += this.a[i][j] * p[j];
                }
                ans[i] += this.a[i][j] * p[j];
            }
        }
        return ans;
    }

    minOfParabola(x1, x2, x3, f1, f2, f3) {
        let a0, a1, a2;
        a0 = f1;
        a1 = (f2 - a0) / (x2 - x1);
        a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2);
        return 0.5 * (x1 + x2 - a1 / a2);
    }

    equals(a, b, eps) {
        return Math.abs(a - b) < eps;
    }

    threeNotEquals( a,  b,  c,  eps) {
        return !this.equals(a, b, eps) && !this.equals(a, c, eps) && !this.equals(b, c, eps);
    }

    findMin(x, a, c, eps) {
        const phi = (3 - Math.sqrt(5)) / 2;
        let x2, x1, x3, xi = 0, f2, f1, f3, fi, d, e, g, tol;
        let accepted;
        x2 = x1 = x3 = a + phi * (c - a);
        f2 = f1 = f3 = this.findG(x, x2);
        d = e = c - a;
        do {
            accepted = false;
            g = e;
            e = d;
            tol = eps * Math.abs(x2) + eps / 10;
            if (Math.abs(x2 - (a + c) / 2) + (c - a) / 2 <= 2 * tol) {
                break;
            }
            if (this.threeNotEquals(x2, x3, x1, eps)) {
                xi = this.minOfParabola(x1, x2, x3, f1, f2, f3);
                if (a <= xi && xi <= c && Math.abs(xi - x2) < g / 2) {
                    accepted = true;
                    if (xi - a < 2 * tol || c - xi < 2 * tol) {
                        xi = x2 - Math.sign(x2 - (a + c) / 2) * tol;
                    }
                }
            }
            if (!accepted) {
                if (x2 < (a + c) / 2) {
                    xi = x2 + phi * (c - x2);
                    e = c - x2;
                } else {
                    xi = x2 - phi * (x2 - a);
                    e = x2 - a;
                }
            }
            if (Math.abs(xi - x2) < tol) {
                xi = x2 + Math.sign(xi - x2) * tol;
            }
            d = Math.abs(xi - x2);
            fi = this.findG(x, xi);
            if (fi <= f2) {
                if (xi >= x2) {
                    a = x2;
                } else {
                    c = x2;
                }
                x3 = x1;
                x1 = x2;
                x2 = xi;
                f3 = f1;
                f1 = f2;
                f2 = fi;
            } else {
                if (xi >= x2) {
                    c = xi;
                } else {
                    a = xi;
                }
                if (fi <= f1 || this.equals(x1, x2, eps)) {
                    x3 = x1;
                    x1 = xi;
                    f3 = f1;
                    f1 = fi;
                } else if (fi <= f3 || this.equals(x3, x2, eps) || this.equals(x3, x1, eps)) {
                    x3 = xi;
                    f3 = fi;
                }
            }
        } while (true);
        return x2;
    }
}
