package com.bezkoder.springjwt;

import com.bezkoder.springjwt.Service.ServiceClient;
import com.bezkoder.springjwt.models.Paiment;
import com.bezkoder.springjwt.notifdto.MonthlyPaymentDTO;
import com.bezkoder.springjwt.repository.PaimentRep;
import com.twilio.rest.api.v2010.account.call.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@SpringBootTest

public class ServiceClientTestMock {
    @InjectMocks
    private ServiceClient clientService;

    @Mock
    private PaimentRep paimentRepository;

    @Test
    public void testGetTotalPaymentsByMonth() {
        MonthlyPaymentDTO payment1 = new MonthlyPaymentDTO(10, 2024, BigDecimal.valueOf(300));
        MonthlyPaymentDTO payment2 = new MonthlyPaymentDTO(10, 2024, BigDecimal.valueOf(500));

        when(paimentRepository.getTotalPaymentsByMonth())
                .thenReturn(new ArrayList<>(Arrays.asList(payment1, payment2)));

        List<MonthlyPaymentDTO> totalPayments = clientService.getTotalPaymentsByMonth();

        List<MonthlyPaymentDTO> nonZeroPayments = totalPayments.stream()
                .filter(payment -> payment.getTotalPayment().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        assertEquals(2, nonZeroPayments.size());
        assertEquals(BigDecimal.valueOf(300), nonZeroPayments.get(0).getTotalPayment());
        assertEquals(BigDecimal.valueOf(500), nonZeroPayments.get(1).getTotalPayment());

        verify(paimentRepository).getTotalPaymentsByMonth();
    }
}