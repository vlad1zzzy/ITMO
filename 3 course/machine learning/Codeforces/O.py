import numpy as np

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


def main():
    k1, k2 = parsin(l=1, vpl=2, cf=to_int)
    n = parsin(cf=to_int)
    xs, ys, count = [0] * k1, [0] * k2, {}
    for _ in range(n):
        x, y = parsin(l=1, vpl=2, cf=lambda v: int(v) - 1)
        count[(x, y)] = count.get((x, y), 0) + 1
        xs[x] += 1
        ys[y] += 1
    print(n + np.sum([count[(x, y)] * (count[(x, y)] / (xs[x] * ys[y] / n) - 2) for (x, y) in count]))


if __name__ == '__main__':
    main()
