"use strict";

function Const(value) {
    this.value = value;
}

Const.prototype.evaluate = function () {
    return this.value;
};
Const.prototype.toString = function () {
    return String(this.value);
};
Const.prototype.prefix = function () {
    return String(this.value);
};

function Variable(value) {
    this.value = value;
}

Variable.prototype.evaluate = function (x, y, z) {
    return (this.value === "x") ? x :
        (this.value === "y") ? y :
            (this.value === "z") ? z :
                "Unexpected variable"
};

Variable.prototype.toString = function () {
    return this.value;
};

Variable.prototype.prefix = function () {
    return this.value;
};


function AbstractFunction(operation, numberOfOperands, ...args) {
    this.operation = operation;
    this.operands = args;
    this.numberOfOperands = numberOfOperands;

}

AbstractFunction.prototype.evaluate = function (...args) {
    let calc = [];
    for (const i in this.operands) {
        calc.push(this.operands[i].evaluate(...args))
    }
    return this.calculate(...calc);
};

AbstractFunction.prototype.toString = function () {
    return this.operands.join(" ") + " " + this.operation;
};

AbstractFunction.prototype.prefix = function () {
    let expressionToStr = "(" + this.operation;
    for (let i = 0; i < this.operands.length; i++) {
        expressionToStr += " " + this.operands[i].prefix();
    }
    expressionToStr += ")";
    return expressionToStr;
};

const makeNewOperation = function (calculate, operation, numberOfOperands) {
    const op = function (...operands) {
        AbstractFunction.call(this, operation, numberOfOperands, ...operands);
    };
    op.prototype = new AbstractFunction();
    op.prototype.calculate = calculate;
    return op;
};


const Add = new makeNewOperation(
    (first, second) => first + second,
    "+",
    2
);

const Subtract = new makeNewOperation(
    (first, second) => first - second,
    "-",
    2
);

const Multiply = new makeNewOperation(
    (first, second) => first * second,
    "*",
    2
);

const Divide = new makeNewOperation(
    (first, second) => first / second,
    "/",
    2
);

const Negate = new makeNewOperation(
    (value) => -value,
    "negate",
    1
);

const Min3 = new makeNewOperation(
    (...args) => Math.min(...args),
    "min3",
    3
);

const Max5 = new makeNewOperation(
    (...args) => Math.max(...args),
    "max5",
    5
);

const ArcTan = new makeNewOperation(
    (value) => Math.atan(value),
    "atan",
    1
);

const Exp = new makeNewOperation(
    (value) => Math.exp(value),
    "exp",
    1
);

const OPERATORS = {
    "+": (...args) => new Add(...args),
    "-": (...args) => new Subtract(...args),
    "*": (...args) => new Multiply(...args),
    "/": (...args) => new Divide(...args),
    "negate": (...args) => new Negate(...args),
    "min3": (...args) => new Min3(...args),
    "max5": (...args) => new Max5(...args),
    "atan": (...args) => new ArcTan(...args),
    "exp": (...args) => new Exp(...args),
};

//HARD CRASH ON 200+ TEST

function parse(expression) {
    const stack = [];
    const parsed = [];
    let pos = 0;
    while (pos < expression.length) {
        let value = "";
        while (pos < expression.length && expression[pos] !== " ")
            value += expression[pos++];
        if (value)
            stack.push(value);
        pos++;
    }
    for (let i = 0; i < stack.length; i++) {
        if (stack[i] in OPERATORS) {
            let ops = [];
            let len = OPERATORS[stack[i]]().numberOfOperands;
            for (let x = 0; x < len; x++)
                ops.push(parsed.pop());
            parsed.push(OPERATORS[stack[i]](...ops.reverse()))
        } else {
            if (["x", "y", "z"].includes(stack[i]))
                parsed.push(new Variable(stack[i]));
            else
                parsed.push(new Const(Number(stack[i])));
        }
    }
    return parsed.pop();
}

function parsePrefix(expression) {
    const END = expression.length;
    let pos, opener, closer;
    [pos, opener, closer] = [0, 0, 0];

    const nextValue = function () {
        skipWS();
        let value = "";
        while (pos < END && expression[pos] !== " " && expression[pos] !== ")") {
            value += expression[pos++];
            if (value === "(") {
                opener++;
                value = parseBrackets();
            }
            let parsed = parsedValue(value, !expression[pos] || expression[pos] === "(");
            if (parsed) return parsed;
        }
        skipWS();
        if (!value)
            throw new LessArguments("Missed value in " + expression, pos);
        let parsed = parsedValue(value, 1);
        if (parsed) return parsed;
        throw new BadSymbol("Bad symbol find in " + expression, pos);
    };

    const parsedValue = function (value, condition) {
        if (["x", "y", "z"].includes(value) && condition)
            return new Variable(value);
        else if (!isNaN(Number(value)) && condition)
            return new Const(Number(value));
        else if (AbstractFunction.prototype.isPrototypeOf(value))
            return value;
        else if (value in OPERATORS && condition)
            return parseOperation(value);
        return "";
    };

    const parseOperation = function (operation) {
        const values = [];
        for (let i = 0; i < OPERATORS[operation]().numberOfOperands; i++) {
            const operand = nextValue();
            if (operand === ")") {
                throw new LessArguments("Less arguments in " + expression, pos);
            }
            if (operand === "(") {
                opener++;
                values.push(parseBrackets());
            }
            values.push(operand);
        }
        return OPERATORS[operation](...values);
    };

    const parseBrackets = function () {
        skipWS();
        const expr = nextValue();
        skipWS();
        if (!AbstractFunction.prototype.isPrototypeOf(expr))
            throw new MissOperation("No operation in brackets", pos);
        if (expression[pos++] !== ")")
            throw new LessBrackets("Less close bracket in " + expression, pos);
        closer++;
        return expr;
    };

    const skipWS = function () {
        while (pos < END && expression[pos] === " ") pos++;
    };

    const solved = nextValue();
    if (opener !== closer)
        throw new ExpressionException("Less brackets in " + expression, pos);
    skipWS();
    if (pos !== END)
        throw new IllegalExpression("Illegal expression");
    return solved;
}

//''''''''''''''''''''ERRORS'''''''''''''''''''''

function ExpressionException(name, message) {
    Error.call(this, message);
    this.name = name;
    this.message = message;
}

ExpressionException.prototype = Object.create(Error.prototype);

const createException = function (name) {
    const exc = function (message, pos) {
        ExpressionException.call(this, name, message + ((pos) ? " in position " + pos : ""));
    };
    exc.prototype = new ExpressionException();
    return exc;
};

const BadSymbol = new createException(
    "BadSymbol",
);

const LessBrackets = new createException(
    "LessBrackets",
);

const IllegalExpression = new createException(
    "IllegalExpression",
);

const LessArguments = new createException(
    "LessArguments",
);

const MissOperation = new createException(
    "MissOperation",
);
