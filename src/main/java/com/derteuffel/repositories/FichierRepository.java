package com.derteuffel.repositories;

import com.derteuffel.entities.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichierRepository extends JpaRepository<Fichier,String>{

    Fichier findByName(String name);
}
