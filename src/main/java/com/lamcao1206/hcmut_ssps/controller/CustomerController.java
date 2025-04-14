package com.lamcao1206.hcmut_ssps.controller;


import com.lamcao1206.hcmut_ssps.DTO.CustomerDTO;
import com.lamcao1206.hcmut_ssps.DTO.PaymentDTO;
import com.lamcao1206.hcmut_ssps.DTO.PrintOrderDTO;
import com.lamcao1206.hcmut_ssps.core.ResponseFactory;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.security.CustomUserDetails;
import com.lamcao1206.hcmut_ssps.service.CustomerService;
import com.lamcao1206.hcmut_ssps.service.PrintOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    
    @Autowired
    PrintOrderService printOrderService;
    
    @GetMapping("/")
    String sayHello() {
        return "Hello, customer";
    }
    
    @GetMapping("/{id}")
    ResponseEntity<?> findCustomerInfo(@PathVariable("id") Long id) {
        CustomerDTO customerResponseDTO = customerService.findCustomerById(id);
        return ResponseFactory.success("Fetch customer information success", customerResponseDTO);
    }
    
    @PostMapping("/order")
    ResponseEntity<?> createPrintOrder(
            @RequestPart("config")PrintOrderDTO printOrderDTO,
            @RequestPart("file")MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {
        Long customerId = userDetails.getId();
        return ResponseFactory.created("Upload file successfully", printOrderService.createOrder(printOrderDTO, file, customerId));
    }
    
    @GetMapping("/purchase")
    ResponseEntity<?> getPurchaseHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {
        Long customerId = userDetails.getId();
        Customer customer = (Customer) userDetails.getUser();
        return ResponseFactory.success("Fetch purchase history successfully!", customerService.findPaymentsByCustomer(customer));
    }
    
    @PostMapping("/purchase")
    ResponseEntity<?> createNewPurchase(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PaymentDTO dto
    ) throws IOException {
        Customer customer = (Customer) userDetails.getUser();
        return ResponseFactory.created("Payment created", customerService.createPayment(dto, customer));
    }
    
}
