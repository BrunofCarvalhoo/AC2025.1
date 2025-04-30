package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteApoliceDAO extends TesteDAO {
    private ApoliceDAO dao = new ApoliceDAO();

    @Override
    protected Class<?> getClasse() {
        return Apolice.class;
    }

    private Apolice criarApoliceComNumero(String numero) {
        Veiculo veiculo = new Veiculo("PLACA" + numero, 2020, null, null, CategoriaVeiculo.BASICO);
        Apolice apolice = new Apolice(veiculo, BigDecimal.valueOf(500), BigDecimal.valueOf(1000), BigDecimal.valueOf(50000));
        apolice.setNumero(numero);
        return apolice;
    }

    @Test
    public void teste01() {
        String numero = "00000001";
        Apolice ap = criarApoliceComNumero(numero);
        dao.incluir(ap);
        Apolice buscada = dao.buscar(numero);
        Assertions.assertNotNull(buscada);
    }

    @Test
    public void teste02() {
        String numero = "00000002";
        dao.incluir(criarApoliceComNumero(numero));
        Apolice ap = dao.buscar("99999999");
        Assertions.assertNull(ap);
    }

    @Test
    public void teste03() {
        String numero = "00000003";
        dao.incluir(criarApoliceComNumero(numero));
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        String numero = "00000004";
        dao.incluir(criarApoliceComNumero(numero));
        boolean ret = dao.excluir("00009999");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        String numero = "00000005";
        Apolice ap = criarApoliceComNumero(numero);
        boolean ret = dao.incluir(ap);
        Assertions.assertTrue(ret);
        Apolice buscada = dao.buscar(numero);
        Assertions.assertNotNull(buscada);
    }

    @Test
    public void teste06() {
        String numero = "00000006";
        Apolice ap = criarApoliceComNumero(numero);
        dao.incluir(ap);
        boolean ret = dao.incluir(ap);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        String numero = "00000007";
        Apolice ap = criarApoliceComNumero(numero);
        boolean ret = dao.alterar(ap);
        Assertions.assertFalse(ret);
        Apolice buscada = dao.buscar(numero);
        Assertions.assertNull(buscada);
    }

    @Test
    public void teste08() {
        String numero = "00000008";
        Apolice ap = criarApoliceComNumero(numero);
        dao.incluir(ap);
        Apolice alterada = criarApoliceComNumero(numero); 
        alterada.setValorPremio(BigDecimal.valueOf(2000));
        boolean ret = dao.alterar(alterada);
        Assertions.assertTrue(ret);
    }
}
