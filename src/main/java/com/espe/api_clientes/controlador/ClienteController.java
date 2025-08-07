package com.espe.api_clientes.controlador;

import com.espe.api_clientes.modelo.Cliente; // Importa la clase Cliente del modelo
import com.espe.api_clientes.servicio.ClienteService; // Importa el servicio de Cliente
import jakarta.validation.Valid; // Para la validación de objetos de entrada
import org.springframework.beans.factory.annotation.Autowired; // Para la inyección de dependencias
import org.springframework.http.ResponseEntity; // Para construir respuestas HTTP
import org.springframework.web.bind.annotation.*; // Anotaciones REST principales

import java.net.URI; // Para construir la URI de la respuesta 'created'
import java.util.List; // Para listas de clientes
import java.util.Optional; // Para manejar clientes que pueden no existir

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/clientes") // Define la ruta base para todos los endpoints en este controlador
@CrossOrigin(origins = "*") // Permite peticiones CORS desde cualquier origen (útil para desarrollo frontend)
public class ClienteController {

    @Autowired // Inyecta una instancia de ClienteService
    private ClienteService clienteService;

    // Endpoint de prueba: GET /api/clientes/test
    @GetMapping("/test")
    public String test() {
        return "Funciona"; // Retorna un simple mensaje para verificar que el controlador está activo
    }

    // Endpoint para obtener todos los clientes: GET /api/clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos(); // Llama al servicio para obtener la lista de clientes
    }

    // Endpoint para obtener un cliente por ID: GET /api/clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        // @PathVariable: Extrae el ID de la URL
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        // Si el cliente existe, devuelve 200 OK con el cliente, de lo contrario, 404 Not Found
        return clienteOpt.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo cliente: POST /api/clientes
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        // @Valid: Activa la validación definida en la clase Cliente (ej. @NotBlank)
        // @RequestBody: Indica que el objeto Cliente se construye a partir del cuerpo de la solicitud (JSON)
        Cliente creado = clienteService.guardar(cliente); // Llama al servicio para guardar el cliente
        // Devuelve 201 Created y la ubicación del nuevo recurso en la cabecera Location
        return ResponseEntity.created(URI.create("/api/clientes/" + creado.getId()))
                             .body(creado);
    }

    // Endpoint para actualizar un cliente existente: PUT /api/clientes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        // Busca el cliente existente por ID
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        return clienteOpt.map(c -> {
            // Si el cliente existe, actualiza sus campos con los datos de la petición
            c.setCi(cliente.getCi());
            c.setNombres(cliente.getNombres());
            c.setApellidos(cliente.getApellidos());
            c.setContacto(cliente.getContacto());
            Cliente actualizado = clienteService.guardar(c); // Guarda los cambios
            return ResponseEntity.ok(actualizado); // Devuelve 200 OK con el cliente actualizado
        }).orElse(ResponseEntity.notFound().build()); // Si no encuentra el cliente, devuelve 404 Not Found
    }

    // Endpoint para eliminar un cliente: DELETE /api/clientes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // Busca el cliente para verificar si existe antes de intentar eliminar
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        if (clienteOpt.isPresent()) {
            clienteService.eliminar(id); // Si existe, lo elimina
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content (eliminación exitosa sin cuerpo)
        }
        return ResponseEntity.notFound().build(); // Si no encuentra el cliente, devuelve 404 Not Found
    }
}