let stomp = null;

// подключаемся к серверу по окончинии загрузки страницы
window.onload = function () {
    connect();
};

function connect() {
    const socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe("topic/products", function (product) {
            renderItem(product);
        });
    });
}

// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() {
        sendContent();
    });
});

//отправка сообщений на сервер
function sendContent() {
    stomp.send("app/products", {}, JSON.stringify({
        "title": $("#title").val(),
        "price": $("#price").val(),
    }));
}

//рендер сообщения полученного от сервера
function renderItem(productJson) {
    const product = JSON.parse(productJson.body);
    $("#table").append("<tr>" +
        "<td>" + product.title + "</td>" +
        "<td>" + product.price + "</td>" +
        "<td><a href='/products'" + product.id + "/bucket >Add to bucket</a></td>" +
        "</tr>");
}