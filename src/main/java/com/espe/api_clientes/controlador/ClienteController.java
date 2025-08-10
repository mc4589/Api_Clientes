package com.espe.api_clientes.controlador;

import com.espe.api_clientes.modelo.Cliente; 
import com.espe.api_clientes.servicio.ClienteService; 
import jakarta.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 

import java.net.URI; 
import java.util.List; 
import java.util.Optional; 

@RestController 
@RequestMapping("/api/clientes") 
@CrossOrigin(origins = "*") 
public class ClienteController {

    @Autowired 
    private ClienteService clienteService;

    
    @GetMapping("/test")
    public String test() {
        return "API en funcionamiento"; 
    }

    
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos(); 
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
       
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        
        return clienteOpt.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

   
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        
        Cliente creado = clienteService.guardar(cliente); 
       
        return ResponseEntity.created(URI.create("/api/clientes/" + creado.getId()))
                             .body(creado);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
       
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        return clienteOpt.map(c -> {
            
            c.setCi(cliente.getCi());
            c.setNombres(cliente.getNombres());
            c.setApellidos(cliente.getApellidos());
            c.setContacto(cliente.getContacto());
            Cliente actualizado = clienteService.guardar(c); 
            return ResponseEntity.ok(actualizado); 
        }).orElse(ResponseEntity.notFound().build()); 
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        
        Optional<Cliente> clienteOpt = Optional.ofNullable(clienteService.buscarPorId(id));
        if (clienteOpt.isPresent()) {
            clienteService.eliminar(id); 
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build(); 
    }
}