"use strict";

const cnst = value => () => value;
const variable = value => (x, y, z) =>
	(value === "x") ? x:
	(value === "y") ? y:
	(value === "z") ? z:
	console.log("Wrong variable");

let unaryOperation = value => val => (...args) => value.call(null, val(...args));
let binOperation = value => (left, right) => (...args) => value.call(null, left(...args), right(...args));

const add = binOperation((left, right) => left + right);
const subtract = binOperation((left, right) => left - right);
const multiply = binOperation((left, right) => left * right);
const divide = binOperation((left, right) => left / right);

const negate = unaryOperation(value => -value);
const cube = unaryOperation(value => Math.pow(value, 3));
const cuberoot = unaryOperation(value => Math.cbrt(value));