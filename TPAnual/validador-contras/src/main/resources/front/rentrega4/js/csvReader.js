    document.getElementById('archivoCsv').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file && file.type === 'text/csv') {
    const reader = new FileReader();
    reader.onload = function(e) {
    const contenido = e.target.result;
    const lineas = contenido.split('\n');
    const tbody = document.getElementById('previewTableBody');
    tbody.innerHTML = ''; // Limpiar el contenido previo

    for (let i = 1; i < lineas.length; i++) { // Empezar desde la segunda línea (saltando encabezado)
    const linea = lineas[i].trim();
    if (linea) {
    const columnas = linea.split(',');
    const row = document.createElement('tr');

    // Insertar un número de fila
    const th = document.createElement('th');
    th.textContent = i;
    row.appendChild(th);

    // Insertar cada columna
    columnas.forEach((columna) => {
    const td = document.createElement('td');
    td.textContent = columna;
    row.appendChild(td);
});

    tbody.appendChild(row);
}
}
};
    reader.readAsText(file);
} else {
    alert('Por favor, seleccione un archivo CSV válido.');
}
});
