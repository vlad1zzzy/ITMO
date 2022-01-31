'use strict';

/**
 * Складывает два целых числа
 * @param {Number} a Первое целое
 * @param {Number} b Второе целое
 * @throws {TypeError} Когда в аргументы переданы не числа
 * @returns {Number} Сумма аргументов
 */
function abProblem(a, b) {
    // Ваше решение
    if (!Number.isInteger(a) || !Number.isInteger(b)) {
        throw new TypeError("NaN");
    }
    return a + b;
}

/**
 * Определяет век по году
 * @param {Number} year Год, целое положительное число
 * @throws {TypeError} Когда в качестве года передано не число
 * @throws {RangeError} Когда год – отрицательное значение
 * @returns {Number} Век, полученный из года
 */
function centuryByYearProblem(year) {
    // Ваше решение
    if (!Number.isInteger(year)) {
        throw new TypeError("NaN");
    }
    if (year < 0) {
        throw new RangeError("Negate");
    }
    return Math.ceil(year / 100);
}

/**
 * Переводит цвет из формата HEX в формат RGB
 * @param {String} hexColor Цвет в формате HEX, например, '#FFFFFF'
 * @throws {TypeError} Когда цвет передан не строкой
 * @throws {RangeError} Когда значения цвета выходят за пределы допустимых
 * @returns {String} Цвет в формате RGB, например, '(255, 255, 255)'
 */
function colorsProblem(hexColor) {
    // Ваше решение
    if (typeof hexColor !== 'string') {
        throw new TypeError("Not a string");
    }
    if (!hexColor.match(/^#([0-9A-F]{3}){1,2}$/i)) {
        throw new RangeError("RE");
    }
    const d = hexColor.length === 7 ? 0 : 1;
    const [r, g, b] = [
        parseInt(hexColor.slice(1,3 - d).repeat(d + 1), 16),
        parseInt(hexColor.slice(3 - d,5 - 2 * d).repeat(d + 1), 16),
        parseInt(hexColor.slice(5 - 2 * d).repeat(d + 1), 16)
    ];
    return `(${r}, ${g}, ${b})`
}


/**
 * Находит n-ое число Фибоначчи
 * @param {Number} n Положение числа в ряде Фибоначчи
 * @throws {TypeError} Когда в качестве положения в ряде передано не число
 * @throws {RangeError} Когда положение в ряде не является целым положительным числом
 * @returns {Number} Число Фибоначчи, находящееся на n-ой позиции
 */
function fibonacciProblem(n) {
    // Ваше решение
    if (typeof n !== 'number' || isNaN(n)) {
        throw new TypeError("NaN");
    }
    if (!Number.isInteger(n) || n <= 0) {
        throw new RangeError("RE");
    }
    let [a, b] = [1, 1];
    while (--n) {
        [a, b] = [b, a + b];
    }
    return a;
}

/**
 * Транспонирует матрицу
 * @param {(Any[])[]} matrix Матрица размерности MxN
 * @throws {TypeError} Когда в функцию передаётся не двумерный массив
 * @returns {(Any[])[]} Транспонированная матрица размера NxM
 */
function matrixProblem(matrix) {
    // Ваше решение
    if (!Array.isArray(matrix) || matrix.some(el => !Array.isArray(el))) {
        throw new TypeError("Is not matrix");
    }
    return Array.from({length: matrix[0].length},
        (_, i) => Array.from({length: matrix.length},
            (_, j) => matrix[j][i]))
}

/**
 * Переводит число в другую систему счисления
 * @param {Number} n Число для перевода в другую систему счисления
 * @param {Number} targetNs Система счисления, в которую нужно перевести (Число от 2 до 36)
 * @throws {TypeError} Когда переданы аргументы некорректного типа
 * @throws {RangeError} Когда система счисления выходит за пределы значений [2, 36]
 * @returns {String} Число n в системе счисления targetNs
 */
function numberSystemProblem(n, targetNs) {
    // Ваше решение
    if (typeof n !== 'number' || isNaN(n) || !isFinite(n) || !Number.isInteger(targetNs)) {
        throw new TypeError('TE');
    }
    if (targetNs < 2 || targetNs > 36) {
        throw new RangeError('TE');
    }
    return n.toString(targetNs);
}

/**
 * Проверяет соответствие телефонного номера формату
 * @param {String} phoneNumber Номер телефона в формате '8–800–xxx–xx–xx'
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Boolean} Если соответствует формату, то true, а иначе false
 */
function phoneProblem(phoneNumber) {
    // Ваше решение
    if (typeof phoneNumber !== 'string') {
        throw new TypeError('Not a string');
    }
    return !!phoneNumber.match(/^8-800-\d\d\d-\d\d-\d\d$/);
}

/**
 * Определяет количество улыбающихся смайликов в строке
 * @param {String} text Строка в которой производится поиск
 * @throws {TypeError} Когда в качестве аргумента передаётся не строка
 * @returns {Number} Количество улыбающихся смайликов в строке
 */
function smilesProblem(text) {
    // Ваше решение
    if (typeof text !== 'string') {
        throw new TypeError('Not a string');
    }
    return (text.match(/(:-\))|(\(-:)/g) || []).length;
}

/**
 * Определяет победителя в игре "Крестики-нолики"
 * Тестами гарантируются корректные аргументы.
 * @param {(('x' | 'o')[])[]} field Игровое поле 3x3 завершённой игры
 * @returns {'x' | 'o' | 'draw'} Результат игры
 */
function ticTacToeProblem(field) {
    // Ваше решение
    const [inRow, inCol] = [[0, 0, 0], [0, 0, 0]];
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (field[i][j] === 'x') {
                inRow[i]++;
                inCol[j]++;
            }
        }
    }
    const inDiag1 = field.reduce((acc, _, ind) => {
        if (field[ind][ind] === 'x') acc++;
        return acc;
    }, 0)
    const inDiag2 = field.reduce((acc, _, ind) => {
        if (field[ind][2 - ind] === 'x') acc++;
        return acc;
    }, 0)
    if (inRow.some(el => el == 3) || inCol.some(el => el === 3)
        || inDiag1 === 3 || inDiag2 === 3) {
        return 'x';
    }
    if (inRow.some(el => el == 0) || inCol.some(el => el === 0)
        || inDiag1 === 0 || inDiag2 === 0) {
        return 'o';
    }
    return 'draw';
}

module.exports = {
    abProblem,
    centuryByYearProblem,
    colorsProblem,
    fibonacciProblem,
    matrixProblem,
    numberSystemProblem,
    phoneProblem,
    smilesProblem,
    ticTacToeProblem
};