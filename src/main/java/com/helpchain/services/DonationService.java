package com.helpchain.services;

import com.helpchain.models.Donation;
import com.helpchain.models.Campaign;
import com.helpchain.repositories.DonationRepository;
import com.helpchain.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    public Donation createDonation(Donation donation) {
        Campaign campaign = campaignRepository.findById(donation.getCampaign().getId())
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada."));

        LocalDate today = LocalDate.now();
        if (campaign.getStartDate().isAfter(today) || campaign.getEndDate().isBefore(today)) {
            throw new IllegalArgumentException("A campanha não está ativa para receber doações.");
        }

        BigDecimal updatedAmount = campaign.getCurrentAmount().add(donation.getAmount());
        campaign.setCurrentAmount(updatedAmount);
        campaignRepository.save(campaign);

        return donationRepository.save(donation);
    }

    public List<Donation> getDonationsByDonor(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    public List<Donation> getDonationsByCampaign(Long campaignId) {
        return donationRepository.findByCampaignId(campaignId);
    }

    public Double getTotalDonationsByCampaign(Long campaignId) {
        return donationRepository.sumByCampaignId(campaignId);
    }
}
