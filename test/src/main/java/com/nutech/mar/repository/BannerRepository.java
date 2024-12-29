package com.nutech.mar.repository;

import com.nutech.mar.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Integer> {

    @Query(value = "SELECT * FROM tb_banner", nativeQuery = true)
    List<Banner> getAll();
}
