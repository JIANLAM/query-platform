$(document).ready(function () {
    var ti = 120;
    setInterval(function () {
        document.getElementById('second').innerHTML = ti;
        ti--;
        if (ti === 0) {
            javascript:jsObj.JumpHome();
        }
    }, 1000);
});