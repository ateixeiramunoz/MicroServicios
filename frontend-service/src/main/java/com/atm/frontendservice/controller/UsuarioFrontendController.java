package com.atm.frontendservice.controller;

import com.atm.shared.dto.UsuarioDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class UsuarioFrontendController {

    private final WebClient webClient;

    public UsuarioFrontendController(WebClient.Builder builder) {
        // Usa el nombre del servicio registrado en Eureka
        this.webClient = builder.baseUrl("http://users-service").build();
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        Mono<List<UsuarioDto>> usuariosMono = webClient.get()
                .uri("/api/usuarios")
                .retrieve()
                .bodyToFlux(UsuarioDto.class)
                .collectList();

        List<UsuarioDto> usuarios = usuariosMono.block(); // ← puedes usar `block()` si estás en un `@Controller`
        model.addAttribute("usuarios", usuarios);

        return "usuarios"; // plantilla: src/main/resources/templates/usuarios.html
    }
}
