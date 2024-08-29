package com.dbs.repositories.services;

import com.dbs.repositories.dto.OwnerRequest;
import com.dbs.repositories.dto.OwnerResponse;

import java.util.List;

public interface OwnerService {

    OwnerResponse createOwner(final OwnerRequest ownerRequestBody);

    OwnerResponse updateOwner(final OwnerRequest ownerRequestBody, final Long ownerId);

    OwnerResponse getOwnerById(final Long ownerId);

    List<OwnerResponse> getAllOwners();

    Boolean deleteOwner(final Long ownerId);
}
