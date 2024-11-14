package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_note ;
    private float note ;
    @Enumerated(EnumType.STRING)
    private critereNote critere ;
    private Long idUser;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Employee employeee;


}
