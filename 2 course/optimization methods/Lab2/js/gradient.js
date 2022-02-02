export function gradient(xk, quadFunc, epsilon, a, b) {
    let k = 0;
    const data = [xk]
    let lambda = quadFunc.findMin(xk, a, b, epsilon);
    let x;
    do {
        x = xk;
        xk = quadFunc.reducedAddVectors(x, quadFunc.findGradient(x, -1), lambda);
        data.push(xk);
        while (quadFunc.findFx(xk) > quadFunc.findFx(x)) {
            if (k > 1000) {
                return data;
            }
            lambda /= 2;
            xk = quadFunc.reducedAddVectors(x, quadFunc.findGradient(x, -1), lambda);
            data.push(xk);
            k++;
        }
        k++;
    } while (quadFunc.dfNormalize(xk) > epsilon && k < 1000);
    return data;
}

export function gradientGreatDescent(xk, quadFunc, epsilon, a, b) {
    let k = 0;
    let lambda, g;
    const data = [xk]
    let x, p = quadFunc.findGradient(xk, -1);
    do {

        k++;
        x = xk;
        lambda = quadFunc.findMin(x, a, b, epsilon);
        xk = quadFunc.reducedAddVectors(x, p, lambda);
        g = quadFunc.dfNormalize(xk);
        p = quadFunc.findGradient(xk, -1);
        data.push(x)
    } while (g > epsilon && k < 10000);
    return data;
}
export function gradientConjugate(xk, quadFunc, epsilon, a, b, n = 2) {
    let k = 0;
    let lambda, beta, ng1 = 10;
    let x, p = quadFunc.findGradient(xk, -1), g1, g;
    g1 = quadFunc.findGradient(xk, 1);
    const data = [xk]
    do {
        k++;
        x = xk;
        g = g1;
        let apk = quadFunc.findAp(p);
        let f = quadFunc.reducedMultiplyVectors(apk, p);
        let ng = Math.sqrt(quadFunc.reducedMultiplyVectors(g, g));
        lambda = ng * ng / f;
        xk = quadFunc.reducedAddVectors(x, p, lambda);
        data.push(xk);
        g1 = quadFunc.reducedAddVectors(g, apk, lambda);
        if (k % n === 0) {
            g1 = quadFunc.findGradient(xk, -1);
        } else {
            ng1 = Math.sqrt(quadFunc.reducedMultiplyVectors(g1, g1));
            beta = ng1 * ng1 / (ng * ng);
            p = quadFunc.reducedAddVectors(quadFunc.findGradient(xk, -1), p, beta);
        }
    } while (ng1 > epsilon && k < 10000);
    return data;
}