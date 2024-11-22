package com.dev.customer.dtos.requisicao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RequisicaoCriaCustomer implements Serializable {

    private String name;

    private String email;

    private String address;

    private String phone;
}
