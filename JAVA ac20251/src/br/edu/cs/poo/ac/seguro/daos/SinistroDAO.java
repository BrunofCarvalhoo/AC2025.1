package br.edu.cs.poo.ac.seguro.daos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;

public class SinistroDAO extends DAOGenerico<Sinistro> {
    
	@Override
    protected Class<Sinistro> getClasseEntidade() {
        return Sinistro.class;
    }
}
