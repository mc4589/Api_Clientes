package com.espe.api_clientes.repositorio;

import com.espe.api_clientes.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
