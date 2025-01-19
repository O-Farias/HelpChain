package com.helpchain.controllers;

import com.helpchain.models.Donation;
import com.helpchain.services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    // Endpoint para registrar uma nova doação
    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.createDonation(donation));
    }

    // Endpoint para listar todas as doações de um doador
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<Donation>> getDonationsByDonor(@PathVariable Long donorId) {
        return ResponseEntity.ok(donationService.getDonationsByDonor(donorId));
    }

    // Endpoint para listar todas as doações de uma campanha
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<Donation>> getDonationsByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(donationService.getDonationsByCampaign(campaignId));
    }
}
