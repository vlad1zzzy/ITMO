import {dichotomy} from "./dichotomy.js";
import {golden} from "./golden.js";
import {fibonacci} from "./fibonacci.js";
import {parabola} from "./parabola.js";
import {brent} from "./brent.js";
import {addData, removeData} from "./intervals.js";
import {addDataParabola, removeDataParabola, drawParabola} from "./chart.js";

export {stats, func}
const stats = []

const a = 0.5
const b = 4.0
const func = function (x) {
    return x - Math.log(x);
}

const functions = {
    dichotomy: dichotomy,
    golden: golden,
    fibonacci: fibonacci,
    parabola: parabola,
    brent: brent,
}

let chosenFunction = functions.fibonacci;
let chosenFunctionName = "fibonacci"

// CHOSE METHODS
const methods = document.querySelectorAll(".methods li")
methods.forEach((method) => {
    method.addEventListener("click", function (e) {
        document.getElementById(chosenFunctionName).classList.remove("active")
        chosenFunctionName = e.target.id
        chosenFunction = functions[chosenFunctionName]
        method.classList.add("active")

        if (method.id !== "parabola") {
            removeDataParabola()
            arrows.classList.add("disabled")
        }

        while (dataTable.firstChild) {
            dataTable.removeChild(dataTable.firstChild)
            stats.length = 0
        }
        method.scrollIntoView({
            block: "center",
            behavior: "smooth",
        })
    })
})


// CHOSE ACCURACY
const epsSlider = document.getElementById("eps");
const epsValue = document.getElementById("eps-value");
epsValue.innerHTML = epsSlider.value;
epsSlider.oninput = function () {
    epsValue.innerHTML = this.value;
}

// STEPS
const arrows = document.querySelector(".arrows");
const prev = arrows.querySelector(".prev");
const next = arrows.querySelector(".next");
let step;
prev.addEventListener("click", function () {
    drawParabola(--step)
    if (step  < stats.length) {
        next.classList.remove("disabled")
    }
    if (step === 1) {
        prev.classList.add("disabled")
    }
})
next.addEventListener("click", function () {
    drawParabola(++step)
    if (step > 1) {
        prev.classList.remove("disabled")
    }
    if (step === stats.length) {
        next.classList.add("disabled")
    }
})

// SUBMIT
const btn = document.querySelector(".btn")
const funcMin = document.getElementById("func-min")
const dotMin = document.getElementById("dot-min")
btn.addEventListener("click", function () {
    while (dataTable.firstChild) {
        dataTable.removeChild(dataTable.firstChild)
        stats.length = 0
    }
    const eps = epsValue.innerHTML
    if (notValidInput(eps)) {
        epsSlider.value = ""
        alert("Invalid eps value")
    } else if (typeof chosenFunction !== "function") {
        alert("No such method yet")
    } else {
        removeDataParabola()
        removeData()
        const res = chosenFunction(a, b, Math.pow(10, -eps))
        if (chosenFunctionName === "parabola") {
            addDataParabola()
            drawParabola(1)
            step = 1
            prev.classList.add("disabled")
            next.classList.remove("disabled")
        } else {
            addData()
        }
        createTable()
        dotMin.innerText = res;
        funcMin.innerText = func(res)
    }

    function notValidInput(str) {
        return (str === "" || isNaN(str) || str < 1 || str > 8)
    }
})

btn.addEventListener("click", function () {
    if (chosenFunctionName === "parabola") {
        arrows.classList.remove("disabled")
        document.getElementById("myChart").scrollIntoView({
            block: "center",
            behavior: "smooth",
        })
    } else {
        document.getElementById("intervals").scrollIntoView({
            block: "center",
            behavior: "smooth",
        })
    }
})

// CREATING TABLE
const dataTable = document.querySelector(".datatable")
const tableHeaders = ["k", "a", "b", "b - a", "x1", "x2", "f1", "f2"]
const tableHeadersParabola = ["k", "a0", "a1", "a2", "x1", "x2", "x3", "f1", "f2", "f3", "xi", "fi"]
const tableHeadersBrent = ["k", "a", "b", "x1", "x2", "x3", "f1", "f2", "f3", "xi", "fi"]
const createTable = () => {
    let headers;
    switch (chosenFunctionName) {
        case "parabola":
            headers = tableHeadersParabola;
            break;
        case "brent":
            headers = tableHeadersBrent;
            break;
        default:
            headers = tableHeaders
    }
    let table = document.createElement('table')

    let tableHead = document.createElement('thead')
    tableHead.className = 'tableHead'

    let tableHeaderRow = document.createElement('tr')
    tableHeaderRow.className = 'tableHeaderRow'

    headers.forEach(header => {
        let scoreHeader = document.createElement('th')
        scoreHeader.innerText = header
        tableHeaderRow.append(scoreHeader)
    })
    tableHead.append(tableHeaderRow)
    table.append(tableHead)

    let tableBody = document.createElement('tbody')
    tableBody.className = "table-Body"

    stats.forEach(stat => {
        let row = document.createElement('tr')
        stat.forEach(cur => {
            let el = document.createElement("td")
            el.innerHTML = cur
            row.appendChild(el)
        })
        tableBody.appendChild(row)
    })
    table.append(tableBody)
    dataTable.append(table)
}