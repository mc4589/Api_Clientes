package com.espe.api_clientes.servicio;

import com.espe.api_clientes.modelo.Cliente;
import java.util.List;

public interface ClienteService {

    List<Cliente> listarTodos();
    Cliente buscarPorId(Long id);
    Cliente guardar(Cliente cliente);
    Cliente actualizar(Long id, Cliente cliente);
    void eliminar(Long id);
}
