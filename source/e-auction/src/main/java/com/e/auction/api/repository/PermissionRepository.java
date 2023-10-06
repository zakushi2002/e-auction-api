package com.e.auction.api.repository;

import com.e.auction.api.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    Permission findFirstByName(String name);

    Permission findByPermissionCode(String permissionCode);
}
