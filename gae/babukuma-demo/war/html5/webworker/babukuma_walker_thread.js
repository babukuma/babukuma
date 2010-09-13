var timer;
var incrementMove = -5;
var _x_ = 284;

var start = function() {
    _x_ += incrementMove;

    if (_x_ < 16) {
        _x_ = 284;
    }

    postMessage(_x_);
    timer = setTimeout(start, 100);
}

start();