package com.devjoemar.basicclaims.representation;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;


public class ClaimEntity extends RepresentationModel<ClaimEntity> {

    private String claimNumber;

    private List<String> docs;

    private BigDecimal amount;

    private String policyNumber;

    private String username;

    private String dateCreated;

    protected ClaimEntity() {
    }

    private ClaimEntity(ClaimEntityBuilder claimEntityBuilder) {
        this.claimNumber = claimEntityBuilder.claimNumber;
        this.docs = claimEntityBuilder.docs;
        this.amount = claimEntityBuilder.amount;
        this.policyNumber = claimEntityBuilder.policyNumber;
        this.username = claimEntityBuilder.username;
        this.dateCreated = claimEntityBuilder.dateCreated;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public List<String> getDocs() {
        return docs;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public static class ClaimEntityBuilder {
        private String claimNumber;

        private List<String> docs;

        private BigDecimal amount;

        private String policyNumber;

        private String username;

        private String dateCreated;

        public ClaimEntityBuilder setClaimNumber(String claimNumber) {
            this.claimNumber = claimNumber;
            return this;
        }

        public ClaimEntityBuilder setDocs(List<String> docs) {
            this.docs = docs;
            return this;
        }

        public ClaimEntityBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public ClaimEntityBuilder setPolicyNumber(String policyNumber) {
            this.policyNumber = policyNumber;
            return this;
        }

        public ClaimEntityBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ClaimEntityBuilder setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public ClaimEntity build() {
            return new ClaimEntity(this);
        }
    }


}
