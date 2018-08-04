package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.ContaServiceRemote;
import br.com.skull.core.service.dao.CoreServiceTestSuite;
import br.com.skull.core.service.dao.LancamentoServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Lancamento.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class LancamentoServiceTest {

  private static LancamentoServiceRemote SERVICE;
  private static CategoriaServiceRemote SERVICE_CATEGORIA;
  private static ContaServiceRemote SERVICE_CONTA;

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";
  private static final long CODIGO_TIPO_CATEGORIA_TESTES
          = TipoCategoriaEnum.CATEGORIA.getCodigo();

  private static final String NOME_CONTA_TESTES = "__IGNORE-ContaTestes";
  private static final String DESCRICAO_CONTA_TESTES = "Descrição conta de testes"
          + " - não utilizar esta conta";

  private static final String DESCRICAO_LANCAMENTO_TESTES = "Descrição lançamento de testes"
          + " - não utilizar este lançamento";
  private static final long CODIGO_TIPO_LANCAMENTO_TESTES
          = TipoLancamentoEnum.CREDITO.getCodigo();
  private static final double VALOR_LANCAMENTO_TESTES = 99321123;

  public LancamentoServiceTest() {
  }

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa container e serviços.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (LancamentoServiceRemote) CoreServiceTestSuite.getContainer().getContext()
            .lookup("java:global/classes/LancamentoService");

    SERVICE_CATEGORIA = (CategoriaServiceRemote) CoreServiceTestSuite.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");

    SERVICE_CONTA = (ContaServiceRemote) CoreServiceTestSuite.getContainer().getContext()
            .lookup("java:global/classes/ContaService");
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
   * Test of persist method, of class LancamentoService.
   *
   */
  @Test
  @Repeat(times = 3)
  @Ignore
  public void testPersist() {
    System.out.println("|--> [persist()]");

    Categoria novaCategoriaLancamento = new Categoria();

    novaCategoriaLancamento.setNome(NOME_CATEGORIA_TESTES);
    novaCategoriaLancamento.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    novaCategoriaLancamento.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    novaCategoriaLancamento = SERVICE_CATEGORIA.persist(novaCategoriaLancamento);

    Conta novaContaLancamento = new Conta();

    novaContaLancamento.setNome(NOME_CONTA_TESTES);
    novaContaLancamento.setDescricao(DESCRICAO_CONTA_TESTES);
    novaContaLancamento.setCategoria(novaCategoriaLancamento);

    novaContaLancamento = SERVICE_CONTA.persist(novaContaLancamento);

    Lancamento novoLancamento = new Lancamento();

    String sulfixo = Calendar.getInstance().getTime().toString();

    novoLancamento.setCategoria(novaCategoriaLancamento);
    novoLancamento.setConta(novaContaLancamento);
    novoLancamento.setDescricao(DESCRICAO_LANCAMENTO_TESTES.concat(sulfixo));
    novoLancamento.setTipo(CODIGO_TIPO_LANCAMENTO_TESTES);
    novoLancamento.setValor(VALOR_LANCAMENTO_TESTES);
    novoLancamento.setMomento(Calendar.getInstance().getTime());

    novoLancamento = SERVICE.persist(novoLancamento);

    assertTrue("Id da conta não é maior que zero", (novoLancamento.getId() > 0));
  }

  /**
   * Test of remove method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testRemove_GenericType() {

  }

  /**
   * Test of remove method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testRemove_long_Class() {

  }

  /**
   * Test of getById method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetById() {

  }

  /**
   * Test of getByConta method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetByConta() {

  }

  /**
   * Test of getByContaTipo method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetByContaTipo_4args_1() {

  }

  /**
   * Test of getByContaTipo method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetByContaTipo_4args_2() {

  }

  /**
   * Test of getByContaCategoria method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetByContaCategoria() {

  }

  /**
   * Test of getByContaTipoCategoriaMomento method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetByContaTipoCategoriaMomento_5args_1() {

  }

  /**
   * Test of getByContaTipoCategoriaMomento method, of class LancamentoService.
   */
  @Test
  @Ignore
  public void testGetByContaTipoCategoriaMomento_5args_2() {

  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {

  }

}
