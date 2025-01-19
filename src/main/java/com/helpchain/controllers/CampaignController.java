package com.helpchain.controllers;

import com.helpchain.models.Campaign;
import com.helpchain.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    // Endpoint para criar uma nova campanha
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }

    // Endpoint para listar todas as campanhas
    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    // Endpoint para listar campanhas ativas
    @GetMapping("/active")
    public ResponseEntity<List<Campaign>> getActiveCampaigns() {
        return ResponseEntity.ok(campaignService.getActiveCampaigns());
    }

    // Endpoint para listar campanhas por ONG
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Campaign>> getCampaignsByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(campaignService.getCampaignsByOwner(ownerId));
    }

    // Endpoint para deletar uma campanha
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build();
    }
}
