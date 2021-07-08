//
// Created by Vlad on 21.05.2021.
//
#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <cmath>
#include <algorithm>
#include <stack>
#include <queue>
#include <set>
#include <iomanip>
#include <complex>

using namespace std;

const int INF = 1e9;
const long long INF_ll = 1e18;
int n, m, k;
const double PI = std::atan(1.0) * 4;

struct lll_int {
    int size, sign;
    vector<int> number;

    lll_int(int minus, int curSize, vector<int> curNumber) {
        sign = minus;
        size = curSize;
        number = move(curNumber);
    }

    lll_int(string str) {
        int stringSize = str.size();
        vector<int> digits(stringSize, 0);
        for (int i = stringSize - 1; i > 0; i--) {
            digits[stringSize - i - 1] = (int) str[i] - 48;
        }
        if ('0' <= str[0] && str[0] <= '9') {
            digits[stringSize - 1] = (int) str[0] - 48;
            sign = 1;
        } else {
            digits.pop_back();
            stringSize--;
            sign = -1;
        }
        size = stringSize;
        number = move(digits);
    }

    lll_int operator-() {
        this->sign = -(this->sign);
        return *this;
    }

    int whichMore(lll_int &num) {
        if (this->sign != num.sign)
            return (this->sign == 1) ? 1 : 2;
        if (this->size != num.size)
            return (this->size * this->sign > num.size * num.sign) ? 1 : 2;
        int length = max(this->size, num.size);
        int s = this->sign;
        for (int i = length - 1; i >= 0; i--) {
            if (this->number[i] > num.number[i]) {
                return (s == 1) ? 1 : 2;
            } else if (this->number[i] < num.number[i]) {
                return (s == 1) ? 2 : 1;
            }
        }
        return 0;
    }

    bool operator>(lll_int &num) {
        return whichMore(num) == 1;
    }

    bool operator<(lll_int &num) {
        return whichMore(num) == 2;
    }

    bool operator>=(lll_int &num) {
        return whichMore(num) != 2;
    }

    bool operator<=(lll_int &num) {
        return whichMore(num) != 1;
    }

    bool operator==(lll_int &num) {
        return whichMore(num) == 0;
    }

    bool operator!=(lll_int &num) {
        return whichMore(num) != 0;
    }

    lll_int operator+(lll_int &num) {
        make_one_length(num);
        int length = max(this->size, num.size);
        vector<int> res(length + 1, 0);
        if (this->sign == num.sign) {
            for (int i = 0; i < length; i++) {
                res[i + 1] = (res[i] + this->number[i] + num.number[i]) / 10;
                res[i] = (res[i] + this->number[i] + num.number[i]) % 10;
            }
            if (res[length])
                length++;
            return lll_int(this->sign, length, res);
        } else if (this->sign == -1) {
            *this = -*this;
            return num - *this;
        } else {
            num = -num;
            return *this - num;
        }
    }

    lll_int operator-(lll_int &num) {
        make_one_length(num);
        int length = max(this->size, num.size);
        vector<int> first = this->number;
        vector<int> second = num.number;
        vector<int> res(length + 1, 0);
        if (this->sign != num.sign) {
            if (this->sign == -1)
                return -((-*this) + num);
            else
                return -num + *this;
        }
        if (*this == num)
            return lll_int(1, 1, {0});
        else if (*this > num) {
            int curSize = 1;
            for (int i = 0; i < first.size(); i++) {
                if (first[i] < second[i])
                    first[i + 1]--;
                res[i] = (first[i] - second[i] + 10) % 10;
                if (res[i])
                    curSize = i + 1;
            }
            return lll_int(this->sign, curSize, res);
        } else {
            int curSize = 1;
            for (int i = 0; i < second.size(); i++) {
                if (second[i] < first[i])
                    second[i + 1]--;
                res[i] = (second[i] - first[i] + 10) % 10;
                if (res[i])
                    curSize = i + 1;
            }
            return lll_int(-this->sign, curSize, res);
        }
    }

    lll_int operator*(lll_int &num) {
        if ((this->size == 1 && !this->number[0]) || (num.size == 1 && !num.number[0]))
            return lll_int(1, 1, {0});
        int curSize = this->size + num.size + 1;
        vector<int> res(curSize);
        for (int i = 0; i < this->size; i++) {
            for (int j = 0; j < num.size; j++) {
                res[i + j + 1] += (res[i + j] + this->number[i] * num.number[j]) / 10;
                res[i + j] = (res[i + j] + this->number[i] * num.number[j]) % 10;
            }
        }
        while (!res[--curSize]);
        return lll_int(this->sign * num.sign, curSize + 1, res);
    }


    void make_one_length(lll_int &num) {
        int resSize = max(this->size, num.size);
        vector<int> first(resSize, 0);
        vector<int> second(resSize, 0);
        for (int i = 0; i < this->size; i++)
            first[i] = this->number[i];
        for (int i = 0; i < num.size; i++)
            second[i] = num.number[i];
        this->number = first;
        num.number = second;
    }

    string toString() {
        string res;
        if (this->sign == 2)
            return "NaN";
        for (int i = this->size - 1; i >= 0; i--)
            res += to_string(this->number[i]);
        if (this->sign == -1)
            res = "-" + res;
        return res;
    }
};

vector<lll_int> p, q;
lll_int zero = lll_int(1, 1, {0});

vector<lll_int> mul() {
    vector<lll_int> res(n + n + 2, zero);
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= n; j++) {
            lll_int tmp = p[i] * q[j];
            res[i + j] = res[i + j] + tmp;
        }
    }
    return res;
}

typedef complex<double> base;

void fft(vector<base> &a, bool invert) {
    int n = (int) a.size();
    if (n == 1) return;

    vector<base> a0(n / 2), a1(n / 2);
    for (int i = 0, j = 0; i < n; i += 2, ++j) {
        a0[j] = a[i];
        a1[j] = a[i + 1];
    }
    fft(a0, invert);
    fft(a1, invert);

    double ang = 2 * PI / n * (invert ? -1 : 1);
    base w(1), wn(cos(ang), sin(ang));
    for (int i = 0; i < n / 2; ++i) {
        a[i] = a0[i] + w * a1[i];
        a[i + n / 2] = a0[i] - w * a1[i];
        if (invert)
            a[i] /= 2, a[i + n / 2] /= 2;
        w *= wn;
    }
}

void multiply(const vector<int> &a, const vector<int> &b, vector<int> &res) {
    vector<base> fa(a.begin(), a.end()), fb(b.begin(), b.end());
    m = 1;
    while (m < max(a.size(), b.size())) m <<= 1;
    m <<= 1;
    fa.resize(m), fb.resize(m);

    fft(fa, false), fft(fb, false);
    for (int i = 0; i < m; ++i)
        fa[i] *= fb[i];
    fft(fa, true);

    res.resize(m);
    for (int i = 0; i < m; ++i)
        res[i] = int(fa[i].real() + 0.5);
}

vector<int> A, B;

int main() {
    cin >> n;
    /*
    string tmp;
    p.resize(n + 1, zero);
    q.resize(n + 1, zero);
    for (int i = 0; i <= n; i++) {
        cin >> tmp;
        p[i] = lll_int(tmp);
    }
    for (int i = 0; i <= n; i++) {
        cin >> tmp;
        q[i] = lll_int(tmp);
    }
    vector<lll_int> muled = mul();
    for (int i = 0; i <= n * 2; i++) {
        cout << muled[i].toString() << " ";
    }*/

    A.resize(n + 1, 0);
    B.resize(n + 1, 0);
    for (int i = 0; i <= n; i++) {
        cin >> A[i];
    }
    for (int i = 0; i <= n; i++) {
        cin >> B[i];
    }

    vector<int> muled;
    multiply(A, B, muled);
    for (int i = 0; i <= n * 2; i++) {
        cout << muled[i] << " ";
    }
    return 0;
}