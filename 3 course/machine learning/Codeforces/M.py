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


# def distance(xs):
#     n = len(xs)
#     return 2 * np.sum([(2 * i - n + 1) * x for (i, x) in enumerate(sorted(xs))])
#     # xs.sort()
#     # res, s = 0, 0
#     # for i in range(len(xs)):
#     #     res += (xs[i] * i - s)
#     #     s += xs[i]
#     # return 2 * res


def main():
    k, n = parsin(cf=to_int), parsin(cf=to_int)
    # xs, ys = [], {}
    # for _ in range(n):
    #     x, y = parsin(l=1, vpl=2, cf=to_int)
    #     if y not in ys:
    #         ys[y] = [x]
    #     else:
    #         ys[y].append(x)
    #     xs.append(x)
    # inner = np.sum([distance(xs) for xs in ys.values()])
    # print(inner)
    # print(distance(xs) - inner)
    ks, dots = [[] for _ in range(k)], []
    for _ in range(n):
        x, y = parsin(l=1, vpl=2, cf=to_int)
        y -= 1
        ks[y].append(x)
        dots.append((x, y))

    inner, outer = 0, 0
    for k_i in ks:
        cl = sorted(k_i.copy())
        p_sum, s_sum = 0, sum(cl)
        for i in range(len(cl)):
            s_sum -= cl[i]
            inner += (i * cl[i] - p_sum) + (s_sum - (len(cl) - 1 - i) * cl[i])
            p_sum += cl[i]
    print(inner)

    dots = sorted(dots)
    all_pref, all_suf = 0, 0
    pref, suf, pref_cnt, suf_cnt = {y_i: 0 for y_i in range(k)}, {y_i: 0 for y_i in range(k)}, {y_i: 0 for y_i in range(k)}, {y_i: 0 for y_i in range(k)}

    for x, y in dots:
        all_suf += x
        suf[y] += x
        suf_cnt[y] += 1

    for i, (x, y) in enumerate(sorted(dots)):
        all_pref += x
        all_suf -= x
        pref[y] += x
        suf[y] -= x
        pref_cnt[y] += 1
        suf_cnt[y] -= 1
        outer += ((i + 1 - pref_cnt[y]) * x - (all_pref - pref[y])) + ((all_suf - suf[y]) - ((n - (i + 1)) - suf_cnt[y]) * x)
    print(outer)


if __name__ == '__main__':
    main()
