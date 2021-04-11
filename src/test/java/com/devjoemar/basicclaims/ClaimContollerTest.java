package com.devjoemar.basicclaims;

import com.devjoemar.basicclaims.controller.ClaimController;
import com.devjoemar.basicclaims.exception.ClaimProcessException;
import com.devjoemar.basicclaims.model.Claim;
import com.devjoemar.basicclaims.model.User;
import com.devjoemar.basicclaims.representation.ClaimAssembler;
import com.devjoemar.basicclaims.representation.ClaimEntity;
import com.devjoemar.basicclaims.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClaimController.class)
public class ClaimContollerTest {

    private final String BASE_URL = "/api/v1/claims";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClaimService claimService;

    @SpyBean
    private ClaimAssembler claimAssembler;

    @Test
    void testDoCreateClaim() throws Exception {

        ClaimEntity claimEntity = new ClaimEntity.ClaimEntityBuilder()
                .setUsername("doe")
                .setDocs(Arrays.asList("doc1", "doc2"))
                .setPolicyNumber("1111")
                .setAmount(new BigDecimal("100.00"))
                .setDateCreated("2020-12-20 12:10:00")
                .build();

        User user = new User();
        user.setId(1L);
        user.setUsername(claimEntity.getUsername());

        Claim claim = new Claim();
        claim.setUser(user);
        claim.setAmount(new BigDecimal("100.00"));
        claim.setSubmittedDocs(claimEntity.getDocs());
        claim.setClaimNumber("ABCD-123");
        claim.setDateCreated(LocalDateTime.parse("2020-12-20 12:10:00", ClaimAssembler.DATE_TIME_FORMATTER));

        when(claimService.createClaim(claimEntity)).thenReturn(claim);

        this.mockMvc.perform(post(BASE_URL + "/createClaim")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                        "\t\"amount\" : \"123\",\n" +
                        "\t\"policyNumber\": \"1234\",\n" +
                        "\t\"dateCreated\": \"2020-12-20 12:10:00\",\n" +
                        "\t\"username\": \"doe\",\n" +
                        "\t\"docs\": [\"file1\", \"file2\"]\n" +
                        "}")).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    void testDoCreateClaimFails() throws Exception {

        ClaimEntity claimEntity = new ClaimEntity.ClaimEntityBuilder().build();
        when(claimService.createClaim(claimEntity)).thenThrow(new ClaimProcessException(anyString()));
        this.mockMvc.perform(post(BASE_URL + "/createClaim")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                        "\t\"amount\" : \"123\",\n" +
                        "\t\"policyNumber\": \"1234\",\n" +
                        "\t\"dateCreated\": \"2020-12-20 12:10:00\",\n" +
                        "\t\"username\": \"doe\",\n" +
                        "\t\"docs\": [\"file1\", \"file2\"]\n" +
                        "}")).andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    void testDoGetClaim() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("doe");

        Claim claim1 = new Claim();
        claim1.setUser(user);
        claim1.setAmount(new BigDecimal("100.00"));
        claim1.setSubmittedDocs(Arrays.asList("doc1", "doc2"));
        claim1.setClaimNumber("ABCD-123");
        claim1.setDateCreated(LocalDateTime.parse("2020-12-20 12:10:00", ClaimAssembler.DATE_TIME_FORMATTER));

        Claim claim2 = new Claim();
        claim2.setUser(user);
        claim2.setAmount(new BigDecimal("100.00"));
        claim2.setSubmittedDocs(Arrays.asList("doc1", "doc2"));
        claim2.setClaimNumber("ABCD-123");
        claim2.setDateCreated(LocalDateTime.parse("2020-12-20 12:10:00", ClaimAssembler.DATE_TIME_FORMATTER));

        List<Claim> claimList = new ArrayList<>();
        claimList.add(claim1);
        claimList.add(claim2);

        when(claimService.getClaims(anyString())).thenReturn(claimList);
        this.mockMvc.perform(get(BASE_URL + "/getClaims")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{ \"username\": \"doe\"}"))
        .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void testDoUpdateClaim() throws Exception {

        ClaimEntity claimEntity = new ClaimEntity.ClaimEntityBuilder()
                .setUsername("doe")
                .setDocs(Arrays.asList("doc1", "doc2"))
                .setPolicyNumber("1111")
                .setAmount(new BigDecimal("100.00"))
                .setDateCreated("2020-12-20 12:10:00")
                .build();

        User user = new User();
        user.setId(1L);
        user.setUsername(claimEntity.getUsername());

        Claim claim = new Claim();
        claim.setUser(user);
        claim.setAmount(new BigDecimal("100.00"));
        claim.setSubmittedDocs(claimEntity.getDocs());
        claim.setClaimNumber("ABCD-123");
        claim.setDateCreated(LocalDateTime.parse("2020-12-20 12:10:00", ClaimAssembler.DATE_TIME_FORMATTER));

        when(claimService.updateClaim(claimEntity)).thenReturn(claim);
        this.mockMvc.perform(put(BASE_URL + "/updateClaim")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
                        "\t\"amount\" : \"999\",\n" +
                        "\t\"policyNumber\": \"9999\",\n" +
                        "\t\"dateCreated\": \"2020-12-21 12:10:00\",\n" +
                        "\t\"username\": \"doe\",\n" +
                        "\t\"docs\": [\"file1\", \"file2\"]\n" +
                        "}")).andExpect(status().is(HttpStatus.OK.value()));
    }
}
