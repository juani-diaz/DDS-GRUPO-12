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

function registrar() {
    let inputUsuario = document.getElementById("floatingInput");
    let uValido = inputUsuario.checkValidity();
    let inputContra = document.getElementById("floatingPassword");
    let cValido = inputContra.checkValidity();
    let inputContraR = document.getElementById("floatingPasswordR");
    let crValido = inputContraR.checkValidity();

    if(!uValido || !cValido || !crValido){
        if (inputUsuario.validationMessage == "Ya existe") {
            inputUsuario.setCustomValidity("");
        } else if (inputContra.validationMessage == "No coinciden" || inputContra.validationMessage == "Invalida") {
            inputContra.setCustomValidity("");
        } else if (inputContraR.validationMessage == "No coinciden" || inputContraR.validationMessage == "Invalida") {
            inputContraR.setCustomValidity("");
        } else {
            return
        }
    }

    let nuevoUs = inputUsuario.value;
    let nuevaC = inputContra.value;
    let nuevaCR = inputContraR.value;

    if(nuevaC != nuevaCR){
        inputContra.setCustomValidity("No coinciden");
        inputContraR.setCustomValidity("No coinciden");
        return
    } else {
        inputContra.setCustomValidity("");
        inputContraR.setCustomValidity("");
    }

    let usuarios = JSON.parse(getCookie("usuario"));
    let contras = JSON.parse(getCookie("contra"))

    if(usuarios == null){
        usuarios = []
    }

    if(contras == null){
        contras = []
    }

    if(!checkeoUsuario(nuevoUs,usuarios)){
        inputUsuario.setCustomValidity("Ya existe");
        return
    } else {
        inputUsuario.setCustomValidity("");
    }

    if(!checkeoContra(nuevaC)){
        inputContra.setCustomValidity("Invalida");
        inputContraR.setCustomValidity("Invalida");
        return
    } else {
        inputContra.setCustomValidity("");
        inputContraR.setCustomValidity("");
    }

    usuarios.push(nuevoUs);
    contras.push(nuevaC);

    setCookie("usuario",JSON.stringify(usuarios), 1);
    setCookie("contra",JSON.stringify(contras), 1);

    const toastLiveExample = document.getElementById('toastRegistro')

    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
    toastBootstrap.show()

    console.log("Registro ok")
}

const usuariosProhibidos = ["admin", "sudo"]
const contrasProhibidas = ["admin", "sudo", "1234"]

function checkeoUsuario(usuario,usuarios){
    if(usuarios.indexOf(usuario) > -1)
        return false
    if(usuariosProhibidos.indexOf(usuario) > -1)
        return false
    return true;
}

function checkeoContra(contra){
    if(contrasProhibidas.indexOf(contra) > -1)
        return false
    return true;
}

function limpiar() {
    eraseCookie("usuario")
    eraseCookie("contra")
}

function priiint() {
    console.log(JSON.parse(getCookie("usuario")));
    console.log(JSON.parse(getCookie("contra")));
}