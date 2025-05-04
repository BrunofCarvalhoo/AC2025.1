package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {
    private SeguradoPessoaDAO dao = new SeguradoPessoaDAO();
    private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private static final SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();

    private SeguradoPessoaMediator() {}

    public static SeguradoPessoaMediator getInstancia() {
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF deve ser informado";
        }
        if (cpf.length() != 11) {
            return "CPF deve ter 11 caracteres";
        }
        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF com dígito inválido";
        }
        return null;
    }

    public String validarRenda(double renda) {
        if (renda < 0) {
            return "Renda deve ser maior ou igual à zero";
        }
        return null;
    }

    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        if (seg == null) {
            return "Segurado inválido";
        }

        String msg = validarCpf(seg.getCpf());
        if (msg != null) return msg;

        msg = validarRenda(seg.getRenda());
        if (msg != null) return msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        if (seg.getDataNascimento() == null) {
            return "Data do nascimento deve ser informada";
        }

        return null;
    }

    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        if (dao.buscar(seg.getCpf()) != null) {
            return "CPF do segurado pessoa já existente";
        }

        boolean ok = dao.incluir(seg);
        if (!ok) {
            return "Erro na inclusão";
        }
        return null;
    }

    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) return msg;

        if (dao.buscar(seg.getCpf()) == null) {
            return "CPF do segurado pessoa não existente";
        }

        boolean ok = dao.alterar(seg);
        if (!ok) {
            return "Erro na alteração";
        }
        return null;
    }

    public String excluirSeguradoPessoa(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF deve ser informado";
        }

        if (dao.buscar(cpf) == null) {
            return "CPF do segurado pessoa não existente";
        }

        boolean ok = dao.excluir(cpf);
        if (!ok) {
            return "Erro na exclusão";
        }
        return null;
    }

    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return null;
        }
        return dao.buscar(cpf);
    }
}