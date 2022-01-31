'use strict';

/**
 * Итератор по друзьям
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 * @param {Number} level
 */
function Iterator(friends, {isAppropriate}, level = Infinity) {
    const sorted = (arr) => arr.sort(({name: n1}, {name: n2}) => n1.localeCompare(n2));
    this.position = 0;
    const result = [];
    const setOfFriends = new Set();
    let guests = sorted(friends.filter(({best}) => best));

    while (guests.length > 0 && level > 0) {
        level--;
        const friendsOfGuests = new Set();
        result.push(...guests);
        guests.forEach(({name, friends}) => {
            setOfFriends.add(name);
            friends.forEach(f => friendsOfGuests.add(f));
        });
        guests = sorted(friends.filter(({name}) => !setOfFriends.has(name) && friendsOfGuests.has(name)));
    }
    this.guests = result.filter(isAppropriate);
}

Iterator.prototype.done = function () {
    return this.position >= this.guests.length;
};

Iterator.prototype.next = function () {
    return this.done() ? null : this.guests[this.position++];
};

/**
 * Итератор по друзям с ограничением по кругу
 * @extends Iterator
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 * @param {Number} maxLevel – максимальный круг друзей
 */
const LimitedIterator = Iterator;

/**
 * Фильтр друзей
 * @constructor
 */
function Filter() {}
Filter.prototype.isAppropriate = f => f;

/**
 * Фильтр друзей
 * @extends Filter
 * @constructor
 */
function MaleFilter() {}
MaleFilter.prototype = Object.create(Filter.prototype, {
    constructor: {value: MaleFilter},
    isAppropriate: {value: ({gender}) => gender === 'male'}
});

/**
 * Фильтр друзей-девушек
 * @extends Filter
 * @constructor
 */
function FemaleFilter() {}
FemaleFilter.prototype = Object.create(Filter.prototype, {
    constructor: {value: FemaleFilter},
    isAppropriate: {value: ({gender}) => gender === 'female'}
});

// const friends = [
//     {
//         name: 'Sam',
//         friends: ['Mat', 'Sharon'],
//         gender: 'male',
//         best: true
//     },
//     {
//         name: 'Sally',
//         friends: ['Brad', 'Emily'],
//         gender: 'female',
//         best: true
//     },
//     {
//         name: 'Mat',
//         friends: ['Sam', 'Sharon'],
//         gender: 'male'
//     },
//     {
//         name: 'Sharon',
//         friends: ['Sam', 'Itan', 'Mat'],
//         gender: 'female'
//     },
//     {
//         name: 'Brad',
//         friends: ['Sally', 'Emily', 'Julia'],
//         gender: 'male'
//     },
//     {
//         name: 'Emily',
//         friends: ['Sally', 'Brad'],
//         gender: 'female'
//     },
//     {
//         name: 'Itan',
//         friends: ['Sharon', 'Julia'],
//         gender: 'male'
//     },
//     {
//         name: 'Julia',
//         friends: ['Brad', 'Itan'],
//         gender: 'female'
//     }
// ];
//
// function friend(name) {
//     let len = friends.length;
//
//     while (len--) {
//         if (friends[len].name === name) {
//             return friends[len];
//         }
//     }
// }
//
// const maleFilter = new MaleFilter();
// const femaleFilter = new FemaleFilter();
// const maleIterator = new LimitedIterator(friends, maleFilter, 2);
// const femaleIterator = new Iterator(friends, femaleFilter);
//
// const invitedFriends = [];
//
// while (!maleIterator.done() && !femaleIterator.done()) {
//     invitedFriends.push([
//         maleIterator.next(),
//         femaleIterator.next()
//     ]);
// }
//
// while (!femaleIterator.done()) {
//     invitedFriends.push(femaleIterator.next());
// }
//
// const res = [
//     [friend('Sam'), friend('Sally')],
//     [friend('Brad'), friend('Emily')],
//     [friend('Mat'), friend('Sharon')],
//     friend('Julia')
// ];
//
// var deepEqual = function (x, y) {
//     if (x === y) {
//         return true;
//     } else if ((typeof x == "object" && x != null) && (typeof y == "object" && y != null)) {
//         if (Object.keys(x).length != Object.keys(y).length)
//             return false;
//
//         for (var prop in x) {
//             if (y.hasOwnProperty(prop)) {
//                 if (!deepEqual(x[prop], y[prop]))
//                     return false;
//             } else
//                 return false;
//         }
//
//         return true;
//     } else
//         return false;
// }
//
// console.log(invitedFriends.every((el, ind) => {
//     return deepEqual(el, res[ind]);
// }))

exports.Iterator = Iterator;
exports.LimitedIterator = LimitedIterator;

exports.Filter = Filter;
exports.MaleFilter = MaleFilter;
exports.FemaleFilter = FemaleFilter;