package main.java.com.atm.frontendservice.service;

import com.atm.frontendservice.dto.UsuarioConPedidosDto;
import com.atm.shared.dto.PedidoDto;
import com.atm.shared.dto.UsuarioDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioFrontendService {

    private final WebClient usersClient = WebClient.builder().baseUrl("http://localhost:8081").build();
    private final WebClient ordersClient = WebClient.builder().baseUrl("http://localhost:8082").build();

    public Mono<UsuarioConPedidosDto> obtenerUsuarioConPedidos(Long id) {

        Mono<UsuarioDto> usuarioMono = usersClient.get().uri("/usuarios/{id}", id)
                .retrieve().bodyToMono(UsuarioDto.class);

        Flux<PedidoDto> pedidosFlux = ordersClient.get().uri("/pedidos/usuario/{id}", id)
                .retrieve().bodyToFlux(PedidoDto.class);

        return Mono.zip(usuarioMono, pedidosFlux.collectList(), UsuarioConPedidosDto::new);
    }
}
