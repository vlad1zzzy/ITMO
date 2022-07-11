from math import e, pi, cos

__author__ = "Kryukov Vladislav"


def identity(*args):
    if len(args) == 1:
        return args[0]
    return args


def to_int(s):
    return int(s)


def parsin(l=1, vpl=1, cf=identity, s=" "):
    """ Can parse inputs usually used in competitive programming problems.
    Arguments:
    - l, as in "Lines", the number of lines to parse at once.
    - vpl, as in "Values Per Line", the number of values to parse per line.
    - cf, as in "Cast Function", the function to apply to each parsed element.
    - s, as in "Separator", the string separating multiple values in the same
      line.
    """
    if l == 1:
        if vpl == 1:
            return cf(input())
        else:
            return list(map(cf, input().split(s)))
    else:
        if vpl == 1:
            return [cf(input()) for _ in range(l)]
        else:
            return [list(map(cf, input().split(s))) for _ in range(l)]


def less_or_equal_1(variable, value):
    return value if abs(variable) < 1 else 0


kernels = {
    "uniform": (lambda u: less_or_equal_1(u, 0.5)),
    "triangular": (lambda u: less_or_equal_1(u, 1 - abs(u))),
    "epanechnikov": (lambda u: less_or_equal_1(u, 0.75 * (1 - u ** 2))),
    "quartic": (lambda u: less_or_equal_1(u, (15 / 16) * (1 - u ** 2) ** 2)),
    "triweight": (lambda u: less_or_equal_1(u, (35 / 32) * (1 - u ** 2) ** 3)),
    "tricube": (lambda u: less_or_equal_1(u, (70 / 81) * (1 - abs(u) ** 3) ** 3)),
    "gaussian": (lambda u: (1 / (2 * pi) ** 0.5) * e ** (-0.5 * u ** 2)),
    "cosine": (lambda u: less_or_equal_1(u, (pi / 4) * cos(pi * u / 2))),
    "logistic": (lambda u: 1 / (e ** u + 2 + e ** (-u))),
    "sigmoid": (lambda u: 2 / (pi * (e ** u + e ** (-u)))),
}

distances = {
    "manhattan": (lambda f, s: sum([abs(f[i] - s[i]) for i in range(len(f))])),
    "euclidean": (lambda f, s: sum([(f[i] - s[i]) ** 2 for i in range(len(f))]) ** 0.5),
    "chebyshev": (lambda f, s: max([abs(f[i] - s[i]) for i in range(len(f))])),
}

windows = {
    "fixed": False,
    "variable": True,
}


def regression(data, q, metric, kernel, window, h):
    distance = sorted([metric(q, point[:-1]) for point in data])[h] if window else h
    up, down = 0, 0
    for point in data:
        k = kernel(metric(point[:-1], q) if distance == 0 else metric(point[:-1], q) / distance)
        up += k * point[-1]
        down += k
    return up / down if down != 0 else sum([point[-1] for point in data]) / len(data)


def main():
    n, m = parsin(l=1, vpl=2, cf=to_int)
    data = [parsin(l=n, vpl=m + 1, cf=to_int)] if n == 1 else parsin(l=n, vpl=m + 1, cf=to_int)
    q = [parsin(l=1, vpl=m, cf=to_int)] if m == 1 else parsin(l=1, vpl=m, cf=to_int)
    metric, kernel, window = parsin(), parsin(), parsin()
    h = parsin(cf=to_int)

    # print(n, m, data, q, metric, kernel, window, h, sep='\n')
    print(regression(data, q, distances[metric], kernels[kernel], windows[window], h))


if __name__ == '__main__':
    main()
