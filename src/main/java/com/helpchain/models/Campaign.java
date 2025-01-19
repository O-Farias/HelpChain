package com.helpchain.models;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal goalAmount;

    @Column(nullable = false)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-campaigns")
    private User owner;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "campaign-donations")
    private List<Donation> donations = new ArrayList<>();
}
