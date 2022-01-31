'use strict';

/**
 * Телефонная книга
 */
const phoneBook = new Map();

const tokens = {
    // ДОБАВЬ
    add: 'Добавь ',
    phone: 'телефон ',
    mail: 'почту ',

    // ПОКАЖИ
    show: 'Покажи ',
    name: 'имя ',
    mails: 'почты ',
    phones: 'телефоны ',

    // СОЗДАЙ
    create: 'Создай ',

    // УДАЛИ
    delete: 'Удали ',
    contact: 'контакт ',
    contacts: 'контакты, ',

    for: 'для ',
    forContact: 'контакта ',
    forContacts: 'контактов, ',

    where: 'где ',
    has: 'есть ',
}

/**
 * Вызывайте эту функцию, если есть синтаксическая ошибка в запросе
 * @param {number} lineNumber – номер строки с ошибкой
 * @param {number} charNumber – номер символа, с которого запрос стал ошибочным
 */
function syntaxError(lineNumber, charNumber) {
    throw new Error(`SyntaxError: Unexpected token at ${lineNumber}:${charNumber}`);
}

/**
 * Выполнение запроса на языке pbQL
 * @param {string} queries
 * @returns {string[]} - строки с результатами запроса
 */
function run(queries) {
    if (typeof queries !== 'string' || queries.length === 0) {
        syntaxError(1, 1);
    }
    const res = [];
    let pos = 0, line = 0, query = '';
    while (pos < queries.length) {
        if (queries[pos] === ';') {
            line++;
            if (!query) {
                syntaxError(line, 1);
            }
            const result = processQuery(query, line);
            res.push(...(result || []));
            query = '';
            pos++;
        } else {
            query += queries[pos++];
        }
    }
    if (query) {
        processQuery(query, line + 1);
        syntaxError(line + 1, query.length + 1);
    }
    return res;
}

function processQuery(query, line) {
    switch (query[0]) {
        case 'С':
            return parseCreate(query, line, 1);
        case 'У':
            return parseDelete(query, line, 1);
        case 'Д':
            return parseAdd(query, line, 1);
        case 'П':
            return parseShow(query, line, 1);
        default:
            syntaxError(line, 1);
    }
}

function parseCreate(query, line, pos) {
    validateStart(query, line, tokens.create, pos);
    query = query.replace(tokens.create, '');
    pos += tokens.create.length;
    validateStart(query, line, tokens.contact, pos);
    pos += tokens.contact.length;
    const name = query.replace(tokens.contact, '');
    if (name.includes('  ')) {
        syntaxError(line, pos + name.indexOf('  ') + 1);
    }
    createContact(name);
}

function parseDelete(query, line, pos) {
    validateStart(query, line, tokens.delete, pos);
    pos += tokens.delete.length;
    const next = query.replace(tokens.delete, '');
    switch (next[0]) {
        case 'к':
            return parseDeleteContact(next, line, pos);
        case 'т':
        case 'п':
            return parseDeleteOrAddMailsAndPhones(next, line, pos, deletePhones, deleteMails);
        default:
            syntaxError(line, pos);
    }
}

function parseDeleteContact(query, line, pos) {
    if (query.startsWith(tokens.contacts)) {
        validateStart(query, line, tokens.contacts, pos);
        pos += tokens.contacts.length;
        query = query.replace(tokens.contacts, '');
        validateStart(query, line, tokens.where, pos);
        pos += tokens.where.length;
        query = query.replace(tokens.where, '');
        validateStart(query, line, tokens.has, pos);
        deleteContacts(query.replace(tokens.has, ''));
    } else {
        validateStart(query, line, tokens.contact, pos);
        deleteContact(query.replace(tokens.contact, ''));
    }
}

function parseDeleteOrAddMailsAndPhones(query, line, pos, cbPhones, cbMails) {
    const phones = [], mails = [];
    let [next, posNext] = parsePhoneOrMailQuery(query, line, pos, phones, mails);
    while (next.startsWith('и ')) {
        [next, posNext] = parsePhoneOrMailQuery(next.slice(2), line, posNext + 2, phones, mails);
    }
    validateStart(next, line, tokens.for, posNext);
    posNext += tokens.for.length;
    next = next.replace(tokens.for, '')
    validateStart(next, line, tokens.forContact, posNext);
    const name = next.replace(tokens.forContact, '');
    posNext += tokens.forContact.length;
    if (name.includes('  ')) {
        syntaxError(line, posNext + name.indexOf('  ') + 1);
    }
    cbPhones(name, ...phones);
    cbMails(name, ...mails);
}

function parsePhoneOrMailQuery(query, line, pos, phones, mails) {
    if (query[0] === 'т') {
        validateStart(query, line, tokens.phone, pos);
        const next = query.replace(tokens.phone, '');
        pos += tokens.phone.length;
        let cur = 0, phone = '';
        while (cur < next.length && next[cur] !== ' ') {
            if (!next[cur].match(/[0-9]/) || cur > 9) {
                syntaxError(line, pos);
            }
            phone += next[cur];
            cur++;
        }
        if (cur < 10) {
            syntaxError(line, pos);
        }
        phones.push(phone);
        return [next.replace(phone + ' ', ''), pos + cur + 1];
    }
    if (query[0] === 'п') {
        validateStart(query, line, tokens.mail, pos);
        const next = query.replace(tokens.mail, '');
        pos += tokens.mail.length;
        let cur = 0, mail = '';
        while (cur < next.length && next[cur] !== ' ') {
            mail += next[cur];
            cur++;
        }
        if (!mail) {
            syntaxError(line, pos)
        }
        mails.push(mail);
        return [next.replace(mail + ' ', ''), pos + cur + 1];
    }
    syntaxError(line, pos);
    //return [query, pos];
}

function parseAdd(query, line, pos) {
    validateStart(query, line, tokens.add, pos);
    return parseDeleteOrAddMailsAndPhones(query.replace(tokens.add, ''), line, pos + tokens.add.length, addPhones, addMails);
}

function getRequests(query, line, pos) {
    const requests = [];
    let [next, posNext] = parseNameOrPhonesOrMails(query, line, pos, requests);

    while (next.startsWith('и ')) {
        [next, posNext] = parseNameOrPhonesOrMails(next.slice(2), line, posNext + 2, requests);
    }

    validateStart(next, line, tokens.for, posNext);
    posNext += tokens.for.length;
    next = next.replace(tokens.for, '');

    validateStart(next, line, tokens.forContacts, posNext);
    posNext += tokens.forContacts.length;
    next = next.replace(tokens.forContacts, '');

    validateStart(next, line, tokens.where, posNext);
    posNext += tokens.where.length;
    next = next.replace(tokens.where, '');

    validateStart(next, line, tokens.has, posNext);
    return [requests, next.replace(tokens.has, '')];
}

function parseNameOrPhonesOrMails(query, line, pos, requests) {
    switch (query[0]) {
        case 'и':
            validateStart(query, line, tokens.name, pos);
            requests.push('name');
            return [query.replace(tokens.name, ''), pos + tokens.name.length];
        case 'т':
            validateStart(query, line, tokens.phones, pos);
            requests.push('phones');
            return [query.replace(tokens.phones, ''), pos + tokens.phones.length];
        case 'п':
            validateStart(query, line, tokens.mails, pos);
            requests.push('mails');
            return [query.replace(tokens.mails, ''), pos + tokens.mails.length];
        default:
            syntaxError(line, pos);
            //return [query, pos];
    }
}

function parseShow(query, line, pos) {
    validateStart(query, line, tokens.show, pos);
    const [requests, by] = getRequests(query.replace(tokens.show, ''), line, pos + tokens.show.length);
    if (by) {
        return showBy(requests, by);
    }
}

function showBy(requests, by) {
    const response = [];
    for (const [name, info] of phoneBook) {
        if (isHaveSome(name, info, by)) {
            const found = {
                name,
                phones: getIncluded(info.phones, true),
                mails: getIncluded(info.mails,false),
            }
            response.push(requests.reduce((acc, el) => [...acc, found[el]], []).join(';'));
        }
    }
    return response;
}

function getIncluded(field, isPhone) {
    return (
        isPhone
            ?
            Array.from(field).map(el => parsePhone(el)).join(',')
            :
            Array.from(field)
    );
}

function validateStart(query, line, token, pos) {
    Array.from(token).forEach((el, ind, arr) => {
        if (query[ind] === ' ' && ind && query[ind - 1] === ' ') {
            syntaxError(line, pos + ind);
        }
        if (query[ind] !== arr[ind]) {
            syntaxError(line, pos);
        }
    })
}

function createContact(name) {
    if (!phoneBook.has(name)) {
        phoneBook.set(name, {
            phones: new Set(),
            mails: new Set(),
        });
    }
}

function deleteContacts(by) {
    if (by) {
        for (const [name, info] of phoneBook) {
            if (isHaveSome(name, info, by)) {
                phoneBook.delete(name);
            }
        }
    }
}

function isHaveSome(name, info, by) {
    return (
        name.includes(by) ||
        Array.from(info.phones).some(el => el.includes(by)) ||
        Array.from(info.mails).some(el => el.includes(by))
    )
}

function deleteContact(name) {
    phoneBook.delete(name);
}

function addPhones(name, ...phones) {
    if (phoneBook.has(name)) {
        const backet = phoneBook.get(name).phones;
        backet && phones.forEach(phone => backet.add(phone));
    }
}

function addMails(name, ...mails) {
    if (phoneBook.has(name)) {
        const backet = phoneBook.get(name).mails;
        backet && mails.forEach(mail => backet.add(mail));
    }
}

function deletePhones(name, ...phones) {
    if (phoneBook.has(name)) {
        const backet = phoneBook.get(name).phones;
        backet && phones.forEach(phone => backet.delete(phone));
    }
}

function deleteMails(name, ...mails) {
    if (phoneBook.has(name)) {
        const backet = phoneBook.get(name).mails;
        backet && mails.forEach(mail => backet.delete(mail));
    }
}

function parsePhone(phone) {
    return `+7 (${phone.slice(0, 3)}) ${phone.slice(3, 6)}-${phone.slice(6, 8)}-${phone.slice(8, 10)}`;
}


// Пример 1
//console.log(run('Покажи имя для контактов, где есть ий;'));
/*
    []
*/

// Пример 2
//console.log(run('Создай контакт Григорий;Создай контакт Василий;Создай контакт Иннокентий;Покажи имя для контактов, где есть ий;'));
/*
    [
        'Григорий',
        'Василий',
        'Иннокентий'
    ]
*/

// Пример 3
//console.log(run('Создай контакт Григорий;Создай контакт Василий;Создай контакт Иннокентий;Покажи имя и имя и имя для контактов, где есть ий;'));
/*
    [
        'Григорий;Григорий;Григорий',
        'Василий;Василий;Василий',
        'Иннокентий;Иннокентий;Иннокентий'
    ]
*/

// Пример 4
//console.log(run('Создай контакт Григорий;Покажи имя для контактов, где есть ий;Покажи имя для контактов, где есть ий;'));
/*
    [
        'Григорий',
        'Григорий'
    ]
*/

// Пример 5
//console.log(run('Создай контакт Григорий;Удали контакт Григорий;Покажи имя для контактов, где есть ий;'));
/*
    []
*/

// Пример 6
//console.log(run('Создай контакт Григорий;Добавь телефон 5556667787 для контакта Григорий;Добавь телефон 5556667788 и почту grisha@example.com для контакта Григорий;Покажи имя и телефоны и почты для контактов, где есть ий;'));
/*
    [
        'Григорий;+7 (555) 666-77-87,+7 (555) 666-77-88;grisha@example.com'
    ]
*/

// Пример 7
//console.log(run('Создай контакт Григорий;Добавь телефон 5556667788 для контакта Григорий;Удали телефон 5556667788 для контакта Григорий;Покажи имя и телефоны для контактов, где есть ий;'));
/*
    [
        'Григорий;'
    ]
*/

module.exports = {phoneBook, run};