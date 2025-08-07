package com.espe.api_clientes.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema; // Importar la anotación Schema

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Marca el ID como de solo lectura en la documentación de Swagger
    // Esto lo ocultará o deshabilitará para la entrada en operaciones de creación (POST)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "La cédula no puede estar vacía")
    private String ci;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Los nombres no pueden estar vacíos")
    private String nombres;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    private String apellidos;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El contacto no puede estar vacío")
    private String contacto;

    // Constructor sin argumentos
    public Cliente() {
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
}
