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
    x_avg, y_avg = np.average(data[:, 0]), np.average(data[:, 1])

    num = sum([(x - x_avg) * (y - y_avg) for [x, y] in data])
    denum = np.sqrt(
        sum([(x - x_avg) ** 2 for [x, _] in data])
        *
        sum([(y - y_avg) ** 2 for [_, y] in data])
    )

    print(0 if num == 0 or denum == 0 else num / denum)


if __name__ == '__main__':
    main()
