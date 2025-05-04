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
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado";
        }
        if (cnpj.length() != 14) {
            return "CNPJ deve ter 14 caracteres";
        }
        if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ com dígito inválido";
        }
        return null;
    }

    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0) {
            return "Faturamento deve ser maior que zero";
        }
        return null;
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        if (seg == null) {
            return "Segurado inválido";
        }

        String msg = validarCnpj(seg.getCnpj());
        if (msg != null) return msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        if (seg.getDataAbertura() == null) {
            return "Data da abertura deve ser informada";
        }

        return validarFaturamento(seg.getFaturamento());
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        if (dao.buscar(seg.getCnpj()) != null) {
            return "CNPJ do segurado empresa já existente";
        }

        boolean ok = dao.incluir(seg);
        if (!ok) {
            return "Erro na inclusão";
        }
        return null;
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) return msg;

        if (dao.buscar(seg.getCnpj()) == null) {
            return "CNPJ do segurado empresa não existente";
        }

        boolean ok = dao.alterar(seg);
        if (!ok) {
            return "Erro na alteração";
        }
        return null;
    }

    public String excluirSeguradoEmpresa(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado";
        }

        if (dao.buscar(cnpj) == null) {
            return "CNPJ do segurado empresa não existente";
        }

        boolean ok = dao.excluir(cnpj);
        if (!ok) {
            return "Erro na exclusão";
        }
        return null;
    }

    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return null;
        }
        return dao.buscar(cnpj);
    }
}