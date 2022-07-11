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
    _, _ = parsin(l=1, vpl=2)
    n = parsin(cf=to_int)
    xs, xys = {}, {}
    for _ in range(n):
        x, y = parsin(l=1, vpl=2, cf=to_int)
        xs[x] = xs.get(x, 0) + 1 / n
        xys[(x, y)] = xys.get((x, y), 0) + 1 / n
    print(np.sum([xys[(x, y)] * (np.log(xs[x]) - np.log(xys[(x, y)])) for (x, y) in xys]))


if __name__ == '__main__':
    main()
