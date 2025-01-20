package com.helpchain.controllers;

import com.helpchain.models.Campaign;
import com.helpchain.services.CampaignService;
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

@WebMvcTest(CampaignController.class)
@WithMockUser(username = "testuser", roles = {"USER"}) 
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    private Campaign sampleCampaign;

    @BeforeEach
    public void setUp() {
        
        sampleCampaign = new Campaign();
        sampleCampaign.setId(1L);
        sampleCampaign.setTitle("Campanha Teste");
        sampleCampaign.setDescription("Descrição da campanha teste");
        sampleCampaign.setGoalAmount(BigDecimal.valueOf(5000.00));
        sampleCampaign.setCurrentAmount(BigDecimal.valueOf(1500.00));
        sampleCampaign.setStartDate(LocalDate.now().minusDays(10));
        sampleCampaign.setEndDate(LocalDate.now().plusDays(10));
    }

    @Test
    public void testCreateCampaign() throws Exception {
        when(campaignService.createCampaign(Mockito.any(Campaign.class))).thenReturn(sampleCampaign);

        String campaignJson = """
                {
                    "title": "Campanha Teste",
                    "description": "Descrição da campanha teste",
                    "goalAmount": 5000.00,
                    "startDate": "2025-01-10",
                    "endDate": "2025-01-30"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(campaignJson)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleCampaign.getId()))
                .andExpect(jsonPath("$.title").value(sampleCampaign.getTitle()));
    }

    @Test
    public void testGetAllCampaigns() throws Exception {
        when(campaignService.getAllCampaigns()).thenReturn(List.of(sampleCampaign));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleCampaign.getId()))
                .andExpect(jsonPath("$[0].title").value(sampleCampaign.getTitle()));
    }

    @Test
    public void testGetActiveCampaigns() throws Exception {
        when(campaignService.getActiveCampaigns()).thenReturn(List.of(sampleCampaign));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/campaigns/active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleCampaign.getId()))
                .andExpect(jsonPath("$[0].title").value(sampleCampaign.getTitle()));
    }

    @Test
    public void testGetCampaignsByOwner() throws Exception {
        when(campaignService.getCampaignsByOwner(1L)).thenReturn(List.of(sampleCampaign));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/campaigns/owner/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleCampaign.getId()))
                .andExpect(jsonPath("$[0].title").value(sampleCampaign.getTitle()));
    }

    @Test
    public void testDeleteCampaign() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/campaigns/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }
}
