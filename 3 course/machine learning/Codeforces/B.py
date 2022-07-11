import numpy as np

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


def f_score(r, p):
    return divide(2 * p * r, p + r)


def divide(numerator, denominator):
    return 0 if np.abs(denominator) < 0.0000000001 else numerator / denominator


def main():
    K = parsin(cf=lambda n: int(n))
    CM = np.array(parsin(l=K, vpl=K, cf=lambda n: int(n))) if K != 1 else [[parsin(cf=lambda n: int(n))]]
    column, row, full = np.sum(CM, axis=0), np.sum(CM, axis=1), np.sum(CM)

    tp = [CM[i][i] for i in range(K)]
    fp = [column[i] - tp[i] for i in range(K)]
    fn = [row[i] - tp[i] for i in range(K)]

    tp_w = [tp[i] * row[i] for i in range(K)]
    fp_w = [fp[i] * row[i] for i in range(K)]
    fn_w = [fn[i] * row[i] for i in range(K)]

    precisions = [divide(tp[i], tp[i] + fp[i]) for i in range(K)]
    recalls = [divide(tp[i], tp[i] + fn[i]) for i in range(K)]
    scores = [f_score(recalls[i], precisions[i]) for i in range(K)]

    precision_macro = divide(np.sum([divide(CM[i][i] * row[i], column[i]) for i in range(K)]), full)

    precision_micro = divide(np.sum(tp_w), np.sum(tp_w) + np.sum(fp_w))
    recall_micro = divide(np.sum(tp_w), np.sum(tp_w) + np.sum(fn_w))

    f_micro = f_score(precision_micro, recall_micro)
    f_macro = f_score(divide(np.sum(tp), full), precision_macro)
    f_1 = np.average(scores, weights=row)

    print(f_micro, f_macro, f_1, sep='\n')


if __name__ == '__main__':
    main()
