package com.e.auction.api.repository;

import com.e.auction.api.model.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {
    Page<Auction> findAllByWinnerIdAndStatus(Long winnerId, Integer status, Pageable pageable);
}
