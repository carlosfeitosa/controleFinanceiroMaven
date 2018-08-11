package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.ContaServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class ContaServiceTest {

  private static ContaServiceRemote SERVICE;
  private static CategoriaServiceRemote SERVICE_CATEGORIA;

  private static final String NOME_CONTA_TESTES = "__IGNORE-ContaTestes";
  private static final String NOME_CATEGORIA_CONTA_TESTES = "__IGNORE-CategoriaContaTestes";
  private static final String DESCRICAO_CONTA_TESTES = "Descrição conta de testes"
          + " - não utilizar esta conta";
  private static final String DESCRICAO_CATEGORIA_CONTA_TESTES = "Descrição categoria conta "
          + "de testes - não utilizar esta conta";

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa container e serviços.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (ContaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaService");

    SERVICE_CATEGORIA = (CategoriaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");
  }

  /**
   * Finaliza serviços pendentes.
   *
   * @throws javax.naming.NamingException caso não consiga recuperar os serviços para limpeza
   */
  @AfterClass
  public static void tearDownClass() throws NamingException {
    cleanUp();
  }

  /**
   * Test of persist method, of class ContaService.
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    String sulfixo = Calendar.getInstance().getTime().toString();

    Conta novaConta = new Conta();

    novaConta.setNome(NOME_CONTA_TESTES);
    novaConta.setDescricao(DESCRICAO_CONTA_TESTES.concat(sulfixo));

    Categoria categoriaConta = new Categoria();

    categoriaConta.setNome(NOME_CATEGORIA_CONTA_TESTES);
    categoriaConta.setDescricao(DESCRICAO_CATEGORIA_CONTA_TESTES);
    categoriaConta.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    categoriaConta = SERVICE_CATEGORIA.persist(categoriaConta);

    novaConta.setCategoria(categoriaConta);

    novaConta = SERVICE.persist(novaConta);

    assertTrue("Id da conta não é maior que zero", (novaConta.getId() > 0));
  }

  /**
   * Test of getTodas method, of class ContaService.
   */
  @Test
  public void testGetTodas() {
    Conta novaConta = new Conta();

    novaConta.setNome(NOME_CONTA_TESTES);
    novaConta.setDescricao(DESCRICAO_CONTA_TESTES);

    Categoria categoriaConta = new Categoria();

    categoriaConta.setNome(NOME_CATEGORIA_CONTA_TESTES);
    categoriaConta.setDescricao(DESCRICAO_CATEGORIA_CONTA_TESTES);
    categoriaConta.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    categoriaConta = SERVICE_CATEGORIA.persist(categoriaConta);

    novaConta.setCategoria(categoriaConta);

    SERVICE.persist(novaConta);

    List<Conta> listaContas = SERVICE.getTodas();

    assertTrue("Quantidade de itens não é maior que zero", (listaContas.size() > 0));
  }

  /**
   * Test of remove method, of class ContaService.
   */
  @Test
  public void testRemovePorId() {
    List<Conta> listaContas = SERVICE.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    SERVICE.remove(listaContas.get(0).getId());
  }

  /**
   * Test of remove method, of class ContaService.
   */
  @Test
  public void testRemovePorCategoria() {
    List<Conta> listaContas = SERVICE.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    SERVICE.remove(listaContas.get(0));
  }

  /**
   * Test of getById method, of class ContaService.
   */
  @Test
  public void testGetById() {
    List<Conta> listaContas = SERVICE.getTodas();

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    Conta contaEsperada = listaContas.get(0);
    Conta contaTeste = SERVICE.getById(contaEsperada.getId());

    assertEquals("Contas comparadas não são iguais", contaEsperada, contaTeste);
  }

  /**
   * Test of getByNome method, of class ContaService.
   */
  @Test
  public void testGetByName() {
    List<Conta> listaContas = SERVICE.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    Conta contaRecuperada = listaContas.get(0);

    assertTrue("Não foi possível recuperar categoria de testes",
            (contaRecuperada.getId() > 0));

    assertEquals("Nome da conta diferente da experada", NOME_CONTA_TESTES,
            contaRecuperada.getNome());
  }

  /**
   * Test of getByCategoria method, of class ContaService.
   */
  @Test
  public void testGetByCategoria() {
    Conta novaConta = new Conta();

    novaConta.setNome(NOME_CONTA_TESTES);
    novaConta.setDescricao(DESCRICAO_CONTA_TESTES);

    Categoria categoriaConta = new Categoria();

    categoriaConta.setNome(NOME_CATEGORIA_CONTA_TESTES);
    categoriaConta.setDescricao(DESCRICAO_CATEGORIA_CONTA_TESTES);
    categoriaConta.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    categoriaConta = SERVICE_CATEGORIA.persist(categoriaConta);

    novaConta.setCategoria(categoriaConta);

    novaConta = SERVICE.persist(novaConta);

    List<Conta> listaContas = SERVICE.getByCategoria(categoriaConta);

    assertTrue("Lista de contas não é maior que zero",
            (listaContas.size() > 0));

    assertEquals("Contas comparadas não são iguais",
            novaConta, listaContas.get(0));
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Conta> listaContas = new ArrayList<>();

    listaContas.addAll(SERVICE.getByNome(NOME_CONTA_TESTES));

    List<Categoria> listaCategorias = SERVICE_CATEGORIA.getByNome(NOME_CATEGORIA_CONTA_TESTES);

    listaContas.forEach((conta) -> {
      SERVICE.remove(conta);
    });

    listaCategorias.forEach((categoria) -> {
      SERVICE_CATEGORIA.remove(categoria);
    });
  }
}
