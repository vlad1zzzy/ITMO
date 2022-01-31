'use strict';

const fetch = require('node-fetch');

const API_KEY = require('./key.json');

/**
 * @typedef {object} TripItem Город, который является частью маршрута.
 * @property {number} geoid Идентификатор города
 * @property {number} day Порядковое число дня маршрута
 */

class TripBuilder {
  trip = [];
  cityTimer = Number.MAX_VALUE;

  static DAY_TYPES = {
    SUNNY: 'SUNNY',
    CLOUDY: 'CLOUDY',
  }

  static MAP_DAY_TYPES = {
    clear: TripBuilder.DAY_TYPES.SUNNY,
    'partly-cloudy': TripBuilder.DAY_TYPES.SUNNY,
    cloudy: TripBuilder.DAY_TYPES.CLOUDY,
    overcast: TripBuilder.DAY_TYPES.CLOUDY,
  }

  constructor(geoids) {
    this.geoids = geoids;
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества солнечных дней
   * Согласно API Яндекс.Погоды, к солнечным дням
   * можно приравнять следующие значения `condition`:
   * * `clear`;
   * * `partly-cloudy`.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  sunny(daysCount) {
    return this.weather(TripBuilder.DAY_TYPES.SUNNY, daysCount);
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества пасмурных дней
   * Согласно API Яндекс.Погоды, к солнечным дням
   * можно приравнять следующие значения `condition`:
   * * `cloudy`;
   * * `overcast`.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  cloudy(daysCount) {
    return this.weather(TripBuilder.DAY_TYPES.CLOUDY, daysCount);
  }

  /**
   * Метод, добавляющий условие наличия в маршруте
   * указанного количества {weather} дней.
   * @param {string} weather тип погоды
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  weather(weather, daysCount) {
    for (let i = 0; i < daysCount; i++) {
      this.trip.push({
        type: weather,
      });
    }

    return this;
  }

  /**
   * Метод, добавляющий условие максимального количества дней.
   * @param {number} daysCount количество дней
   * @returns {object} Объект планировщика маршрута
   */
  max(daysCount) {
    this.cityTimer = Math.min(daysCount, this.cityTimer);

    return this;
  }

  /**
   * Метод, возвращающий Promise с планируемым маршрутом.
   * @returns {Promise<TripItem[]>} Список городов маршрута
   */
  async build() {
    if (this.trip.length > 7) {
      throw new Error('Не могу построить маршрут!');
    }
    const info = await this.getWeather();

    return this.getTrip(info);
  }

  getTrip(info) {
    const tripTimer = this.trip.length;
    const neighbors = [];
    const result = [];
    let day = 0;
    let flag = true;

    while (flag && day < tripTimer) {
      flag = false;
      info.forEach(({ geoid, conditions }) => {
        if (!neighbors.includes(geoid) && conditions[day]) {
          for (
            let i = 0;
            i < this.cityTimer
            && day < tripTimer
            && conditions[day] === this.trip[day].type;
            i++
          ) {
            !flag && neighbors.push(geoid);
            flag = true;
            day++;

            result.push({
              geoid,
              day,
            });
          }
        }
      });
    }

    if (day < tripTimer) {
      throw new Error('Не могу построить маршрут!');
    }

    return result;
  }

  async getWeather() {
    return await Promise.all(this.geoids.map(this.fetchWeather));
  }

  async fetchWeather(geoid) {
    const response = await fetch(`https://api.weather.yandex.ru/v2/forecast?hours=false&limit=7&geoid=${geoid}`, {
      'X-Yandex-API-Key': API_KEY,
    });
    const data = await response.json();

    return {
      geoid,
      conditions: data['forecasts']
        .map(day => TripBuilder.MAP_DAY_TYPES[day['parts']['day_short']['condition']])
    }
  }
}

/**
 * Фабрика для получения планировщика маршрута.
 * Принимает на вход список идентификаторов городов, а
 * возвращает планировщик маршрута по данным городам.
 *
 * @param {number[]} geoids Список идентификаторов городов
 * @returns {TripBuilder} Объект планировщика маршрута
 * @see https://yandex.ru/dev/xml/doc/dg/reference/regions-docpage/
 */
function planTrip(geoids) {
  return new TripBuilder(geoids);
}

module.exports = {
  planTrip
};
