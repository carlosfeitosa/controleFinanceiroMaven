package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;
import br.com.skull.core.service.dao.LancamentoServiceBean;
import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;
import br.com.skull.core.service.dao.fixture.template.CategoriaTemplate;
import br.com.skull.core.service.dao.fixture.template.ContaTemplate;
import br.com.skull.core.service.dao.fixture.template.LancamentoTemplate;
import br.com.skull.core.service.dao.fixture.template.UsuarioTemplate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import java.util.List;
import javax.ejb.EJBException;
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

  private static final List<Lancamento> LISTA_ENTIDADE = new ArrayList<Lancamento>();

  private Lancamento lancamentoTestes;

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

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.service.dao.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    lancamentoTestes = new Lancamento();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != lancamentoTestes) && (null != lancamentoTestes.getId())) {
      LISTA_ENTIDADE.add(lancamentoTestes);
    }
  }

  /**
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Testa persistir um lançamento.
   *
   */
  @Test
  public void testPersist() {
    lancamentoTestes = Fixture.from(Lancamento.class).gimme(LancamentoTemplate.VALIDO);

    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);
    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    lancamentoTestes.setCategoria(novaCategoria);
    lancamentoTestes.setConta(novaConta);
    lancamentoTestes.setUsuario(novoUsuario);

    lancamentoTestes = SERVICE.persist(lancamentoTestes);

    assertTrue("Id da conta não é maior que zero", (lancamentoTestes.getId() > 0));
  }

  /**
   * Testa se uma exceção será lançada na tentativa de persistir nulo.
   */
  @Test(expected = EJBException.class)
  public void testPersistFailNull() {
    SERVICE.persist(null);
  }

  /**
   * Testa se uma exceção será lançada a tentativa de persistir um modelo vazio.
   */
  @Test(expected = EJBException.class)
  public void testPersistFailUsuarioContaInvalida() {
    SERVICE.persist(lancamentoTestes);
  }

  /**
   * Testa se consegue remover um lançamento (por classe Lancamento).
   */
  @Test
  public void testRemoveByLancamento() {
    lancamentoTestes = Fixture.from(Lancamento.class).gimme(LancamentoTemplate.VALIDO);

    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);
    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    lancamentoTestes.setCategoria(novaCategoria);
    lancamentoTestes.setConta(novaConta);
    lancamentoTestes.setUsuario(novoUsuario);

    lancamentoTestes = SERVICE.persist(lancamentoTestes);

    SERVICE.remove(lancamentoTestes);
  }

  /**
   * Testa remover um elemento através de seu id.
   */
  @Test
  public void testRemoveById() {
    lancamentoTestes = Fixture.from(Lancamento.class).gimme(LancamentoTemplate.VALIDO);

    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);
    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    lancamentoTestes.setCategoria(novaCategoria);
    lancamentoTestes.setConta(novaConta);
    lancamentoTestes.setUsuario(novoUsuario);

    lancamentoTestes = SERVICE.persist(lancamentoTestes);

    SERVICE.remove(lancamentoTestes.getId());
  }

  /**
   * Testa recuperar um elemento por id.
   */
  @Test
  public void testGetById() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(LISTA_ENTIDADE.get(0).getConta(),
            null, null);

    assertTrue("Lista de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    Lancamento lancamentoExp = listaLancamentos.get(0);
    Lancamento lancamentoResult = SERVICE.getById(lancamentoExp.getId());

    assertEquals("Lançamento diferente do esperado", lancamentoExp, lancamentoResult);
  }

  /**
   * Testa recuperar elementos por conta.
   */
  @Test
  public void testGetByConta() {
    List<Lancamento> listaLancamentos = SERVICE.getByConta(LISTA_ENTIDADE.get(0).getConta(),
            null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    for (Lancamento lancamento : listaLancamentos) {
      assertEquals("Conta diferente da esperada", LISTA_ENTIDADE.get(0).getConta(),
              lancamento.getConta());
    }
  }

  /**
   * Testa recuperar elementos por conta e tipo (enum).
   */
  @Test
  public void testGetByContaTipoEnum() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaTipo(LISTA_ENTIDADE.get(0).getConta(),
            TipoLancamentoEnum.valueOf(LISTA_ENTIDADE.get(0).getTipo()), null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    for (Lancamento lancamento : listaLancamentos) {
      assertEquals("Conta diferente da esperada", LISTA_ENTIDADE.get(0).getConta(),
              lancamento.getConta());

      assertEquals("Tipo diferente do esperado", LISTA_ENTIDADE.get(0).getTipo(),
              lancamento.getTipo());
    }
  }

  /**
   * Testa recuperar elementos por conta e tipo (código).
   */
  @Test
  public void testGetByContaTipoCodigo() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaTipo(LISTA_ENTIDADE.get(0).getConta(),
            LISTA_ENTIDADE.get(0).getTipo(), null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    for (Lancamento lancamento : listaLancamentos) {
      assertEquals("Conta diferente da esperada", LISTA_ENTIDADE.get(0).getConta(),
              lancamento.getConta());

      assertEquals("Tipo diferente do esperado", LISTA_ENTIDADE.get(0).getTipo(),
              lancamento.getTipo());
    }
  }

  /**
   * Testa recuperar elementos por categoria.
   */
  @Test
  public void testGetByContaCategoria() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaCategoria(
            LISTA_ENTIDADE.get(0).getConta(), LISTA_ENTIDADE.get(0).getCategoria(), null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    for (Lancamento lancamento : listaLancamentos) {
      assertEquals("Conta diferente da esperada", lancamento.getConta(),
              listaLancamentos.get(0).getConta());

      assertEquals("Categoria diferente da esperada", lancamento.getCategoria(),
              listaLancamentos.get(0).getCategoria());
    }
  }

  /**
   * Testa recuperar elementos por conta, tipo (enum) e categoria.
   */
  @Test
  public void testGetByContaTipoEnumCategoria() {
    lancamentoTestes = Fixture.from(Lancamento.class).gimme(LancamentoTemplate.VALIDO);

    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);
    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    lancamentoTestes.setCategoria(novaCategoria);
    lancamentoTestes.setConta(novaConta);
    lancamentoTestes.setUsuario(novoUsuario);

    lancamentoTestes = SERVICE.persist(lancamentoTestes);

    LISTA_ENTIDADE.add(lancamentoTestes);

    List<Lancamento> listaLancamentos = SERVICE.getByContaTipoCategoria(
            LISTA_ENTIDADE.get(0).getConta(),
            TipoLancamentoEnum.valueOf(LISTA_ENTIDADE.get(0).getTipo()),
            LISTA_ENTIDADE.get(0).getCategoria(), null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    for (Lancamento lancamento : listaLancamentos) {
      assertEquals("Conta diferente da esperada", lancamento.getConta(),
              listaLancamentos.get(0).getConta());

      assertEquals("Tipo diferente do esperado", LISTA_ENTIDADE.get(0).getTipo(),
              lancamento.getTipo());

      assertEquals("Categoria diferente da esperada", lancamento.getCategoria(),
              listaLancamentos.get(0).getCategoria());

    }
  }

  /**
   * Testa recuperar elementos por conta, tipo (código) e categoria.
   */
  @Test
  public void testGetByContaTipoCodigoCategoria() {
    List<Lancamento> listaLancamentos = SERVICE.getByContaTipoCategoria(
            LISTA_ENTIDADE.get(0).getConta(), LISTA_ENTIDADE.get(0).getTipo(),
            LISTA_ENTIDADE.get(0).getCategoria(), null, null);

    assertTrue("Quantidade de lançamentos não é maior que zero", listaLancamentos.size() > 0);

    for (Lancamento lancamento : listaLancamentos) {
      assertEquals("Conta diferente da esperada", lancamento.getConta(),
              listaLancamentos.get(0).getConta());

      assertEquals("Tipo diferente do esperado", LISTA_ENTIDADE.get(0).getTipo(),
              lancamento.getTipo());

      assertEquals("Categoria diferente da esperada", lancamento.getCategoria(),
              listaLancamentos.get(0).getCategoria());

    }
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (Lancamento lancamento : LISTA_ENTIDADE) {
      SERVICE.remove(lancamento);

      SERVICE_CONTA.remove(lancamento.getConta());
      SERVICE_USUARIO.remove(lancamento.getUsuario());
      SERVICE_CATEGORIA.remove(lancamento.getCategoria());
    }
  }

}
