package com.helpchain.repositories;

import com.helpchain.models.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    // Busca doações feitas por um doador específico
    List<Donation> findByDonorId(Long donorId);

    // Busca doações associadas a uma campanha específica
    List<Donation> findByCampaignId(Long campaignId);

    // Calcula o total arrecadado por uma campanha
    Double sumByCampaignId(Long campaignId);
}
