from math import sqrt


def gcd(x, y):
    while (y):
        x, y = y, x % y
    return x


'''Нахождение коэффицентов плоскости'''


def definePlane(cords):
    A = (cords[0][1] * (cords[1][2] - cords[2][2]) +
         cords[1][1] * (cords[2][2] - cords[0][2]) +
         cords[2][1] * (cords[0][2] - cords[1][2]))
    B = (cords[0][2] * (cords[1][0] - cords[2][0]) +
         cords[1][2] * (cords[2][0] - cords[0][0]) +
         cords[2][2] * (cords[0][0] - cords[1][0]))
    C = (cords[0][0] * (cords[1][1] - cords[2][1]) +
         cords[1][0] * (cords[2][1] - cords[0][1]) +
         cords[2][0] * (cords[0][1] - cords[1][1]))
    D = (-A * cords[0][0] - B * cords[0][1] - C * cords[0][2])
    k = gcd(A, B)
    for i in range(2):
        k = gcd(k, [C, D][i])
    return [A / k, B / k, C / k, D / k]


'''Нахождение координат точек куба, построение плоскостей граней'''


def defineCube(abcd):
    A, B, C, D = abcd
    F = [A[0] + D[0] - B[0], A[1] + D[1] - B[1], A[2] + D[2] - B[2]]
    G = [A[0] + D[0] - C[0], A[1] + D[1] - C[1], A[2] + D[2] - C[2]]
    return [definePlane([A, B, C]), definePlane([B, C, D]),
            definePlane([C, D, F]), definePlane([A, F, G]),
            definePlane([A, B, G]), definePlane([D, F, G])]


'''Расстояние между точками'''


def lengthFromDotToDot(dot1, dot2):
    return (sqrt((dot2[0] - dot1[0]) ** 2 +
                 (dot2[1] - dot1[1]) ** 2 +
                 (dot2[2] - dot1[2]) ** 2))


'''Скалярное произведение'''


def scalPr(vec1, vec2):
    return (vec1[0] * vec2[0] +
            vec1[1] * vec2[1] +
            vec1[2] * vec2[2])


'''Вектор по двум точкам'''


def toVector(dot1, dot2):
    return [dot2[i] - dot1[i] for i in range(3)]


'''Точка пересечение прямой и плоскости'''


def findDotOnPlane(line, plane):
    tx, ty, tz = line
    A, B, C, D = plane
    countT = A * tx[0] + B * ty[0] + C * tz[0]
    nums = A * tx[1] + B * ty[1] + C * tz[1] + D
    t = -nums / countT
    return [tx[0] * t + tx[1],
            ty[0] * t + ty[1],
            tz[0] * t + tz[1]]


'''Отражение вектора'''


def reverse(vector, normal):
    length = sqrt(sum([i ** 2 for i in normal]))
    projection = scalPr(normal, vector) / length / length
    return [(vector[i] - 2 * projection * normal[i]) for i in range(3)]


'''Параметрическое уравние прямой'''


def toLine(vec, dot):
    x = [vec[0], dot[0]]
    y = [vec[1], dot[1]]
    z = [vec[2], dot[2]]
    return [x, y, z]


'''Описание полета луча'''


def startMove(vector, dotStart):
    global energy, eps, n, current

    line = toLine(vector, dotStart)
    closest = float("inf")
    for i in range(n + 6):
        if -eps < scalPr(vector, planes[i][:-1]) < eps:
            continue
        r = findDotOnPlane(line, planes[i])
        if scalPr(vector, toVector(dotStart, r)) >= 0 and eps < lengthFromDotToDot(dotStart, r) < closest:
            closest = lengthFromDotToDot(dotStart, r)
            current = i

    if current < 6:
        r = findDotOnPlane(line, planes[current])
        out.write("1\n")
        out.write(str(energy) + "\n")
        for i in r:
            out.write(str(round(i, 5)) + " ")
        out.write('\n')
        for i in vector:
            out.write(str(i) + " ")
        return
    else:
        energy -= 1
        r = findDotOnPlane(line, planes[current])
        if not energy:
            out.write("0\n")
            for i in r:
                out.write(str(i) + " ")
            return
        vector = reverse(vector, planes[current][:-1])
        startMove(vector, r)
    return


if __name__ == "__main__":
    file = open("input.txt")
    out = open("output.txt", "w")
    cube = defineCube([list(map(float, file.readline().split())) for i in range(4)])
    vector = list(map(float, file.readline().split()))
    dot = list(map(float, file.readline().split()))
    energy = int(file.readline())
    n = int(file.readline())
    mirrors = [definePlane([list(map(float, file.readline().split()))
                            for j in range(3)])
               for i in range(n)]
    planes = cube + mirrors
    current = 'none'
    eps = 1e-3

    startMove(vector, dot)

    file.close()
    out.close()
