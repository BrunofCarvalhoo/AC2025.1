package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {
	 private static final SeguradoMediator sm = new SeguradoMediator();
	 
	public String validarNome(String nome) {
		if (StringUtils.ehNuloOuBranco(nome)) {
			return "Nome inválido";
		}
		return null;
	}

	public String validarEndereco(Endereco endereco) {
		if (endereco == null) {
			return "Endereço inválido";
		}
		return null;
	}

	public String validarDataCriacao(LocalDate dataCriacao) {
		if (dataCriacao == null || dataCriacao.isAfter(LocalDate.now())) {
			return "Data de criação inválida";
		}
		return null;
	}

	public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
		if (bonus == null || valorDebito == null) {
			return valorDebito;
		}
		BigDecimal novoValor = valorDebito.subtract(bonus);
		return novoValor.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : novoValor;
	}

	public static SeguradoMediator getInstancia() {
		return sm ;
	}
}
