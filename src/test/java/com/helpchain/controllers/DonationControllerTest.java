package com.helpchain.controllers;

import com.helpchain.models.Campaign;
import com.helpchain.models.Donation;
import com.helpchain.models.User;
import com.helpchain.services.DonationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DonationController.class)
@WithMockUser(username = "testuser", roles = {"USER"})
public class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonationService donationService;

    private Donation sampleDonation;

    @BeforeEach
    public void setUp() {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setTitle("Campanha Teste");

        User donor = new User();
        donor.setId(1L);
        donor.setName("Doador Teste");

        sampleDonation = new Donation();
        sampleDonation.setId(1L);
        sampleDonation.setAmount(BigDecimal.valueOf(100.00));
        sampleDonation.setDonationDate(LocalDate.now());
        sampleDonation.setCampaign(campaign);
        sampleDonation.setDonor(donor);
    }

    @Test
    public void testGetDonationsByCampaign() throws Exception {
        when(donationService.getDonationsByCampaign(1L)).thenReturn(List.of(sampleDonation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/donations/campaign/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleDonation.getId()))
                .andExpect(jsonPath("$[0].amount").value(sampleDonation.getAmount().doubleValue()));
    }

    @Test
    public void testGetDonationsByDonor() throws Exception {
        when(donationService.getDonationsByDonor(1L)).thenReturn(List.of(sampleDonation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/donations/donor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleDonation.getId()))
                .andExpect(jsonPath("$[0].amount").value(sampleDonation.getAmount().doubleValue()));
    }

    @Test
    public void testCreateDonation() throws Exception {
        when(donationService.createDonation(Mockito.any(Donation.class))).thenReturn(sampleDonation);

        String donationJson = """
                {
                    "amount": 100.00,
                    "donationDate": "2025-01-19",
                    "campaign": {"id": 1},
                    "donor": {"id": 1}
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(donationJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleDonation.getId()))
                .andExpect(jsonPath("$.amount").value(sampleDonation.getAmount().doubleValue()));
    }
}
