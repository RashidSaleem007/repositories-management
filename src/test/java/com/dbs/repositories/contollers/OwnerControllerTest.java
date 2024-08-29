package com.dbs.repositories.contollers;

import com.dbs.repositories.dto.OwnerRequest;
import com.dbs.repositories.dto.OwnerResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;
    private static String name;
    private static String email;
    private static Long ownerId;
    private final static String service = "owners";

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    public void createOwnerTest() {
        final int ranNum = TestCasesUtilities.createRanNumber();
        name = "Owner " + ranNum;
        email = "owner" + ranNum + "@gmail.com";
        final String url = TestCasesUtilities.createURLWithPort(port, service);
        final OwnerRequest ownerRequest = new OwnerRequest(name, email);
        final HttpEntity<OwnerRequest> entity = new HttpEntity<>(ownerRequest, headers);
        final ResponseEntity<OwnerResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, OwnerResponse.class);
        final OwnerResponse ownerResponse = response.getBody();
        assert ownerResponse != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(ownerResponse.getOwnerName(), name);
        ownerId = ownerResponse.getId();
    }

    @Test
    @Order(2)
    public void updateOwnerTest() {
        final int ranNum = TestCasesUtilities.createRanNumber();
        name = "Owner " + ranNum;
        email = "owner" + ranNum + "@gmail.com";
        final String url = TestCasesUtilities.createURLWithPort(port, service);
        final OwnerRequest ownerRequest = new OwnerRequest(name, email);
        final HttpEntity<OwnerRequest> entity = new HttpEntity<>(ownerRequest, headers);
        final ResponseEntity<OwnerResponse> response = restTemplate.exchange(url + "/" + ownerId, HttpMethod.PUT, entity, OwnerResponse.class);
        final OwnerResponse ownerResponse = response.getBody();
        assert ownerResponse != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(ownerResponse.getOwnerName(), name);
    }

    @Test
    @Order(3)
    public void getAllOwnersTest() {
        final String url = TestCasesUtilities.createURLWithPort(port, service);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<OwnerResponse>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
        final List<OwnerResponse> ownerEntities = response.getBody();
        assert ownerEntities != null;
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    @Order(4)
    public void getOwnerByIdTest() {
        final String url = TestCasesUtilities.createURLWithPort(port, service);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<OwnerResponse> response = restTemplate.exchange(url + "/" + ownerId, HttpMethod.GET, entity, OwnerResponse.class);
        final OwnerResponse ownerResponse = response.getBody();
        assert ownerResponse != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(ownerResponse.getOwnerName(), name);
    }

    @Test
    @Order(5)
    public void deleteOwnerByIdTest() {
        final String url = TestCasesUtilities.createURLWithPort(port, service);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<Boolean> response = restTemplate.exchange(url + "/" + ownerId, HttpMethod.DELETE, entity, Boolean.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody(), Boolean.TRUE);
    }

}
