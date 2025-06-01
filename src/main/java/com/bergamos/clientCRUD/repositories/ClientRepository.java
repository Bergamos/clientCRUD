package com.bergamos.clientCRUD.repositories;

import com.bergamos.clientCRUD.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
