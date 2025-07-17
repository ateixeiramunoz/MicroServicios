package com.atm.frontendservice.dto;

import com.atm.shared.dto.PedidoDto;
import com.atm.shared.dto.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioConPedidosDto {

    private UsuarioDto usuario;
    private List<PedidoDto> pedidos;
}
