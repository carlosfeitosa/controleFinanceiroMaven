package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;
import br.com.skull.core.service.dao.LancamentoServiceBean;
import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Lançamento.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class LancamentoServiceBeanImplTest {

  private static LancamentoServiceBean SERVICE;
  private static CategoriaServiceBean SERVICE_CATEGORIA;
  private static ContaServiceBean SERVICE_CONTA;
  private static UsuarioServiceBean SERVICE_USUARIO;

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";
  private static final long CODIGO_TIPO_CATEGORIA_TESTES
          = TipoCategoriaEnum.CATEGORIA.getCodigo();

  private static final String NOME_CONTA_TESTES = "__IGNORE-ContaLancamentoTestes";
  private static final String DESCRICAO_CONTA_TESTES = "Descrição conta de testes"
          + " - não utilizar esta conta";

  private static final String NOME_USUARIO_TESTES = "__IGNORE-UsuarioTestes";
  private static final String EMAIL_NOME_USUARIO_TESTES = "testes";
  private static final String EMAIL_DOMINIO_USUARIO_TESTES = "@testes.com";
  private static final String PASSWORD_USUARIO_TESTES = "passwd";
  private static final long CODIGO_TIPO_USUARIO_TESTES = TipoUsuarioEnum.REGULAR.getCodigo();

  private static final String DESCRICAO_LANCAMENTO_TESTES = "Descrição lançamento de testes"
          + " - não utilizar este lançamento";
  private static final TipoLancamentoEnum TIPO_LANCAMENTO_TESTES = TipoLancamentoEnum.CREDITO;
  private static final long CODIGO_TIPO_LANCAMENTO_TESTES = TipoLancamentoEnum.CREDITO.getCodigo();
  private static final double VALOR_LANCAMENTO_TESTES = 99321123;

  private static Categoria CATEGORIA_TESTES;
  private static Conta CONTA_TESTES;
  private static Usuario USUARIO_TESTES;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa serviços.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (LancamentoServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/LancamentoServiceBeanImpl");

    SERVICE_CATEGORIA = (CategoriaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaServiceBeanImpl");

    SERVICE_CONTA = (ContaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaServiceBeanImpl");

    SERVICE_USUARIO = (UsuarioServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioServiceBeanImpl");

    init();
  }

  /**
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class LancamentoServiceBean.
   *
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    Lancamento novoLancamento = new Lancamento();

    String sulfixo = Calendar.getInstance().getTime().toString();

    novoLancamento.setCategoria(CATEGORIA_TESTES);
    novoLancamento.setConta(CONTA_TESTES);
    novoLancamento.setUsuario(USUARIO_TESTES);
    novoLancamento.setDescricao(DESCRICAO_LANCAMENTO_TESTES.concat(sulfixo));
    novoLancamento.setTipo(CODIGO_TIPO_LANCAMENTO_TESTES);
    novoLancamento.setValor(VALOR_LANCAMENTO_TESTES);
    novoLancamento.setMomento(Calendar.getInstance().getTime());

    novoLancamento = SERVICE.persist(novoLancamento);

    assertTrue("Id da conta não é maior que zero", (novoLancamento.getId() > 0));
  }

  /**
   * Test of remove method, of class LancamentoServiceBean.
   */
  @Test
  public void testRemoveByLancamento() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(CONTA_TESTES, null, null);

    assertTrue("Lista de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    SERVICE.remove(listaLancamentos.get(0));
  }

  /**
   * Test of remove method, of class LancamentoServiceBean.
   */
  @Test
  public void testRemoveById() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(CONTA_TESTES, null, null);

    assertTrue("Lista de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    SERVICE.remove(listaLancamentos.get(0).getId());
  }

  /**
   * Test of getById method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetById() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(CONTA_TESTES, null, null);

    assertTrue("Lista de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    Lancamento lancamentoExp = listaLancamentos.get(0);
    Lancamento lancamentoResult = SERVICE.getById(lancamentoExp.getId());

    assertEquals("Lançamento diferente do esperado", lancamentoExp, lancamentoResult);
  }

  /**
   * Test of getByConta method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetByConta() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(CONTA_TESTES, null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    assertEquals("Conta diferente da esperada", CONTA_TESTES,
            listaLancamentos.get(0).getConta());
  }

  /**
   * Test of getByContaTipo method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetByContaTipoEnum() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaTipo(CONTA_TESTES,
            TIPO_LANCAMENTO_TESTES, null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    assertEquals("Conta diferente da esperada", TIPO_LANCAMENTO_TESTES.getCodigo(),
            listaLancamentos.get(0).getTipo());
  }

  /**
   * Test of getByContaTipo method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetByContaTipoCodigo() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaTipo(CONTA_TESTES,
            CODIGO_TIPO_LANCAMENTO_TESTES, null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    assertEquals("Conta diferente da esperada", TIPO_LANCAMENTO_TESTES.getCodigo(),
            listaLancamentos.get(0).getTipo());
  }

  /**
   * Test of getByContaCategoria method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetByContaCategoria() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaCategoria(CONTA_TESTES, CATEGORIA_TESTES,
            null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    assertEquals("Conta diferente da esperada", CONTA_TESTES,
            listaLancamentos.get(0).getConta());

    assertEquals("Categoria diferente da esperada", CATEGORIA_TESTES,
            listaLancamentos.get(0).getCategoria());
  }

  /**
   * Test of getByContaTipoCategoria method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetByContaTipoEnumCategoria() {
    Lancamento novoLancamento = new Lancamento();

    String sulfixo = Calendar.getInstance().getTime().toString();

    novoLancamento.setCategoria(CATEGORIA_TESTES);
    novoLancamento.setConta(CONTA_TESTES);
    novoLancamento.setUsuario(USUARIO_TESTES);
    novoLancamento.setDescricao(DESCRICAO_LANCAMENTO_TESTES.concat(sulfixo));
    novoLancamento.setTipo(CODIGO_TIPO_LANCAMENTO_TESTES);
    novoLancamento.setValor(VALOR_LANCAMENTO_TESTES);
    novoLancamento.setMomento(Calendar.getInstance().getTime());

    SERVICE.persist(novoLancamento);

    List<Lancamento> listaLancamentos = SERVICE.getByContaTipoCategoria(CONTA_TESTES,
            TIPO_LANCAMENTO_TESTES, CATEGORIA_TESTES, null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    assertEquals("Conta diferente da esperada", CONTA_TESTES,
            listaLancamentos.get(0).getConta());

    assertEquals("Tipo diferente do esperado", TIPO_LANCAMENTO_TESTES.getCodigo(),
            listaLancamentos.get(0).getTipo());

    assertEquals("Categoria diferente da esperada", CATEGORIA_TESTES,
            listaLancamentos.get(0).getCategoria());
  }

  /**
   * Test of getByContaTipoCategoria method, of class LancamentoServiceBean.
   */
  @Test
  public void testGetByContaTipoCodigoCategoria() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaTipoCategoria(CONTA_TESTES,
            CODIGO_TIPO_LANCAMENTO_TESTES, CATEGORIA_TESTES, null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    assertEquals("Conta diferente da esperada", CONTA_TESTES,
            listaLancamentos.get(0).getConta());

    assertEquals("Tipo diferente do esperado", CODIGO_TIPO_LANCAMENTO_TESTES,
            listaLancamentos.get(0).getTipo());

    assertEquals("Categoria diferente da esperada", CATEGORIA_TESTES,
            listaLancamentos.get(0).getCategoria());
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(CONTA_TESTES, null, null);

    listaLancamentos.forEach(SERVICE::remove);

    SERVICE_USUARIO.remove(USUARIO_TESTES);
    SERVICE_CONTA.remove(CONTA_TESTES);
    SERVICE_CATEGORIA.remove(CATEGORIA_TESTES);
  }

  /**
   * Inicializa as entidades necessárias para os testes.
   */
  private static void init() {
    CATEGORIA_TESTES = new Categoria();

    CATEGORIA_TESTES.setNome(NOME_CATEGORIA_TESTES);
    CATEGORIA_TESTES.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    CATEGORIA_TESTES.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    CATEGORIA_TESTES = SERVICE_CATEGORIA.persist(CATEGORIA_TESTES);

    CONTA_TESTES = new Conta();

    CONTA_TESTES.setNome(NOME_CONTA_TESTES);
    CONTA_TESTES.setDescricao(DESCRICAO_CONTA_TESTES);
    CONTA_TESTES.setCategoria(CATEGORIA_TESTES);

    CONTA_TESTES = SERVICE_CONTA.persist(CONTA_TESTES);

    USUARIO_TESTES = new Usuario();

    USUARIO_TESTES.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    USUARIO_TESTES.setNome(NOME_USUARIO_TESTES);
    USUARIO_TESTES.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    USUARIO_TESTES.setPassword(PASSWORD_USUARIO_TESTES);

    USUARIO_TESTES = SERVICE_USUARIO.persist(USUARIO_TESTES);
  }

}
