/**
 * Created by valentinspac on 31.10.2015.
 */
function test() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4) {
            alert(xhttp.responseText);
        }
    };
    xhttp.open("GET", "/person", true);
    xhttp.send(null);
}