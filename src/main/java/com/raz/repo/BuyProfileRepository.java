package com.raz.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raz.entity.BuyProfile;

@Repository
public interface BuyProfileRepository extends JpaRepository<BuyProfile,Long> {

}
