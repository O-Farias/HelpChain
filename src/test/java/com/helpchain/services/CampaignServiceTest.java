package com.helpchain.services;

import com.helpchain.models.Campaign;
import com.helpchain.repositories.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCampaignSuccessfully() {
        Campaign campaign = new Campaign();
        campaign.setTitle("Campanha 1");
        campaign.setDescription("Descrição da Campanha");
        campaign.setStartDate(LocalDate.now().minusDays(1));
        campaign.setEndDate(LocalDate.now().plusDays(10));
        campaign.setGoalAmount(BigDecimal.valueOf(5000));
        campaign.setCurrentAmount(BigDecimal.ZERO);

        when(campaignRepository.save(campaign)).thenReturn(campaign);

        Campaign createdCampaign = campaignService.createCampaign(campaign);

        assertNotNull(createdCampaign);
        assertEquals("Campanha 1", createdCampaign.getTitle());
        verify(campaignRepository, times(1)).save(campaign);
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        Campaign campaign = new Campaign();
        campaign.setStartDate(LocalDate.now().plusDays(5));
        campaign.setEndDate(LocalDate.now().plusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            campaignService.createCampaign(campaign);
        });

        assertEquals("A data de início deve ser antes da data de término.", exception.getMessage());
    }

    @Test
    void shouldReturnAllCampaigns() {
        Campaign campaign1 = new Campaign();
        Campaign campaign2 = new Campaign();

        when(campaignRepository.findAll()).thenReturn(Arrays.asList(campaign1, campaign2));

        List<Campaign> campaigns = campaignService.getAllCampaigns();

        assertNotNull(campaigns);
        assertEquals(2, campaigns.size());
        verify(campaignRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnActiveCampaigns() {
        Campaign activeCampaign = new Campaign();
        activeCampaign.setStartDate(LocalDate.now().minusDays(5));
        activeCampaign.setEndDate(LocalDate.now().plusDays(5));

        LocalDate today = LocalDate.now();

        when(campaignRepository.findByStartDateBeforeAndEndDateAfter(today, today))
            .thenReturn(List.of(activeCampaign));

        List<Campaign> activeCampaigns = campaignService.getActiveCampaigns();

        assertNotNull(activeCampaigns);
        assertEquals(1, activeCampaigns.size());
        verify(campaignRepository, times(1)).findByStartDateBeforeAndEndDateAfter(today, today);
    }

    @Test
    void shouldDeleteCampaignSuccessfully() {
        Long campaignId = 1L;

        when(campaignRepository.existsById(campaignId)).thenReturn(true);
        doNothing().when(campaignRepository).deleteById(campaignId);

        campaignService.deleteCampaign(campaignId);

        verify(campaignRepository, times(1)).deleteById(campaignId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentCampaign() {
        Long campaignId = 1L;

        when(campaignRepository.existsById(campaignId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            campaignService.deleteCampaign(campaignId);
        });

        assertEquals("Campanha não encontrada para exclusão.", exception.getMessage());
    }
}
