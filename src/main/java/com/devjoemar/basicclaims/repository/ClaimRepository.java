package com.devjoemar.basicclaims.repository;

import com.devjoemar.basicclaims.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ClaimRepository extends JpaRepository<Claim, Long> {

    @Query("SELECT claims FROM Claim claims JOIN claims.user u WHERE (u.username = :username)")
    Optional<List<Claim>> findClaimsByUsername(String username);

    Optional<Claim> findByPolicyNumber(String policyNumber);

    @Query("SELECT claims FROM Claim claims JOIN claims.user u WHERE (u.username = :username) AND (claims.claimNumber = :claimNumber)")
    Optional<Claim> findClaimByNumberByUsername(String username, String claimNumber);
}
