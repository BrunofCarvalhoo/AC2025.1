package br.edu.cs.poo.ac.seguro.daos;

import java.io.Serializable;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;
import br.edu.cs.poo.ac.seguro.entidades.Registro;

public abstract class DAOGenerico<T extends Registro> {

    private CadastroObjetos cadastro;

    public DAOGenerico() {
        this.cadastro = new CadastroObjetos(getClasseEntidade());
    }

    protected abstract Class<T> getClasseEntidade();

    public boolean incluir(T obj) {
        if (obj == null || obj.getIdUnico() == null) {
            return false;
        }
        if (buscar(obj.getIdUnico()) != null) {
            return false; 
        }
        cadastro.incluir((Serializable) obj, obj.getIdUnico());
        return true;
    }

    public boolean alterar(T obj) {
        if (obj == null || obj.getIdUnico() == null) {
            return false;
        }
        if (buscar(obj.getIdUnico()) == null) {
            return false; 
        }
        cadastro.alterar((Serializable) obj, obj.getIdUnico());
        return true;
    }

    @SuppressWarnings("unchecked")
    public T buscar(String id) {
        if (id == null) {
            return null;
        }
        return (T) cadastro.buscar(id);
    }

    public boolean excluir(String id) {
        if (id == null || buscar(id) == null) {
            return false; 
        }
        cadastro.excluir(id);
        return true;
    }

    public Registro[] buscarTodos() {
        return (Registro[]) cadastro.buscarTodos();
    }
}
