package com.e.auction.api.repository;

import com.e.auction.api.model.ShippingCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShippingCostRepository extends JpaRepository<ShippingCost, Long> , JpaSpecificationExecutor<ShippingCost> {

}
