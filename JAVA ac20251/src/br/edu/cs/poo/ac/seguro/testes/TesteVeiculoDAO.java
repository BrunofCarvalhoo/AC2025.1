package br.edu.cs.poo.ac.seguro.testes;

import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TesteVeiculoDAO extends TesteDAO {

    private VeiculoDAO dao = new VeiculoDAO();

    @Override
    protected Class getClasse() {
        return Veiculo.class;
    }

    static {
        String sep = File.separator;
        File dir = new File("." + sep + Veiculo.class.getSimpleName());
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Test
    public void teste01() {
        String placa = "00000000";
        Veiculo veiculo = new Veiculo(placa, 2000, null, null, CategoriaVeiculo.BASICO);
        cadastro.incluir(veiculo, placa);
        Veiculo ve = dao.buscar(placa);
        Assertions.assertNotNull(ve);
    }

    @Test
    public void teste02() {
        String placa = "10000000";
        Veiculo veiculo = new Veiculo(placa, 2001, null, null, CategoriaVeiculo.BASICO);
        cadastro.incluir(veiculo, placa);
        Veiculo ve = dao.buscar("11000000");
        Assertions.assertNull(ve);
    }

    @Test
    public void teste03() {
        String placa = "20000000";
        Veiculo veiculo = new Veiculo(placa, 2002, null, null, CategoriaVeiculo.BASICO);
        cadastro.incluir(veiculo, placa);
        boolean ret = dao.excluir(placa);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        String placa = "30000000";
        Veiculo veiculo = new Veiculo(placa, 2003, null, null, CategoriaVeiculo.BASICO);
        cadastro.incluir(veiculo, placa);
        boolean ret = dao.excluir("31000000");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        String placa = "40000000";
        Veiculo veiculo = new Veiculo(placa, 2004, null, null, CategoriaVeiculo.BASICO);
        boolean ret = dao.incluir(veiculo);
        Assertions.assertTrue(ret);
        Veiculo ve = dao.buscar(placa);
        Assertions.assertNotNull(ve);
    }

    @Test
    public void teste06() {
        String placa = "50000000";
        Veiculo veiculo = new Veiculo(placa, 2005, null, null, CategoriaVeiculo.BASICO);
        cadastro.incluir(veiculo, placa);
        boolean ret = dao.incluir(veiculo);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        String placa = "60000000";
        Veiculo veiculo = new Veiculo(placa, 2006, null, null, CategoriaVeiculo.BASICO);
        boolean ret = dao.alterar(veiculo);
        Assertions.assertFalse(ret);
        Veiculo ve = dao.buscar(placa);
        Assertions.assertNull(ve);
    }

    @Test
    public void teste08() {
        String placa = "70000000";
        Veiculo veiculo = new Veiculo(placa, 2007, null, null, CategoriaVeiculo.BASICO);
        cadastro.incluir(veiculo, placa);
        veiculo = new Veiculo(placa, 2008, null, null, CategoriaVeiculo.ESPORTIVO);
        boolean ret = dao.alterar(veiculo);
        Assertions.assertTrue(ret);
    }
}
