package com.helpchain.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount; 

    @Column(nullable = false)
    private LocalDate donationDate; 

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false) 
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    private User donor;

    
}
