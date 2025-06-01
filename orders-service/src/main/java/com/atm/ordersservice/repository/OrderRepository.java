package com.atm.ordersservice.repository;

import com.atm.ordersservice.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByEmail(String email);
}
