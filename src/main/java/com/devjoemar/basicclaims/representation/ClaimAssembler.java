package com.devjoemar.basicclaims.representation;

import com.devjoemar.basicclaims.model.Claim;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ClaimAssembler implements RepresentationModelAssembler<Claim, ClaimEntity> {

    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public ClaimEntity toModel(Claim entity) {

        ClaimEntity claimEntity = new ClaimEntity.ClaimEntityBuilder()
                .setAmount(entity.getAmount())
                .setClaimNumber(entity.getClaimNumber())
                .setPolicyNumber(entity.getPolicyNumber())
                .setUsername(entity.getUser().getUsername())
                .setDocs(entity.getSubmittedDocs())
                .setDateCreated(entity.getDateCreated().format(DATE_TIME_FORMATTER))
                .build();

        return claimEntity;
    }
}
