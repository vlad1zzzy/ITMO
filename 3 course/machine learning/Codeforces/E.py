from math import log, exp
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
    class_amount = parsin(cf=to_int)
    ks = parsin(vpl=class_amount, cf=to_int)
    alpha, message_amount = parsin(cf=to_int), parsin(cf=to_int)
    class_word_amount, word_amount = {}, {}
    for i in range(class_amount):
        class_word_amount[i] = 0
    for i in range(message_amount):
        line = input().split(' ')
        class_number, message_size, words = int(line[0]), int(line[1]), line[2:]
        class_word_amount[class_number - 1] += 1
        for word in set(words):
            word_amount.setdefault(word, [0] * class_amount)
            word_amount.get(word)[class_number - 1] += 1

    for _ in range(parsin(cf=to_int)):
        words = set(input().split(' ')[1:])
        answers = {}
        for i in range(class_amount):
            answers[i] = log(ks[i] * class_word_amount[i] / message_amount)
            for word, class_count in word_amount.items():
                p = (class_count[i] + alpha) / (class_word_amount[i] + 2 * alpha)
                answers[i] += log(p if (word in words) else (1 - p))
        d = sum([exp(ans) for ans in answers.values()])
        for i in range(class_amount):
            print(exp(answers[i]) if d == 0 else exp(answers[i]) / d, end=' ')
        print()


if __name__ == '__main__':
    main()
