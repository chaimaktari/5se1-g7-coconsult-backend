package com.bezkoder.springjwt.notifdto;

import lombok.*;
import org.hibernate.grammars.hql.HqlParser;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyPaymentDTO implements Serializable {
    private int month;
    private int year;
    private BigDecimal totalPayment;


}
