package br.edu.cs.poo.ac.seguro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
//@AllArgsConstructor
@RequiredArgsConstructor
public class Apolice implements Serializable {

	private static final long serialVersionUID = 1L;
	private String numero;
    @NonNull private Veiculo veiculo;
    @NonNull private BigDecimal valorFranquia;
    @NonNull private BigDecimal valorPremio;
    @NonNull private BigDecimal valorMaximoSegurado;

}