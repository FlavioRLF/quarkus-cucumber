package com.dev.solicitacao;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class Solicitacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String titulo;

    private String descricao;

    private LocalDateTime data;

}
