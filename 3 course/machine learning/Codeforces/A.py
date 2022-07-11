__author__ = "Kryukov Vladislav"


def identity(*args):
    if len(args) == 1:
        return args[0]
    return args


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
    [[_, _, k], items] = parsin(2, 2, lambda x: int(x))
    items = list(map(lambda x: (x[0] + 1, str(x[1])), enumerate(items)))

    stat = {}
    for (index, element) in items:
        if element in stat:
            stat[element].append(index)
        else:
            stat[element] = [index]

    res = [[] for _ in range(k)]
    index = 0
    for element in stat:
        for i in stat[element]:
            res[index].append(i)
            index = (index + 1) % k

    for i in res:
        print(len(i), *sorted(i))


if __name__ == '__main__':
    main()
