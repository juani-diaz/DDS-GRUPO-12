// Configuración básica de Toastr
toastr.options = {
    closeButton: true, // Muestra un botón para cerrar
    progressBar: false, // Muestra una barra de progreso
    positionClass: 'toast-bottom-right', // Posición del toast
    timeOut: 5000 // Tiempo de visualización (5 segundos)
};

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

const mensajeError = getCookie('obs_error');
const mensajeExito = getCookie('obs_exito');

console.log("Mensaje de error:", mensajeError); // Depuración
console.log("Mensaje de éxito:", mensajeExito); // Depuración

// Mostrar toast de error si existe
if (mensajeError) {
    const mensajeDecodificado = decodeURIComponent(mensajeError.replace(/\+/g, ' '));
    toastr.error(mensajeDecodificado);
    document.cookie = 'obs_error=; Max-Age=0; path=/';
}

// Mostrar toast de éxito si existe
if (mensajeExito) {
    const mensajeDecodificado = decodeURIComponent(mensajeExito.replace(/\+/g, ' '));
    toastr.success(mensajeDecodificado);
    document.cookie = 'obs_exito=; Max-Age=0; path=/';
}