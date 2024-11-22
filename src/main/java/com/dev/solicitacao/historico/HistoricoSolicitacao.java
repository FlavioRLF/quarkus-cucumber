package com.dev.solicitacao.historico;

import com.dev.solicitacao.Solicitacao;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class HistoricoSolicitacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Solicitacao solicitacao;
}
