'use strict';

const nunjucks = require('nunjucks');
const fs = require('fs');

const OUTPUT = './app.js'
const data = {
    style: fs.readFileSync("./style.css", {encoding: "utf-8"}),
    html: fs.readFileSync("./index.html", {encoding: "utf-8"}),
}

nunjucks.configure("./", {
    autoescape: false,
})
const bundle = nunjucks.render('./app.js.j2', data)

fs.writeFile(OUTPUT, bundle, {encoding: "utf-8"}, () => {
    console.info('successfully created output: ' + OUTPUT);
})
