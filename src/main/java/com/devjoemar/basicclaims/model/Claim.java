package com.devjoemar.basicclaims.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Claim implements Serializable {

    private static final long serialVersionUID = 7791062727179053065L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private BigDecimal amount;

    @Column(unique = true)
    private String claimNumber;

    @Column(unique = true)
    private String policyNumber;

    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ElementCollection
    private List<String> submittedDocs;

    public Claim() {

    }

    public Claim(BigDecimal amount, String claimNumber, String policyNumber, LocalDateTime dateCreated, User user, List<String> submittedDocs) {
        this.amount = amount;
        this.claimNumber = claimNumber;
        this.policyNumber = policyNumber;
        this.dateCreated = dateCreated;
        this.user = user;
        this.submittedDocs = submittedDocs;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getSubmittedDocs() {
        return submittedDocs;
    }

    public void setSubmittedDocs(List<String> submittedDocs) {
        this.submittedDocs = submittedDocs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Claim claim = (Claim) o;
        return Objects.equals(id, claim.id) &&
                Objects.equals(amount, claim.amount) &&
                Objects.equals(claimNumber, claim.claimNumber) &&
                Objects.equals(policyNumber, claim.policyNumber) &&
                Objects.equals(dateCreated, claim.dateCreated) &&
                Objects.equals(lastUpdate, claim.lastUpdate) &&
                Objects.equals(user, claim.user) &&
                Objects.equals(submittedDocs, claim.submittedDocs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, claimNumber, policyNumber, dateCreated, lastUpdate, user, submittedDocs);
    }
}
