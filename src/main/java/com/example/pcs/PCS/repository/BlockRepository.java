package com.example.pcs.PCS.repository;

import com.example.pcs.PCS.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findByPartyAAndPartyB(String partyA, String partyB);
}
