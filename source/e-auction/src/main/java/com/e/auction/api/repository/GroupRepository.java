package com.e.auction.api.repository;

import com.e.auction.api.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Group findFirstByName(String name);

    Group findFirstByKind(Integer kind);
}
