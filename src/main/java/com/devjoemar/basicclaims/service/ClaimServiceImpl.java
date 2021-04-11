package com.devjoemar.basicclaims.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.devjoemar.basicclaims.exception.ClaimProcessException;
import com.devjoemar.basicclaims.exception.ResourceNotFoundException;
import com.devjoemar.basicclaims.model.Claim;
import com.devjoemar.basicclaims.model.User;
import com.devjoemar.basicclaims.repository.ClaimRepository;
import com.devjoemar.basicclaims.repository.UserRepository;
import com.devjoemar.basicclaims.representation.ClaimAssembler;
import com.devjoemar.basicclaims.representation.ClaimEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    private final UserRepository userRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository, UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Claim createClaim(ClaimEntity claimEntity) {

        if (isPolicyNumberExist(claimEntity.getPolicyNumber())) {
            String msg = String.format("Policy number %s already exist. Could not create new claim",
                    claimEntity.getPolicyNumber());
            throw new ClaimProcessException(msg);
        }

        Optional<User> userOptional = userRepository.findByUsername(claimEntity.getUsername());
        User user = userOptional.orElseGet(() -> userRepository.save(new User(claimEntity.getUsername())));

        LocalDateTime dateTime = LocalDateTime.parse(claimEntity.getDateCreated(), ClaimAssembler.DATE_TIME_FORMATTER);
        String claimNumber = NanoIdUtils.randomNanoId();
        Claim claim = new Claim(claimEntity.getAmount(), claimNumber, claimEntity.getPolicyNumber(), dateTime, user, claimEntity.getDocs());

        return claimRepository.save(claim);
    }

    private boolean isPolicyNumberExist(String policyNumber) {
        Optional<Claim> claimOptional = claimRepository.findByPolicyNumber(policyNumber);
        return claimOptional.isPresent();
    }

    @Override
    public List<Claim> getClaims(String username) {
        Optional<List<Claim>> claimsOptional = claimRepository.findClaimsByUsername(username);
        return claimsOptional.orElseThrow(() -> new ResourceNotFoundException(String.format("No claims for user: %s", username)));
    }

    @Override
    public Claim updateClaim(ClaimEntity claimEntity) {
        Optional<Claim> claimOptional = claimRepository.findByPolicyNumber(claimEntity.getPolicyNumber());
        Optional<User> userOptional = userRepository.findByUsername(claimEntity.getUsername());

        if (claimOptional.isPresent() && userOptional.isPresent()) {
            LocalDateTime dateTime = LocalDateTime.parse(claimEntity.getDateCreated(), ClaimAssembler.DATE_TIME_FORMATTER);
            Claim claim = claimOptional.get();
            User user = userOptional.get();
            claim.setAmount(claimEntity.getAmount());
            claim.setDateCreated(dateTime);
            claim.setUser(user);
            claim.setSubmittedDocs(claimEntity.getDocs());
            return claimRepository.save(claim);
        }

        final String notExistMsg = "not exist";
        String msg = String.format("Update claim failed. Policy number %s. Username %s.",
                claimOptional.isPresent() ? claimOptional.get().getClaimNumber() : notExistMsg,
                userOptional.isPresent() ? userOptional.get().getUsername() : notExistMsg);
        throw new ClaimProcessException(msg);
    }

    @Override
    public void deleteClaim(ClaimEntity claimEntity) {
        Optional<Claim> claimOptional = claimRepository.findClaimByNumberByUsername(claimEntity.getUsername(),
                claimEntity.getClaimNumber());

        if (claimOptional.isPresent()) {
            claimRepository.delete(claimOptional.get());
        } else {
            String msg = String.format("Delete claim failed with claim number: %s", claimEntity.getClaimNumber());
            throw new ClaimProcessException(msg);
        }
    }
}
