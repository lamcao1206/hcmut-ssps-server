package com.lamcao1206.hcmut_ssps.service;

import com.lamcao1206.hcmut_ssps.DTO.PrintOrderDTO;
import com.lamcao1206.hcmut_ssps.entity.Customer;
import com.lamcao1206.hcmut_ssps.entity.Document;
import com.lamcao1206.hcmut_ssps.entity.PrintOrder;
import com.lamcao1206.hcmut_ssps.entity.Printer;
import com.lamcao1206.hcmut_ssps.entity.enums.OrderStatus;
import com.lamcao1206.hcmut_ssps.exception.NotFoundException;
import com.lamcao1206.hcmut_ssps.repository.CustomerRepository;
import com.lamcao1206.hcmut_ssps.repository.DocumentRepository;
import com.lamcao1206.hcmut_ssps.repository.PrintOrderRepository;
import com.lamcao1206.hcmut_ssps.repository.PrinterRepository;
import com.lamcao1206.hcmut_ssps.util.CloudStorageUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PrintOrderService {
    @Autowired
    private PrintOrderRepository printOrderRepository;
    
    @Autowired
    private PrinterRepository printerRepository;
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional
    public PrintOrderDTO createOrder(PrintOrderDTO orderDTO, MultipartFile file, Long customerId) throws IOException, RuntimeException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        PrintOrder printOrder = PrintOrder.builder()
                .status(OrderStatus.PENDING)
                .orientation(orderDTO.getOrientation())
                .numCopies(orderDTO.getNumCopies())
                .customer(customer)
                .build();

        if (orderDTO.getPrinterId() != null) {
            Printer printer = printerRepository.findById(orderDTO.getPrinterId())
                    .orElseThrow(() -> new NotFoundException("Printer not found"));
            printOrder.setPrinter(printer);
        }

        printOrderRepository.save(printOrder);

        String gcsFileName = "documents/print_order_" + printOrder.getId();
        String gcsUrl = CloudStorageUtil.uploadFile(file, gcsFileName);

        Document document = new Document();
        document.setDocumentName(file.getOriginalFilename());
        document.setDocumentType(FilenameUtils.getExtension(file.getOriginalFilename()));
        document.setPage(orderDTO.getPage());
        document.setUrl(gcsUrl);
        document.setOrder(printOrder);

        documentRepository.save(document);

        printOrder.setDocument(document);
        printOrderRepository.save(printOrder);
        
        return modelMapper.map(printOrder, PrintOrderDTO.class);
        
    }
    
}
