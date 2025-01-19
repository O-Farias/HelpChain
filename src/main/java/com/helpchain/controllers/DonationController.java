package com.helpchain.controllers;

import com.helpchain.models.Donation;
import com.helpchain.services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations") 
@CrossOrigin(origins = "*")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        Donation createdDonation = donationService.createDonation(donation);
        return ResponseEntity.ok(createdDonation);
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<Donation>> getDonationsByDonor(@PathVariable Long donorId) {
        List<Donation> donations = donationService.getDonationsByDonor(donorId);
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<Donation>> getDonationsByCampaign(@PathVariable Long campaignId) {
        List<Donation> donations = donationService.getDonationsByCampaign(campaignId);
        return ResponseEntity.ok(donations);
    }
}
