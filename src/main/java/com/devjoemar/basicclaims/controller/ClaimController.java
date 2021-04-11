package com.devjoemar.basicclaims.controller;

import com.devjoemar.basicclaims.model.Claim;
import com.devjoemar.basicclaims.representation.ClaimAssembler;
import com.devjoemar.basicclaims.representation.ClaimEntity;
import com.devjoemar.basicclaims.service.ClaimService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/claims")
public class ClaimController {

    private final ClaimService claimService;

    private final ClaimAssembler claimAssembler;

    public ClaimController(ClaimService claimService, ClaimAssembler claimAssembler) {
        this.claimService = claimService;
        this.claimAssembler = claimAssembler;
    }

    @PostMapping("/createClaim")
    public ResponseEntity<?> doCreateClaim(@RequestBody ClaimEntity claimEntity) {

        Claim saved = claimService.createClaim(claimEntity);

        ClaimEntity res = new ClaimAssembler().toModel(saved);
        res.add(getLinks(res));

        return ResponseEntity.created(res.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(res);
    }

    @GetMapping("/getClaims")
    public ResponseEntity<?> doGetClaims(@RequestBody ClaimEntity claimEntity) {
        List<Claim> claims = claimService.getClaims(claimEntity.getUsername());
        List<ClaimEntity> claimEntities = claims.stream()
                .map(claimAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<ClaimEntity> collectionModels = CollectionModel.of(claimEntities,
                getLinks(claimEntity));

       return ResponseEntity.ok(collectionModels);
    }

    @PutMapping("/updateClaim")
    public  ResponseEntity<?> doUpdateClaim(@RequestBody ClaimEntity claimEntity) {
        Claim updateClaim = claimService.updateClaim(claimEntity);
        ClaimEntity res = new ClaimAssembler().toModel(updateClaim);
        res.add(getLinks(res));
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/deleteClaim")
    public  ResponseEntity<?> doDeleteClaim(@RequestBody ClaimEntity claimEntity) {
        claimService.deleteClaim(claimEntity);

        return ResponseEntity.ok().body("Deleted");
    }

    private List<Link> getLinks(ClaimEntity claimEntity) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(ClaimController.class)
                .doGetClaims(claimEntity))
                .withSelfRel()
                .withType(MediaType.APPLICATION_JSON.toString()));

        links.add(linkTo(methodOn(ClaimController.class)
                .doUpdateClaim(claimEntity))
                .withSelfRel()
                .withType(MediaType.APPLICATION_JSON.toString()));

        links.add(linkTo(methodOn(ClaimController.class)
                .doDeleteClaim(claimEntity))
                .withSelfRel()
                .withType(MediaType.APPLICATION_JSON.toString()));
        return links;
    }
}
