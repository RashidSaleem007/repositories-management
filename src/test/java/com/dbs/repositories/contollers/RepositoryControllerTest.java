package com.dbs.repositories.contollers;

import com.dbs.repositories.dto.OwnerRequest;
import com.dbs.repositories.dto.OwnerResponse;
import com.dbs.repositories.dto.RepositoryRequest;
import com.dbs.repositories.dto.RepositoryResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private static HttpHeaders headers;
    private static String repositoryName;
    private static String repositoryDesc;
    private static String cloneUrl;
    private static int stars;
    private static Long repoId;
    private static Long ownerId;
    private final static String serviceRepo = "repositories";
    private final static String serviceOwner = "owners";

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        repositoryDesc = "testing repo";
        cloneUrl = "localhost";
        stars = 4;
    }

    @Test
    @Order(1)
    public void createRepositoryTest() {
        final int ranNum = TestCasesUtilities.createRanNumber();

        final String ownerUrl = TestCasesUtilities.createURLWithPort(port, serviceOwner);
        final String name = "Owner " + ranNum;
        final String email = "owner" + ranNum + "@gmail.com";
        final OwnerRequest ownerRequest = new OwnerRequest(name, email);
        final HttpEntity<OwnerRequest> ownerRequestHttpEntity = new HttpEntity<>(ownerRequest, headers);
        final ResponseEntity<OwnerResponse> responseResponseEntity = restTemplate.exchange(ownerUrl, HttpMethod.POST, ownerRequestHttpEntity, OwnerResponse.class);
        ownerId = Objects.requireNonNull(responseResponseEntity.getBody()).getId();

        final String url = TestCasesUtilities.createURLWithPort(port, serviceRepo);
        repositoryName = "Repo-" + ranNum;
        final RepositoryRequest repositoryRequest = new RepositoryRequest(repositoryName, repositoryDesc, cloneUrl, stars, ownerId);
        final HttpEntity<RepositoryRequest> entity = new HttpEntity<>(repositoryRequest, headers);
        final ResponseEntity<RepositoryResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, RepositoryResponse.class);
        final RepositoryResponse repositoryResponse = response.getBody();
        assert repositoryResponse != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(repositoryResponse.getRepositoryName(), repositoryName);
        repoId = repositoryResponse.getId();
    }

    @Test
    @Order(2)
    public void updateRepositoryTest() {
        final int ranNum = TestCasesUtilities.createRanNumber();
        final String url = TestCasesUtilities.createURLWithPort(port, serviceRepo);
        repositoryName = "Repo-" + ranNum;
        final RepositoryRequest repositoryRequest = new RepositoryRequest(repositoryName, repositoryDesc, cloneUrl, stars, ownerId);
        final HttpEntity<RepositoryRequest> entity = new HttpEntity<>(repositoryRequest, headers);
        final ResponseEntity<RepositoryResponse> response = restTemplate.exchange(url + "/" + repoId, HttpMethod.PUT, entity, RepositoryResponse.class);
        final RepositoryResponse repositoryResponse = response.getBody();
        assert repositoryResponse != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(repositoryResponse.getRepositoryName(), repositoryName);
    }

    @Test
    @Order(3)
    public void getAllRepositoriesTest() {
        final String url = TestCasesUtilities.createURLWithPort(port, serviceRepo);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<List<RepositoryResponse>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
        final List<RepositoryResponse> repositoryResponses = response.getBody();
        assert repositoryResponses != null;
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    @Order(4)
    public void getRepositoryByIdTest() {
        final String url = TestCasesUtilities.createURLWithPort(port, serviceRepo);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<RepositoryResponse> response = restTemplate.exchange(url + "/" + repoId, HttpMethod.GET, entity, RepositoryResponse.class);
        final RepositoryResponse repositoryResponse = response.getBody();
        assert repositoryResponse != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(repositoryResponse.getRepositoryName(), repositoryName);
    }

    @Test
    @Order(5)
    public void deleteRepositoryByIdTest() {
        final String url = TestCasesUtilities.createURLWithPort(port, serviceRepo);
        final HttpEntity<String> entity = new HttpEntity<>(null, headers);
        final ResponseEntity<Boolean> response = restTemplate.exchange(url + "/" + repoId, HttpMethod.DELETE, entity, Boolean.class);
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody(), Boolean.TRUE);

        final String ownerUrl = TestCasesUtilities.createURLWithPort(port, serviceOwner);
        final HttpEntity<String> stringHttpEntity = new HttpEntity<>(null, headers);
        final ResponseEntity<Boolean> responseEntity = restTemplate.exchange(ownerUrl + "/" + ownerId, HttpMethod.DELETE, stringHttpEntity, Boolean.class);
        assertEquals(responseEntity.getStatusCode().value(), 200);
        assertEquals(responseEntity.getBody(), Boolean.TRUE);
    }

}
