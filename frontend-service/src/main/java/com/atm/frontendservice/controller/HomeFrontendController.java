package com.atm.frontendservice.controller;

import com.atm.shared.dto.UsuarioDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class HomeFrontendController {

    @GetMapping
    public String showHome(Model model) {

        return "home"; // plantilla: src/main/resources/templates/usuarios.html
    }
}
