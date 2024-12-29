package com.nutech.mar.controller;

import com.nutech.mar.config.JwtTokenUtil;
import com.nutech.mar.dto.ApiResponseDto;
import com.nutech.mar.dto.BalanceDto;
import com.nutech.mar.dto.RequestDto;
import com.nutech.mar.dto.TransactionDto;
import com.nutech.mar.model.Service;
import com.nutech.mar.model.Transaction;
import com.nutech.mar.model.User;
import com.nutech.mar.repository.ServiceRepository;
import com.nutech.mar.repository.TransactionRepository;
import com.nutech.mar.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping
public class TransactionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository trxReposiotory;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = "/topup")
    public ApiResponseDto topup(@Valid @RequestBody RequestDto requestDto, HttpServletRequest request){
        if (requestDto.getTopUpAmount().compareTo(BigDecimal.ZERO) < 0){
            return new ApiResponseDto().custom(102, "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0");
        }else{
            final String requestTokenHeader = request.getHeader("Authorization");

            ApiResponseDto apiResponseDto = new ApiResponseDto();
            String username = null;
            String jwtToken = null;

            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (ExpiredJwtException e) {
                    System.out.println("Unable to get username from JWT token");
                }
            } else {
                System.out.println("JWT Token does not begin with Bearer String");
            }

            if(username != null){
                User user = this.userRepository.findByUsername(username);
                BalanceDto balanceDto = new BalanceDto();
                BigDecimal newBalance = new BigDecimal(0);
                if(user != null){
                    newBalance = user.getBalance().add(requestDto.getTopUpAmount()); // lalu bagaimana cara mengambil value angkanya?
                    user.setBalance(newBalance);
                    this.userRepository.save(user);
                    this.saveTopupTransaction(user.getUserId(), requestDto.getTopUpAmount());
                    balanceDto.setBalance(newBalance);
                }
                apiResponseDto =  new ApiResponseDto().custom(0, "Top Up Balance berhasil", balanceDto);
            }
            return apiResponseDto;
        }
    }

    private void saveTopupTransaction(Integer userId, BigDecimal amount){
        Random random = new Random();
        int rdm = random.nextInt(1000);

        Transaction trx = new Transaction();
        trx.setUserId(userId);
        trx.setTransactionType("TOPUP");
        trx.setCreatedOn(LocalDateTime.now());
        trx.setDescription("Top Up Balance");
        trx.setTotalAmount(amount);
        trx.setInvoiceNumber("INV"+trx.getTransactionType()+rdm);
        this.trxReposiotory.save(trx);
    }

    @PostMapping(value = "/transaction")
    public ApiResponseDto transaction(@RequestBody RequestDto requestDto, HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("Unable to get username from JWT token");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if(username != null){
            Service service = this.serviceRepository.findByServiceCode(requestDto.getServiceCode());
            if (service != null){
                User user = this.userRepository.findByUsername(username);
                if (user.getBalance().compareTo(service.getServiceTarif()) < 0){
                    return new ApiResponseDto().custom(102, "Balance tidak cukup");
                }else{
                    Random random = new Random();
                    int rdm = random.nextInt(1000);

                    Transaction trx = new Transaction();
                    trx.setTotalAmount(service.getServiceTarif());
                    trx.setUserId(user.getUserId());
                    trx.setTransactionType("PAYMENT");
                    trx.setCreatedOn(LocalDateTime.now());
                    trx.setInvoiceNumber("INV"+trx.getTransactionType()+rdm);
                    trx.setServiceCode(requestDto.getServiceCode());
                    trx.setServiceName(service.getServiceName());
                    this.trxReposiotory.save(trx);

                    TransactionDto trxDto = new TransactionDto();
                    trxDto.setTransactionType(trx.getTransactionType());
                    trxDto.setCreatedOn(trx.getCreatedOn());
                    trxDto.setInvoiceNumber(trx.getInvoiceNumber());
                    trxDto.setServiceCode(trx.getServiceCode());
                    trxDto.setTotalAmount(trx.getTotalAmount());
                    trxDto.setServiceName(trx.getServiceName());

                    BigDecimal newBalance = user.getBalance().subtract(trx.getTotalAmount());
                    user.setBalance(newBalance);
                    this.userRepository.save(user);

                    apiResponseDto =  new ApiResponseDto().custom(0, "Transaksi Berhasil", trxDto);
                }
            }else{
                return new ApiResponseDto().custom(102, "Service atau Layanan tidak ditemukan");
            }
                    }
        return apiResponseDto;
    }

    @GetMapping("/transaction/history")
    public ApiResponseDto getBalance(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "0") int limit
    ){
        final String requestTokenHeader = request.getHeader("Authorization");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("Unable to get username from JWT token");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if(username != null){
            User user = this.userRepository.findByUsername(username);
            List<Transaction> trxList = new ArrayList<>();
            List<TransactionDto> trxDtoList = new ArrayList<>();
            if(user != null){
                if (limit == 0){
                    trxList = this.trxReposiotory.getAll(user.getUserId());
                }else{
                    trxList = this.trxReposiotory.getAll(limit, offset, user.getUserId());
                }
            }

            for (Transaction trx : trxList){
                TransactionDto trxDto = new TransactionDto();
                trxDto.setInvoiceNumber(trx.getInvoiceNumber());
                trxDto.setTransactionType(trx.getTransactionType());
                trxDto.setTotalAmount(trx.getTotalAmount());
                trxDto.setCreatedOn(trx.getCreatedOn());
                trxDtoList.add(trxDto);
            }

            apiResponseDto =  new ApiResponseDto().custom(0, "Get History Berhasil", trxDtoList);
        }
        return apiResponseDto;
    }

    @GetMapping("/balance")
    public ApiResponseDto getBalance(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");

        ApiResponseDto apiResponseDto = new ApiResponseDto();
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("Unable to get username from JWT token");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        if(username != null){
            User user = this.userRepository.findByUsername(username);
            BalanceDto balance = new BalanceDto();
            if(user != null){
                balance.setBalance(user.getBalance());
            }
            apiResponseDto =  new ApiResponseDto().custom(0, "Get Balance Berhasil", balance);
        }
        return apiResponseDto;
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0";
        return new ApiResponseDto().custom(102, errorMessage, null);
    }

}
