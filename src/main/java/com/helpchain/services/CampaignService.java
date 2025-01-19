package com.helpchain.services;

import com.helpchain.models.Campaign;
import com.helpchain.repositories.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    // Método para criar uma nova campanha
    public Campaign createCampaign(Campaign campaign) {
        // Verifica se a data de início é antes da data de fim
        if (campaign.getStartDate().isAfter(campaign.getEndDate())) {
            throw new IllegalArgumentException("A data de início deve ser antes da data de término.");
        }
        return campaignRepository.save(campaign);
    }

    // Método para listar todas as campanhas
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    // Método para listar campanhas ativas
    public List<Campaign> getActiveCampaigns() {
        LocalDate today = LocalDate.now();
        return campaignRepository.findByStartDateBeforeAndEndDateAfter(today, today);
    }

    // Método para buscar campanhas por ONG (usuário dono)
    public List<Campaign> getCampaignsByOwner(Long ownerId) {
        return campaignRepository.findByOwnerId(ownerId);
    }

    // Método para deletar uma campanha
    public void deleteCampaign(Long id) {
        if (!campaignRepository.existsById(id)) {
            throw new IllegalArgumentException("Campanha não encontrada para exclusão.");
        }
        campaignRepository.deleteById(id);
    }
}
