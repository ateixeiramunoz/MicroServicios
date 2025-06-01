package com.atm.usersservice.controller;

import com.atm.usersservice.model.Usuario;
import com.atm.usersservice.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class UsuarioController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final RestTemplate restTemplate;

    public UsuarioController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String listar(Model model) {
        ResponseEntity<Usuario[]> response = restTemplate.getForEntity("http://localhost:8080/usuarios", Usuario[].class);
        List<Usuario> usuarios = Arrays.asList(response.getBody());
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // apunta a usuarios.html
    }
}
