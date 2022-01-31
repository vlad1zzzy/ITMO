'use strict';

/**
 * @param {Object} schedule Расписание Банды
 * @param {number} duration Время на ограбление в минутах
 * @param {Object} workingHours Время работы банка
 * @param {string} workingHours.from Время открытия, например, "10:00+5"
 * @param {string} workingHours.to Время закрытия, например, "18:00+5"
 * @returns {Object}
 */
function getAppropriateMoment(schedule, duration, workingHours) {
    const WEEKDAY = 1440;
    const weekDays = ['ПН', 'ВТ', 'СР']
    const weekDaysENG = ['M', 'T', 'W']
    const timezone = +workingHours.from.slice(workingHours.from.indexOf('+'));
    let events = [];

    const times = {
        'M': 0,
        'T': WEEKDAY,
        'W': WEEKDAY * 2,
    }

    function getDateName(date) {
        return weekDaysENG[weekDays.indexOf(date.slice(0, 2))];
    }

    function processHours(date) {
        let minutes = +date.slice(0, 2) * 60 + +date.slice(3, 5);
        if (date.includes('+')) {
            minutes -= +date.slice(date.indexOf('+')) * 60;
        }
        return minutes
    }

    function fromMinutes(minutes) {
        let day = 0;
        let hours = Math.floor(minutes / 60);
        const mins = minutes - hours * 60;
        while (hours >= 24) {
            hours -= 24;
            day++;
        }
        const supportMins = String(mins).length === 1 ? 0 : '';
        const supportHours = String(hours).length === 1 ? 0 : '';
        //return `${weekDays[day]} ${supportHours}${hours}:${supportMins}${mins}`;
        return {
            day: weekDays[day],
            hours: `${supportHours}${hours}`,
            mins: `${supportMins}${mins}`,
        }
    }

    function addRobberEvent(events, newEvent) {
        const newEvents = [];
        events.forEach(event => {
            if (newEvent.from >= event.from && newEvent.to <= event.to) {
                // (   []   )
                newEvents.push({
                        from: event.from,
                        to: newEvent.from
                    }, {
                        from: newEvent.to,
                        to: event.to
                    }
                );
            } else if (newEvent.from <= event.from && newEvent.to >= event.from && newEvent.to <= event.to) {
                // [  (  ]  )
                newEvents.push({
                    from: newEvent.to,
                    to: event.to,
                });
            } else if (newEvent.from >= event.from && newEvent.from <= event.to && newEvent.to >= event.to) {
                // (  [  )  ]
                newEvents.push({
                    from: event.from,
                    to: newEvent.from,
                });
            } else if (newEvent.from <= event.from && newEvent.to >= event.to) {
                // [  (  )  ]
            } else {
                // [  ]  (  )  |  (  )  [  ]
                newEvents.push(event);
            }
        })

        return newEvents.sort((e1, e2) => e1.from - e2.from);
    }

    for (let i = 0; i < 3; i++) {
        events.push({
            from: processHours(workingHours.from) + i * WEEKDAY,
            to: processHours(workingHours.to) + i * WEEKDAY,
        });
    }

    Object.values(schedule).forEach((robber) => {
        robber.forEach(({from, to}) => {
            events = addRobberEvent(events, {
                from: times[getDateName(from)] + processHours(from.slice(3)),
                to: times[getDateName(to)] + processHours(to.slice(3)),
            })
        })
    });

    events = events.filter(event => event.to - event.from >= duration);

    return {
        /**
         * Найдено ли время
         * @returns {boolean}
         */
        exists() {
            return !!events.length;
        },

        /**
         * Возвращает отформатированную строку с часами
         * для ограбления во временной зоне банка
         *
         * @param {string} template
         * @returns {string}
         *
         * @example
         * ```js
         * getAppropriateMoment(...).format('Начинаем в %HH:%MM (%DD)') // => Начинаем в 14:59 (СР)
         * ```
         */
        format(template) {
            if (!this.exists()) {
                return '';
            }
            const start = fromMinutes(events[0].from + timezone * 60);

            return template
                .replace(/%DD/g, start.day)
                .replace(/%HH/g, start.hours)
                .replace(/%MM/g, start.mins);
        },

        /**
         * Попробовать найти часы для ограбления позже [*]
         * @note Не забудь при реализации выставить флаг `isExtraTaskSolved`
         * @returns {boolean}
         */
        tryLater() {
            if (!this.exists()) {
                return false;
            }

            let newEvents = addRobberEvent(events, {
                from: events[0].from,
                to: events[0].from + 30
            });
            newEvents = newEvents.filter(event => event.to - event.from >= duration);

            return !!newEvents.length && !!(events = newEvents);
        }
    };
}

const gangSchedule = {
    Danny: [{from: 'ПН 12:00+5', to: 'ПН 17:00+5'}, {from: 'ВТ 13:00+5', to: 'ВТ 16:00+5'}],
    Rusty: [{from: 'ПН 11:30+5', to: 'ПН 16:30+5'}, {from: 'ВТ 13:00+5', to: 'ВТ 16:00+5'}],
    Linus: [
        {from: 'ПН 09:00+3', to: 'ПН 14:00+3'},
        {from: 'ПН 21:00+3', to: 'ВТ 09:30+3'},
        {from: 'СР 09:30+3', to: 'СР 15:00+3'}
    ]
};

const bankWorkingHours = {
    from: '10:00+5',
    to: '18:00+5'
};

console.log(getAppropriateMoment(gangSchedule, 121, bankWorkingHours).exists());
console.log(getAppropriateMoment(gangSchedule, 121, bankWorkingHours).format('Метим на %DD, старт в %HH:%MM!'));


// Время не существует
const longMoment = getAppropriateMoment(gangSchedule, 121, bankWorkingHours);

// Выведется `false` и `""`
console.info(longMoment.exists());
console.info(longMoment.format('Метим на %DD, старт в %HH:%MM!'));

// Время существует
const moment = getAppropriateMoment(gangSchedule, 90, bankWorkingHours);

// Выведется `true` и `"Метим на ВТ, старт в 11:30!"`
console.info(moment.exists());
console.info(moment.format('Метим на %DD, старт в %HH:%MM!'));

// Дополнительное задание
// Вернет `true`
console.log(moment.tryLater());
// `"ВТ 16:00"`
console.info(moment.format('%DD %HH:%MM'));

// Вернет `true`
console.log(moment.tryLater());
// `"ВТ 16:30"`
console.info(moment.format('%DD %HH:%MM'));

// Вернет `true`
console.log(moment.tryLater());
// `"СР 10:00"`
console.info(moment.format('%DD %HH:%MM'));

// Вернет `false`
console.log(moment.tryLater());
// `"СР 10:00"`
console.info(moment.format('%DD %HH:%MM'));

module.exports = {
    getAppropriateMoment
};
