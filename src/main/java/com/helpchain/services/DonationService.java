package com.helpchain.services;

import com.helpchain.models.Donation;
import com.helpchain.models.Campaign;
import com.helpchain.repositories.DonationRepository;
import com.helpchain.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    // Método para registrar uma nova doação
    public Donation createDonation(Donation donation) {
        // Verifica se a campanha existe
        Campaign campaign = campaignRepository.findById(donation.getCampaign().getId())
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada."));

        // Verifica se a campanha está ativa
        LocalDate today = LocalDate.now();
        if (campaign.getStartDate().isAfter(today) || campaign.getEndDate().isBefore(today)) {
            throw new IllegalArgumentException("A campanha não está ativa para receber doações.");
        }

        // Atualiza o valor arrecadado da campanha
        campaign.setCurrentAmount(campaign.getCurrentAmount() + donation.getAmount());
        campaignRepository.save(campaign);

        // Salva a doação
        return donationRepository.save(donation);
    }

    // Método para listar todas as doações de um doador
    public List<Donation> getDonationsByDonor(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    // Método para listar todas as doações de uma campanha
    public List<Donation> getDonationsByCampaign(Long campaignId) {
        return donationRepository.findByCampaignId(campaignId);
    }
}
