function setCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function eraseCookie(name) {   
    document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function limpiar() {
    eraseCookie("usuario")
    eraseCookie("contra")
}

function priiint() {
    console.log(JSON.parse(getCookie("usuario")));
    console.log(JSON.parse(getCookie("contra")));
}

function ingresar() {
    let inputUsuario = document.getElementById("floatingInput");
    let uValido = inputUsuario.checkValidity();
    let inputContra = document.getElementById("floatingPassword");
    let cValido = inputContra.checkValidity();

    if(!uValido || !cValido) {
        if (inputUsuario.validationMessage == "No existe") {
            inputUsuario.setCustomValidity("");
        } else if (inputContra.validationMessage == "Contraseña incorrecta") {
            inputContra.setCustomValidity("");
        } else {
            return
        }
    }    

    let usuarios = JSON.parse(getCookie("usuario"));
    let contras = JSON.parse(getCookie("contra"));
    
    let indiceUsuario = usuarios.indexOf(inputUsuario.value);

    if(indiceUsuario < 0){
        inputUsuario.setCustomValidity("No existe");
        return;
    } else {
        if(inputContra.value == contras[indiceUsuario]){
            console.log("login")
        } else {
            console.log("pifiaste")
            inputContra.setCustomValidity("Contraseña incorrecta");
        }
    }
}