var walkerThread;
var _index_ = 0;
var _x_ = 284;
var _y_ = 284;
var incrementIndex = 1;
var _angle_ = 0;
var _toggle_ = true;

/* Imageオブジェクト配列を生成 */
var ani = new Array(3);

var init = function() {
    for (i = 0; i < 3; i++) {
        ani[i] = new Image();
        ani[i].src = "img/" + i + ".png";
    }
};

var onerror = function(event) {
    alert(event);
};

var onmessage = function(event) {
    var canvas = document.getElementById("babukuma_walker_canvas");
    if (!canvas || !canvas.getContext) {
        return false;
    }
    var ctx = canvas.getContext("2d");
    var postX = parseInt(event.data);

    if (_x_ < postX) {
        ctx.rotate(90 * Math.PI / 180);
        _angle_ = (_angle_ + 1) % 4;
    }

    _x_ = postX;

    if (_angle_ == 0) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.drawImage(ani[_index_], _x_, _y_);
    } else if (_angle_ == 1) {
        ctx.clearRect(0, -300, canvas.width, canvas.height);
        ctx.drawImage(ani[_index_], _x_, _y_ - 300);
    } else if (_angle_ == 2) {
        ctx.clearRect(-300, -300, canvas.width, canvas.height);
        ctx.drawImage(ani[_index_], -1 * (300 - _x_), _y_ - 300);
    } else if (_angle_ == 3) {
        ctx.clearRect(-300, 0, canvas.width, canvas.height);
        ctx.drawImage(ani[_index_], -1 * (300 - _x_), _y_);
    }

    _index_ += incrementIndex;

    if (_index_ == 2) {
        incrementIndex = -1;
    } else if (_index_ == 0) {
        incrementIndex = 1;
    }
};

var start = function() {
    // alert("start");
    if (!walkerThread) {
        walkerThread = new Worker("babukuma_walker_thread.js");
        walkerThread.onerror = onerror;
        walkerThread.onmessage = onmessage;
    }
};

var stop = function() {
    // alert("stop");
    if (walkerThread) {
        walkerThread.terminate();
        walkerThread = null;
    }
};
