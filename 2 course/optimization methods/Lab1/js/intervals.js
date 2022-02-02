import {stats} from "./main.js";

export {addData, removeData}

const inter = document.getElementById('intervals');

function addData() {
    intervals.data.labels = Array.apply(null, {length: stats.length}).map(Number.call, Number)
    for (let i = 0; i < intervals.data.datasets.length; i++) {
        for (let j = 0; j < stats.length; j++) {
            let fct = intervals.data.datasets[i].function,
                x = fct(),
                y = stats[j][x];
            intervals.data.datasets[i].data.push(y);
        }
    }
    intervals.update()
}

function removeData() {
    for (let curData of intervals.data.datasets) {
        curData.data.length = 0
    }
    intervals.update()
}

const intervals = new Chart(inter, {
    type: 'line',
    data: {
        labels: Array.apply(null, {length: 10}).map(Number.call, Number),
        datasets: [{
            label: "a",
            function: function() { return 1 },
            borderColor: "rgba(255, 206, 86, 1)",
            data: [],
            fill: false
        },{
            label: "b",
            function: function() { return 2 },
            borderColor: "rgba(54, 162, 235, 1)",
            data: [],
            fill: false
        },{
            label: "f1",
            function: function() { return 6 },
            borderColor: "rgba(75, 192, 192, 1)",
            data: [],
            fill: false
        },{
            label: "f2",
            function: function() { return 7 },
            borderColor: "rgba(153, 102, 255, 1)",
            data: [],
            fill: false
        }]
    },
    options: {
        plugin_interval: true,
        scales: {
            yAxes: [{
                ticks: {
                    min: 0.0,
                    max: 4.0,
                    stepSize: 0.1
                },
            }]
        }
    }
});