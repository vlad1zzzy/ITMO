import {stats} from "./main.js";

export {addDataParabola, removeDataParabola, drawParabola}

const ctx = document.getElementById('myChart');

let data = {
    labels: Array.apply(null, {length: 51}).map(Number.call, Number).map(el => (0.1 * el).toFixed(2)),
    datasets: [{
        label: "f(x) = x - ln(x)",
        function: function (x) {
            return x - Math.log(x)
        },
        borderColor: "rgba(75, 192, 192, 1)",
        data: [],
        fill: false
    }]
};

Chart.plugins.register({
    beforeInit: function (chart) {
        if (chart.options.isMain) {
            let data = chart.config.data;
            for (let i = 0; i < data.datasets.length; i++) {
                for (let j = 0; j < data.labels.length; j++) {
                    let fct = data.datasets[i].function,
                        x = data.labels[j],
                        y = fct(x);
                    data.datasets[i].data.push(y);
                }
            }
        }
    }
})

function addDataParabola() {
    stats.forEach((parabola) => {
        const [k, a0, a1, a2, x1, x2] = parabola.slice(0, 6).map(el => +el);
        myChart.data.datasets.push({
            label: "step " + k,
            function: function (x) {
                return a0 + a1 * (x - x1) + a2 * (x - x1) * (x - x2)
            },
            borderColor: "rgba(255, 186, " + 20 * k + ", 1)",
            data: [],
            fill: false
        })
    })
}

function removeDataParabola() {
    myChart.data.datasets.length = 1;
    myChart.update()
}

function drawParabola(next) {
    next = Math.min(next, stats.length)
    let data = myChart.config.data;
    for (let i = 1; i < data.datasets.length; i++) {
        data.datasets[i].data.length = 0;
    }
    for (let j = 0; j < data.labels.length; j++) {
        let fct = data.datasets[next].function,
            x = data.labels[j],
            y = fct(x);
        if (y > 5) {
            continue;
        }
        data.datasets[next].data.push(y);
    }
    myChart.update()
}

const myChart = new Chart(ctx, {
    type: 'line',
    data: data,
    options: {
        isMain: true,
        responsive:true,
        scales: {
            yAxes: [{
                ticks: {
                    min: 0.5,
                    max: 5,
                    stepSize: 0.25
                },
            }]
        }
    }
});