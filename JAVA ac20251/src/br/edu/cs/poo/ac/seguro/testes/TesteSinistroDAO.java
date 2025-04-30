package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteSinistroDAO extends TesteDAO {
    private SinistroDAO dao = new SinistroDAO();

    @Override
    protected Class<?> getClasse() {
        return Sinistro.class;
    }

    private Sinistro criarSinistroComNumero(String numero) {
        Veiculo veiculo = new Veiculo("PLACA" + numero, 2021, null, null, CategoriaVeiculo.INTERMEDIARIO);
        Sinistro sinistro = new Sinistro(
            veiculo,
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now(),
            "usuario" + numero,
            BigDecimal.valueOf(3000),
            TipoSinistro.COLISAO
        );
        sinistro.setNumero(numero);
        return sinistro;
    }

    @Test
    public void teste01() {
        String numero = "S0001";
        Sinistro sin = criarSinistroComNumero(numero);
        dao.incluir(sin);
        Sinistro buscado = dao.buscar(numero);
        Assertions.assertNotNull(buscado);
    }

    @Test
    public void teste02() {
        String numero = "S0002";
        dao.incluir(criarSinistroComNumero(numero));
        Sinistro sin = dao.buscar("S9999");
        Assertions.assertNull(sin);
    }

    @Test
    public void teste03() {
        String numero = "S0003";
        dao.incluir(criarSinistroComNumero(numero));
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        String numero = "S0004";
        dao.incluir(criarSinistroComNumero(numero));
        boolean ret = dao.excluir("S0005");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        String numero = "S0005";
        Sinistro sin = criarSinistroComNumero(numero);
        boolean ret = dao.incluir(sin);
        Assertions.assertTrue(ret);
        Sinistro buscado = dao.buscar(numero);
        Assertions.assertNotNull(buscado);
    }

    @Test
    public void teste06() {
        String numero = "S0006";
        Sinistro sin = criarSinistroComNumero(numero);
        dao.incluir(sin);
        boolean ret = dao.incluir(sin);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        String numero = "S0007";
        Sinistro sin = criarSinistroComNumero(numero);
        boolean ret = dao.alterar(sin);
        Assertions.assertFalse(ret);
        Sinistro buscado = dao.buscar(numero);
        Assertions.assertNull(buscado);
    }

    @Test
    public void teste08() {
        String numero = "S0008";
        Sinistro sin = criarSinistroComNumero(numero);
        dao.incluir(sin);
        Sinistro alterado = criarSinistroComNumero(numero);
        alterado.setValorSinistro(BigDecimal.valueOf(9000));
        boolean ret = dao.alterar(alterado);
        Assertions.assertTrue(ret);
    }
}
