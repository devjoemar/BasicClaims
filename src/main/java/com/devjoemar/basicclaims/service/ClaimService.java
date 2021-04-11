package com.devjoemar.basicclaims.service;

import com.devjoemar.basicclaims.model.Claim;
import com.devjoemar.basicclaims.representation.ClaimEntity;

import java.util.List;

public interface ClaimService {

    Claim createClaim(ClaimEntity claimEntity);

    List<Claim> getClaims(String username);

    Claim updateClaim(ClaimEntity claimEntity);

    void deleteClaim(ClaimEntity claimEntity);
}
