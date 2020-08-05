#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <fstream>

using namespace std;

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
            digits[stringSize - i - 1] = (int)str[i] - 48;
        }
        if ('0' <= str[0] && str[0] <= '9'){
            digits[stringSize - 1] = (int)str[0] - 48;
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
            int prev = 0;
            for (int j = 0; j < num.size; j++) {
                res[i + j + 1] += (res[i + j] + this->number[i] * num.number[j]) / 10;
                res[i + j] = (res[i + j] + this->number[i] * num.number[j]) % 10;
            }
        }
        while (!res[--curSize]);
        return lll_int(this->sign * num.sign, curSize + 1, res);
    }

    lll_int divide_by_2() {
        vector<int> res = this->number;
        res[this->size - 1] /= 2;
        for (int i = this->size - 2; i >= 0; i--)
            res[i] = (res[i] + this->number[i + 1] % 2 * 10) / 2;
        if (this->size > 1 && !res[this->size - 1])
            this->size--;
        this->number = res;
        if (lll_int{-1, 1, {0}} == *this)
            this->sign = 1;
        return *this;
    }

    lll_int operator/(lll_int& num) {
        if (zero() == num)
            return lll_int(2,0,{0});
        if (zero() == *this)
            return *this;
        int signCur = this->sign * num.sign;
        this->sign = 1;
        num.sign = 1;
        lll_int ans = one(), left = zero(), right = *this;
        while (left <= right) {
            lll_int mid = (left + right).divide_by_2();
            lll_int cur = mid * num;
            if (cur == *this)
                return mid;
            if (cur < *this) {
                left = one() + mid;
                ans = mid;
            } else {
                right = -one() + mid;
            }
        }
        ans.sign = signCur;
        return ans;
    }

    lll_int operator% (lll_int& num) {
        lll_int full = (*this / num) * num;
        return *this - full;
    }

    lll_int sqrt() {
        if (zero() > *this)
            return lll_int(2,0,{0});
        if (zero() == *this || one() == *this)
            return *this;
        lll_int ans = one(), left = one(), right = *this;
        while (left <= right) {
            lll_int mid = (left + right).divide_by_2();
            lll_int cur = mid * mid;
            if (cur == *this)
                return mid;
            if (cur < *this) {
                left = one() + mid;
                ans = mid;
            } else {
                right = -one() + mid;
            }
        }
        return ans;
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
        for (int i = this->size - 1; i >=0; i--)
            res += to_string(this->number[i]);
        if (this->sign == -1)
            res = "-" + res;
        return res;
    }

    static lll_int zero() {
        return lll_int(1, 1, {0});
    }

    static lll_int one() {
        return lll_int(1, 1, {1});
    }
};

int main(int argc, char **argv) {
    if (argc != 3){
        cout << "Wrong number of args";
        exit(1);
    }
    string num1, op, num2, result;

    ifstream input( argv[1] );
    ofstream output(argv[2] );

    if (!(input.is_open() || output.is_open())){
        cout << "Missing file";
        exit(2);
    }

    input >> num1 >> op;
    lll_int first = lll_int(num1);
    if (op == "#")
        result = first.sqrt().toString();
    else {
        input >> num2;
        lll_int second = lll_int(num2);
        if (op == "+")
            result = (first + second).toString();
        else if (op == "-")
            result = (first - second).toString();
        else if (op == "*")
            result = (first * second).toString();
        else if (op == "/")
            result = (first / second).toString();
        else if (op == "%")
            result = (first % second).toString();
        else if (op == "<")
            result = (first < second) ? "1" : "0";
        else if (op == ">")
            result = (first > second) ? "1" : "0";
        else if (op == "<=")
            result = (first <= second) ? "1" : "0";
        else if (op == ">=")
            result = (first >= second) ? "1" : "0";
        else if (op == "==")
            result = (first == second) ? "1" : "0";
        else if (op == "!=")
            result = (first != second) ? "1" : "0";
        else
            result = "unknown operation";
    }
    output << result;
    input.close();
    output.close();
    return 0;
}