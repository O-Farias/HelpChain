package com.helpchain.repositories;

import com.helpchain.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    // Busca campanhas ativas (data atual entre startDate e endDate)
    List<Campaign> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);

    // Busca campanhas por ONG (usuário que criou a campanha)
    List<Campaign> findByOwnerId(Long ownerId);
}
