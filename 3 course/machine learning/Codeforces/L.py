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
    n = parsin(cf=to_int)
    data = [parsin(l=n, vpl=2, cf=to_int)] if n == 1 else parsin(l=n, vpl=2, cf=to_int)
    data = np.array(data)
    if n == 1:
        print(0)
        return
    xs, ys = data[:, 0], data[:, 1]
    x_rank, y_rank = xs.argsort().argsort(), ys.argsort().argsort()
    s = np.sum([(x_rank[i] - y_rank[i]) ** 2 for i in range(n)])
    print(1 - (6 * s) / (n * (n * n - 1)))


if __name__ == '__main__':
    main()
