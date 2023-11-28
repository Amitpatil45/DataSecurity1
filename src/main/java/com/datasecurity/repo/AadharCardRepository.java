package com.datasecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.datasecurity.entity.AadharCard;
import com.datasecurity.entity.AadharCardEntity;

public interface AadharCardRepository extends JpaRepository<AadharCard, Long> {

	void save(AadharCardEntity aadharCardEntity);
    // Add custom queries if needed
}