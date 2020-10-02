package com.derteuffel.repositories;

import com.derteuffel.entities.Courrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourrierRepository extends JpaRepository<Courrier, Long> {

    List<Courrier> findAllByAddedDate(String date);
    List<Courrier> findAllByReceiverDate(String date);
    List<Courrier> findAllBySenderDate(String date);
    List<Courrier> findAllByTypeCourrier(String type);
    Courrier findByReferenceNumber(String numero);
    List<Courrier> findAllByUsers_Id(Long id);

    List<Courrier> findAllByStatus(Boolean status);
}
