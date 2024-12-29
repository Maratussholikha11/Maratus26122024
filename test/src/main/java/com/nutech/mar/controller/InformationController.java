package com.nutech.mar.controller;


import com.nutech.mar.dto.ApiResponseDto;
import com.nutech.mar.model.Banner;
import com.nutech.mar.model.Service;
import com.nutech.mar.repository.BannerRepository;
import com.nutech.mar.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class InformationController {

    @Autowired
    BannerRepository bannerRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping(value = "/banner")
    public ApiResponseDto getBannerList(){
        List<Banner> banner = this.bannerRepository.getAll();
        return new ApiResponseDto().custom(0, "Sukses", banner);
    }

    @GetMapping(value = "/service")
    public ApiResponseDto getServiceList(){
        List<Service> serviceList = this.serviceRepository.getAll();
        return new ApiResponseDto().custom(0, "Sukses", serviceList);
    }
}
