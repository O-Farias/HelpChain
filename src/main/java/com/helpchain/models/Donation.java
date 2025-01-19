package com.helpchain.models;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount; 

    @Column(nullable = false)
    private LocalDate donationDate;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    @JsonBackReference 
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference 
    private User donor;
}
