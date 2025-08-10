const API_URL = "http://localhost:8084/api/clientes"; 
const modal = new bootstrap.Modal(document.getElementById('modalCliente')); 

document.addEventListener("DOMContentLoaded", listarClientes); 


function listarClientes() {
    fetch(API_URL)
        .then(response => {
            if (!response.ok) {
                
                console.error('Error al obtener clientes:', response.statusText);
                return []; 
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById("tabla-clientes"); 
            tbody.innerHTML = ""; 
            data.forEach(cliente => { 
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${cliente.id}</td>
                    <td>${cliente.ci}</td>
                    <td>${cliente.nombres}</td>
                    <td>${cliente.apellidos}</td>
                    <td>${cliente.contacto}</td>
                    <td>
                        <button class="btn btn-sm btn-warning me-2" onclick="editarCliente(${cliente.id})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="eliminarCliente(${cliente.id})">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Hubo un problema con la operación fetch:', error);
            
        });
}


function abrirModalNuevo() {
    limpiarFormulario();
    modal.show();
}

// Guardar o editar un cliente 
function guardarCliente() {
    const id = document.getElementById("cliente-id").value;
    const cliente = {
        ci: document.getElementById("ci").value,
        nombres: document.getElementById("nombres").value,
        apellidos: document.getElementById("apellidos").value,
        contacto: document.getElementById("contacto").value
    };

    const method = id ? "PUT" : "POST"; 
    const url = id ? `${API_URL}/${id}` : API_URL; 

    fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cliente) 
    })
    .then(response => {
        if (!response.ok) {
            
            return response.json().then(err => { throw new Error(err.message || 'Error en la solicitud'); });
        }
        return response.json(); 
    })
    .then(() => {
        modal.hide(); 
        listarClientes(); 
    })
    .catch(error => {
        console.error('Error al guardar el cliente:', error);
        alert('Error al guardar cliente: ' + error.message); 
    });
}

// Editar un cliente 
function editarCliente(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(cliente => { 
            document.getElementById("cliente-id").value = cliente.id;
            document.getElementById("ci").value = cliente.ci;
            document.getElementById("nombres").value = cliente.nombres;
            document.getElementById("apellidos").value = cliente.apellidos;
            document.getElementById("contacto").value = cliente.contacto;
            modal.show(); 
        })
        .catch(error => console.error('Error al cargar cliente para edición:', error));
}

// Eliminar un cliente 
function eliminarCliente(id) {
    if (confirm("¿Está seguro que desea eliminar este cliente?")) { 
        fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (!response.ok) {
                
                return response.json().then(err => { throw new Error(err.message || 'Error al eliminar'); });
            }
            listarClientes(); 
        })
        .catch(error => {
            console.error('Error al eliminar el cliente:', error);
            alert('Error al eliminar cliente: ' + error.message);
        });
    }
}


function limpiarFormulario() {
    document.getElementById("cliente-id").value = "";
    document.getElementById("ci").value = "";
    document.getElementById("nombres").value = "";
    document.getElementById("apellidos").value = "";
    document.getElementById("contacto").value = "";
}