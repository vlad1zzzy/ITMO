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
    k, n = parsin(cf=to_int), parsin(cf=to_int)
    data = [parsin(l=n, vpl=2, cf=to_int)] if n == 1 else parsin(l=n, vpl=2, cf=to_int)
    data = np.array(data)
    x, y = data[:, 0], data[:, 1]
    p_x, e_yx = [0] * k, [0] * k
    e_y2 = np.sum([yi ** 2 / n for yi in y])
    for xi, yi in zip(x, y):
        p_x[xi - 1] += 1 / n
        e_yx[xi - 1] += yi / n
    ee = np.sum([0 if p_x[i] == 0 else e_yx[i] ** 2 / p_x[i] for i in range(k)])
    print(e_y2 - ee)


if __name__ == '__main__':
    main()
