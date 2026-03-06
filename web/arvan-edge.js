'use strict';

const CSS = `
body {
    margin: 0;
    padding: 0;
    font-family: 'Tahoma', sans-serif;
    background: linear-gradient(to bottom, #2d999a, #81b235);
    height: 100vh;
    display: flex;
    flex-direction: column;
    color: white;
    overflow: hidden;
}
.header {
    background: #212121;
    color: white;
    padding: 15px 20px;
    font-size: 22px;
}
.content {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 50px;
    position: relative;
}
.result {
    font-size: 80px;
    margin-bottom: 50px;
    font-weight: 300;
}
.inputs {
    width: 80%;
    max-width: 320px;
    direction: rtl;
}
.input-row {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}
.input-row label {
    font-size: 24px;
    min-width: 70px;
    text-align: right;
}
.input-row input {
    background: transparent;
    border: none;
    border-bottom: 1.5px solid rgba(255, 255, 255, 0.4);
    color: white;
    font-size: 24px;
    width: 100%;
    outline: none;
    padding-bottom: 5px;
    margin-right: 15px;
}
.footer {
    position: absolute;
    bottom: 30px;
    width: 100%;
    text-align: center;
    font-size: 20px;
    padding: 0 20px;
    box-sizing: border-box;
    line-height: 1.5;
}
`;
const HTML = `
<!doctype html>
<html lang="fa">
<head>
    <meta charset="utf-8">
    <title>Darsad Yar</title>
    <link rel="stylesheet" href="/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="header">Darsad Yar</div>
    <div class="content">
        <div class="result">62.5%</div>
        <div class="inputs">
            <div class="input-row">
                <label>کل:</label>
                <input type="text" value="16">
            </div>
            <div class="input-row">
                <label>نزده:</label>
                <input type="text" value="2">
            </div>
            <div class="input-row">
                <label>غلط:</label>
                <input type="text" value="3">
            </div>
            <div class="input-row">
                <label>درست:</label>
                <input type="text" value="">
            </div>
        </div>
        <div class="footer">
            لازم نیست هر ۴ تا خونه رو پر کنی...
            <br>
            امتحان کن!
        </div>
    </div>
</body>
</html>
`;

addEventListener("fetch", (event) => {
    event.respondWith(handleRequest(event.request));
});


async function handleRequest(request) {
    // If request is for style.css, serve the raw CSS
    if (/style\.css$/.test(request.url)) {
        return new Response(CSS, {
            headers: {
                "content-type": "text/css",
            },
        });
    } else {
        // Serve raw HTML using Early Hints for the CSS file
        return new Response(HTML, {
            headers: {
                "content-type": "text/html",
                link: "</style.css>; rel=preload; as=style",
            },
        });
    }
}
