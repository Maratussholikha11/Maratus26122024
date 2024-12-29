package com.nutech.mar.repository;

import com.nutech.mar.model.Service;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query(value = "SELECT * FROM tb_service WHERE service_code = :code", nativeQuery = true)
    Service findByServiceCode(@Param("code") String code);

    @Query(value = "SELECT * FROM tb_service", nativeQuery = true)
    List<Service> getAll();
}
