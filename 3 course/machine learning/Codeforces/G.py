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


def reduce(function, iterable, initializer=None):
    it = iter(iterable)
    if initializer is None:
        value = next(it)
    else:
        value = initializer
    for element in it:
        value = function(value, element)
    return value


def main():
    m = parsin(cf=to_int)
    inputs, count = [], 0
    for i in range(1 << m):
        value = parsin(cf=to_int)
        count += value
        if value == 1:
            inputs.append(format(i, 'b').zfill(m)[::-1])

    def convert(binary_input):
        weights = list(map(lambda x: -1 if x == '0' else 1, binary_input))
        return weights + [reduce(lambda acc, x: acc - 1 if x == 1 else acc, weights, 0.5)]

    if count == 0:
        print(1, 1, sep='\n')
        print(*[0] * m, -1)
    else:
        print(2)
        print(count, 1)
        [print(*convert(binary)) for binary in inputs]
        print(*[1] * count, -0.5)


if __name__ == '__main__':
    main()
