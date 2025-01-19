package com.helpchain.services;

import com.helpchain.models.Campaign;
import com.helpchain.models.Donation;
import com.helpchain.repositories.CampaignRepository;
import com.helpchain.repositories.DonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DonationServiceTest {

    @InjectMocks
    private DonationService donationService;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDonation_Success() {
        // Arrange
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setStartDate(LocalDate.now().minusDays(5));
        campaign.setEndDate(LocalDate.now().plusDays(5));
        campaign.setCurrentAmount(BigDecimal.ZERO);

        Donation donation = new Donation();
        donation.setAmount(BigDecimal.valueOf(100));
        donation.setCampaign(campaign);

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        // Act
        Donation result = donationService.createDonation(donation);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100), campaign.getCurrentAmount());
        verify(campaignRepository, times(1)).save(campaign);
        verify(donationRepository, times(1)).save(donation);
    }

    @Test
    void testCreateDonation_CampaignNotFound() {
        // Arrange
        Donation donation = new Donation();
        donation.setCampaign(new Campaign());
        donation.getCampaign().setId(99L);

        when(campaignRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.createDonation(donation);
        });

        assertEquals("Campanha não encontrada.", exception.getMessage());
        verify(campaignRepository, never()).save(any(Campaign.class));
        verify(donationRepository, never()).save(any(Donation.class));
    }

    @Test
    void testCreateDonation_CampaignNotActive() {
        // Arrange
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setStartDate(LocalDate.now().plusDays(1)); // Campanha ainda não começou
        campaign.setEndDate(LocalDate.now().plusDays(5));

        Donation donation = new Donation();
        donation.setCampaign(campaign);

        when(campaignRepository.findById(1L)).thenReturn(Optional.of(campaign));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            donationService.createDonation(donation);
        });

        assertEquals("A campanha não está ativa para receber doações.", exception.getMessage());
        verify(campaignRepository, never()).save(any(Campaign.class));
        verify(donationRepository, never()).save(any(Donation.class));
    }
}
