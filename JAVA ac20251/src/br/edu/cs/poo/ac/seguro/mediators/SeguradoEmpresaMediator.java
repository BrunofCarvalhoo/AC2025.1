package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {
    private static final SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();
    private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private SeguradoEmpresaDAO dao = new SeguradoEmpresaDAO();

    private SeguradoEmpresaMediator() {}

    public static SeguradoEmpresaMediator getInstancia() {
        return instancia;
    }

    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj) || !ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ inválido";
        }
        return null;
    }

    public String validarFaturamento(double faturamento) {
        if (faturamento < 0) {
            return "Faturamento inválido";
        }
        return null;
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        boolean incluiu = dao.incluir(seg);
        return incluiu ? null : "Segurado empresa já existe";
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        boolean alterou = dao.alterar(seg);
        return alterou ? null : "Segurado empresa não existente";
    }

    public String excluirSeguradoEmpresa(String cnpj) {
        boolean excluiu = dao.excluir(cnpj);
        return excluiu ? null : "Segurado empresa não encontrado";
    }

    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return dao.buscar(cnpj);
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        if (seg == null) {
            return "Segurado empresa nulo";
        }
        String msg = validarCnpj(seg.getCnpj());
        if (msg != null) return msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        return validarFaturamento(seg.getFaturamento());
    }
}
