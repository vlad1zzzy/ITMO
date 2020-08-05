from math import sqrt
from math import acos
from math import degrees

def cos(v,w):
    return (v[0]*w[0] + v[1]*w[1] + v[2]*w[2])/((sqrt(v[0]**2 + v[1]**2 + v[2]**2) *
            (sqrt(w[0]**2 + w[1]**2 + w[2]**2))
            ))


def fight(v,a,m,w):
    nr = [a[1], -a[0], 0] #правая пушка
    nl = [-a[1], a[0], 0] #левая пушка
    vec = [w[0]-v[0], w[1]-v[1], w[2]-v[2]]
    if (abs(cos(m,[0,0,1])) < 0.5 or 
        abs(cos(vec, a)) > sqrt(3)/2):
        return 0
    side = -1 if cos(vec, a) < 0 else 1
    if cos(nl, vec) >= 0.5:
        return 1, side*degrees(acos(cos(nl, vec))), side*degrees(acos(abs(cos(m,[0,0,1])))), "hello"
    return -1, side*degrees(acos(cos(nr, vec))), side*degrees(acos(abs(cos(m,[0,0,1])))), "hello"


if __name__ == '__main__':
    file = open("input.txt")

    v = list(map(int,file.readline().split())) #коорд. корабля
    a = list(map(int,file.readline().split())) #направление
    m = list(map(int,file.readline().split())) #наклон
    w = list(map(int,file.readline().split())) #коорд. врага

    result = fight(v,a,m,w) #огонь
    out = open("output.txt", "w")
    if result == 0:
        out.write("0" + "\n" + "hello")
    else:
        for i in result:
            out.write(str(i) + "\n")

    file.close()
    out.close()
