package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class ContaServiceBeanImplTest {

  private static ContaServiceBean SERVICE;
  private static CategoriaServiceBean SERVICE_CATEGORIA;

  private static final String NOME_CONTA_TESTES = "__IGNORE-ContaTestes";
  private static final String NOME_CATEGORIA_CONTA_TESTES = "__IGNORE-CategoriaContaTestes";
  private static final String DESCRICAO_CONTA_TESTES = "Descrição conta de testes"
          + " - não utilizar esta conta";
  private static final String DESCRICAO_CATEGORIA_CONTA_TESTES = "Descrição categoria conta "
          + "de testes - não utilizar esta conta";

  private static Categoria CATEGORIA_TESTES;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa serviços.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (ContaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaServiceBeanImpl");

    SERVICE_CATEGORIA = (CategoriaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaServiceBeanImpl");

    init();
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
   * Test of persist method, of class ContaServiceBean.
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    String sulfixo = Calendar.getInstance().getTime().toString();

    Conta novaConta = new Conta();

    novaConta.setNome(NOME_CONTA_TESTES);
    novaConta.setDescricao(DESCRICAO_CONTA_TESTES.concat(sulfixo));
    novaConta.setCategoria(CATEGORIA_TESTES);

    novaConta = SERVICE.persist(novaConta);

    assertTrue("Id da conta não é maior que zero", (novaConta.getId() > 0));
  }

  /**
   * Test of getTodas method, of class ContaServiceBean.
   */
  @Test
  public void testGetTodas() {
    Conta novaConta = new Conta();

    novaConta.setNome(NOME_CONTA_TESTES);
    novaConta.setDescricao(DESCRICAO_CONTA_TESTES);

    novaConta.setCategoria(CATEGORIA_TESTES);

    SERVICE.persist(novaConta);

    List<Conta> listaContas = SERVICE.getTodas();

    assertTrue("Quantidade de itens não é maior que zero", (listaContas.size() > 0));
  }

  /**
   * Test of remove method, of class ContaServiceBean.
   */
  @Test
  public void testRemovePorId() {
    List<Conta> listaContas = SERVICE.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    SERVICE.remove(listaContas.get(0).getId());
  }

  /**
   * Test of remove method, of class ContaServiceBean.
   */
  @Test
  public void testRemovePorCategoria() {
    List<Conta> listaContas = SERVICE.getByNome(NOME_CONTA_TESTES);

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    SERVICE.remove(listaContas.get(0));
  }

  /**
   * Test of getById method, of class ContaServiceBean.
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
   * Test of getByNome method, of class ContaServiceBean.
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
   * Test of getByCategoria method, of class ContaServiceBean.
   */
  @Test
  public void testGetByCategoria() {
    List<Conta> listaContas = SERVICE.getByCategoria(CATEGORIA_TESTES);

    assertTrue("Lista de contas não é maior que zero",
            (listaContas.size() > 0));

    assertEquals("Categorias comparadas não são iguais",
            CATEGORIA_TESTES, listaContas.get(0).getCategoria());
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Conta> listaContas = SERVICE.getByNome(NOME_CONTA_TESTES);

    listaContas.forEach(SERVICE::remove);

    SERVICE_CATEGORIA.remove(CATEGORIA_TESTES);
  }

  /**
   * Inicializa as entidades necessárias para os testes.
   */
  private static void init() {
    CATEGORIA_TESTES = new Categoria();

    CATEGORIA_TESTES.setNome(NOME_CATEGORIA_CONTA_TESTES);
    CATEGORIA_TESTES.setDescricao(DESCRICAO_CATEGORIA_CONTA_TESTES);
    CATEGORIA_TESTES.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    CATEGORIA_TESTES = SERVICE_CATEGORIA.persist(CATEGORIA_TESTES);
  }
}
