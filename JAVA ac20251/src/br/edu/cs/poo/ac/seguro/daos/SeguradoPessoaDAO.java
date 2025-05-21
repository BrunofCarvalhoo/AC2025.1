package br.edu.cs.poo.ac.seguro.daos;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaDAO extends DAOGenerico<SeguradoPessoa> {
	@Override
    protected Class<SeguradoPessoa> getClasseEntidade() {
        return SeguradoPessoa.class;
    }
	public SeguradoPessoa buscar(String cpf) {
        return (SeguradoPessoa) super.buscar(cpf);
    }
}