package br.edu.cs.poo.ac.seguro.daos;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaDAO extends DAOGenerico<SeguradoEmpresa>{
	
	@Override
	protected Class<SeguradoEmpresa> getClasseEntidade(){
		return SeguradoEmpresa.class;
	}
	public SeguradoEmpresa buscar(String cnpj) {
        return (SeguradoEmpresa) super.buscar(cnpj);
    }
	
}
