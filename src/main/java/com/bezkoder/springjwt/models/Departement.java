package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Departement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_departement;
    private String libelle ;
    private int maxSaturation;
    private int nbreEmpl;
    @OneToMany(mappedBy="departement",fetch = FetchType.EAGER)
    @JsonIgnore

    Set<Employee> employees;

}
