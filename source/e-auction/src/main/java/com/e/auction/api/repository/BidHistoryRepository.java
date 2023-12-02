package com.e.auction.api.repository;

import com.e.auction.api.model.BidHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BidHistoryRepository extends JpaRepository<BidHistory, Long>, JpaSpecificationExecutor<BidHistory> {
}
