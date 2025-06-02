package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;

import java.math.BigDecimal;

public class SinistroMediator {

    private VeiculoDAO daoVeiculo = new VeiculoDAO();
    private ApoliceDAO daoApolice = new ApoliceDAO();
    private SinistroDAO daoSinistro = new SinistroDAO();

    private static SinistroMediator instancia;

    public static SinistroMediator getInstancia() {
        if (instancia == null)
            instancia = new SinistroMediator();
        return instancia;
    }

    private SinistroMediator() {}

    public String incluirSinistro(DadosSinistro dados, LocalDateTime dataHoraAtual) throws ExcecaoValidacaoDados {
        ExcecaoValidacaoDados excecao = new ExcecaoValidacaoDados();

        if (dados == null) {
            excecao.getMensagens().add("Dados do sinistro não podem ser nulos.");
        } else {
            if (dados.getDataHoraSinistro() == null) {
                excecao.getMensagens().add("Data/hora do sinistro não pode ser nula.");
            } else if (!dados.getDataHoraSinistro().isBefore(dataHoraAtual)) {
                excecao.getMensagens().add("Data/hora do sinistro deve ser anterior à data/hora atual.");
            }

            if (dados.getPlaca() == null || dados.getPlaca().isBlank()) {
                excecao.getMensagens().add("Placa não pode ser nula ou vazia.");
            }

            Veiculo veiculo = null;
            if (dados.getPlaca() != null && !dados.getPlaca().isBlank()) {
                veiculo = daoVeiculo.buscar(dados.getPlaca());
                if (veiculo == null) {
                    excecao.getMensagens().add("Veículo com a placa informada não está cadastrado.");
                }
            }

            if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().isBlank()) {
                excecao.getMensagens().add("Usuário de registro não pode ser nulo ou vazio.");
            }

            if (dados.getValorSinistro() <= 0) {
                excecao.getMensagens().add("Valor do sinistro deve ser maior que zero.");
            }

            TipoSinistro[] tipos = TipoSinistro.values();
            if (dados.getCodigoTipoSinistro() < 0 || dados.getCodigoTipoSinistro() >= tipos.length) {
                excecao.getMensagens().add("Código do tipo de sinistro inválido.");
            }
        }

        // Se houver erros, lança exceção
        if (!excecao.getMensagens().isEmpty()) {
            throw excecao;
        }

        // Buscar apólice vigente para o veículo
        List<Apolice> apolices = daoApolice.buscarTodos();
        Apolice apoliceCobrindo = null;
        for (Apolice apolice : apolices) {
            LocalDateTime fimVigencia = apolice.getInicioVigencia().plusYears(1);
            if (apolice.getVeiculo().getPlaca().equals(dados.getPlaca()) &&
                !dados.getDataHoraSinistro().isBefore(apolice.getInicioVigencia()) &&
                dados.getDataHoraSinistro().isBefore(fimVigencia)) {
                apoliceCobrindo = apolice;
                break;
            }
        }

        if (apoliceCobrindo == null) {
            excecao.getMensagens().add("Não há apólice vigente para o veículo na data do sinistro.");
            throw excecao;
        }

        if (BigDecimal.valueOf(dados.getValorSinistro()).compareTo(apoliceCobrindo.getValorMaximoCobertura()) > 0) {
            excecao.getMensagens().add("Valor do sinistro maior que o valor máximo segurado da apólice.");
            throw excecao;
        }

        // Gerar sequencial
        List<Sinistro> sinistros = daoSinistro.buscarTodos();
        List<Sinistro> sinistrosMesmoNumero = new ArrayList<>();

        for (Sinistro s : sinistros) {
            if (apoliceCobrindo.getNumero().equals(s.getNumeroApolice())) {
                sinistrosMesmoNumero.add(s);
            }
        }

        int novoSequencial = 1;
        if (!sinistrosMesmoNumero.isEmpty()) {
            Collections.sort(sinistrosMesmoNumero, new ComparadorSinistroSequencial());
            novoSequencial = sinistrosMesmoNumero.get(sinistrosMesmoNumero.size() - 1).getSequencial() + 1;
        }

        String numeroSinistro = "S" + apoliceCobrindo.getNumero() + String.format("%03d", novoSequencial);

        Sinistro sinistro = new Sinistro(
            numeroSinistro,
            daoVeiculo.buscar(dados.getPlaca()),
            dados.getDataHoraSinistro(),
            dataHoraAtual,
            dados.getUsuarioRegistro(),
            BigDecimal.valueOf(dados.getValorSinistro()),
            TipoSinistro.values()[dados.getCodigoTipoSinistro()]
        );

        sinistro.setNumeroApolice(apoliceCobrindo.getNumero());
        sinistro.setSequencial(novoSequencial);

        boolean sucesso = daoSinistro.incluir(sinistro);
        if (!sucesso) {
            excecao.getMensagens().add("Erro ao incluir o sinistro no DAO.");
            throw excecao;
        }

        return sinistro.getNumero();
    }
}