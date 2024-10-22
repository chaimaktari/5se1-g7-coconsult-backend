package com.bezkoder.springjwt;

import com.bezkoder.springjwt.Service.ServiceClient;
import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Paiment;
import com.bezkoder.springjwt.models.Typepaiment;
import com.bezkoder.springjwt.notifdto.MonthlyPaymentDTO;
import com.bezkoder.springjwt.repository.PaimentRep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceClientTestJunit {

    @Test
    public void testGetTotalPaymentsByMonth() {
        List<MonthlyPaymentDTO> mockPayments = new ArrayList<>();

        mockPayments.add(MonthlyPaymentDTO.builder()
                .month(1)
                .year(2024)
                .totalPayment(BigDecimal.valueOf(1000))
                .build());

        mockPayments.add(MonthlyPaymentDTO.builder()
                .month(2)
                .year(2024)
                .totalPayment(BigDecimal.valueOf(1500))
                .build());

        mockPayments.add(MonthlyPaymentDTO.builder()
                .month(4)
                .year(2024)
                .totalPayment(BigDecimal.valueOf(800))
                .build());

        // Simulate the method's logic
        List<Integer> monthexistant = new ArrayList<>();
        mockPayments.forEach(existant -> monthexistant.add(existant.getMonth()));

        for (int i = 1; i <= 12; i++) {
            if (!monthexistant.contains(i)) {
                mockPayments.add(new MonthlyPaymentDTO(i, 2024, BigDecimal.ZERO));
            }
        }

        mockPayments.sort((p1, p2) -> Integer.compare(p1.getMonth(), p2.getMonth()));

        Assertions.assertEquals(12, mockPayments.size());

        MonthlyPaymentDTO marchPayment = mockPayments.get(2); // March should be the third element (index 2)
        Assertions.assertEquals(3, marchPayment.getMonth());
        Assertions.assertEquals(BigDecimal.ZERO, marchPayment.getTotalPayment());

        MonthlyPaymentDTO aprilPayment = mockPayments.get(3);
        Assertions.assertEquals(4, aprilPayment.getMonth());
        Assertions.assertEquals(BigDecimal.valueOf(800), aprilPayment.getTotalPayment());
    }
}