const vars = ['1', '2', '3'];
const binary = ['+', '-', '*', '/'];
const not = (expr: string) => `-${expr}`
const brackets = (expr: string) => `(${expr})`

export const generateExpressions = () => {
    const tests: string[] = [];
    vars.forEach((v) => {
        tests.push(v);
        tests.push(not(v));
        tests.push(not(brackets(not(v))));
        tests.push(brackets(not(v)));
        tests.push(brackets(brackets(v)));
        tests.push(not(brackets(v)));
        binary.forEach((bin) => {
            tests.push(`${v} ${bin} ${v}`);
            tests.push(brackets(`${v} ${bin} ${v}`));
            tests.push(`${brackets(`${v} ${bin} ${v}`)} ${bin} ${v}`);
            tests.push(`${brackets(`${v} ${bin} ${v}`)} ${bin} ${brackets(`${v} ${bin} ${v}`)}`);

            tests.push(not(`${v} ${bin} ${v}`));
            tests.push(not(brackets(`${not(v)} ${bin} ${not(v)}`)));
            tests.push(not(`${brackets(`${not(v)} ${bin} ${not(v)}`)} ${bin} ${not(v)}`));
            tests.push(not(`${brackets(`${not(v)} ${bin} ${not(v)}`)} ${bin} ${brackets(`${not(v)} ${bin} ${not(v)}`)}`));
        })
        tests.push('(1 + 2) - (2 * (3 - -2))');
        tests.push('1 + 2 * 3');
        tests.push('1 / 2 * 3');
        tests.push('1 * 2 - 3');
    })
    return tests;
}
