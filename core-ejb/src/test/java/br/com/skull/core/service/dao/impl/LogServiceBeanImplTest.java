package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.LogServiceBean;
import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Log;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.fixture.template.CategoriaTemplate;
import br.com.skull.core.service.dao.fixture.template.LogTemplate;
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
 * Classe de testes para o serviço DAO de log.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class LogServiceBeanImplTest {

  private static LogServiceBean SERVICE;
  private static CategoriaServiceBean SERVICE_CATEGORIA;
  private static UsuarioServiceBean SERVICE_USUARIO;

  private static final List<Log> LISTA_ENTIDADE = new ArrayList<>();

  private Log logTestes;

  /**
   * Instancia os serviços para o testes.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (LogServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/LogServiceBeanImpl");

    SERVICE_CATEGORIA = (CategoriaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaServiceBeanImpl");

    SERVICE_USUARIO = (UsuarioServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioServiceBeanImpl");

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.service.dao.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    logTestes = new Log();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != logTestes) && (null != logTestes.getId())) {
      LISTA_ENTIDADE.add(logTestes);
    }
  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (Log log : LISTA_ENTIDADE) {
      SERVICE.remove(log);

      SERVICE_CATEGORIA.remove(log.getCategoria());

      SERVICE_USUARIO.remove(log.getUsuario());
    }
  }

  /**
   * Testa persistir um log.
   */
  @Test
  public void testPersist() {
    logTestes = Fixture.from(Log.class).gimme(LogTemplate.VALIDO);

    Categoria categoriaTeste = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTeste = SERVICE_CATEGORIA.persist(categoriaTeste);

    Usuario usuarioTeste = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTeste = SERVICE_USUARIO.persist(usuarioTeste);

    logTestes.setCategoria(categoriaTeste);
    logTestes.setUsuario(usuarioTeste);

    logTestes = SERVICE.persist(logTestes);

    assertTrue("Id do log não é maior que zero", (logTestes.getId() > 0));
  }

  /**
   * Testa se uma exceção será lançada na tentativa de persistir null.
   */
  @Test(expected = EJBException.class)
  public void testPersistNull() {
    SERVICE.persist(null);
  }

  /**
   * Testa se uma exceção será lançada na tentativa de persistir um elemento inválido.
   */
  @Test(expected = EJBException.class)
  public void testPersistInvalido() {
    logTestes = Fixture.from(Log.class).gimme(LogTemplate.INVALIDO);

    SERVICE.persist(logTestes);
  }

  /**
   * Testa a possibilidade de excluir um log através do objeto Log (entidade).
   */
  @Test
  public void testRemoveLog() {
    logTestes = Fixture.from(Log.class).gimme(LogTemplate.VALIDO);

    Categoria categoriaTeste = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTeste = SERVICE_CATEGORIA.persist(categoriaTeste);

    Usuario usuarioTeste = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTeste = SERVICE_USUARIO.persist(usuarioTeste);

    logTestes.setCategoria(categoriaTeste);
    logTestes.setUsuario(usuarioTeste);

    logTestes = SERVICE.persist(logTestes);

    SERVICE.remove(logTestes);
  }

  /**
   * Testa a possibilidade de excluir um log através de seu id.
   */
  @Test
  public void testRemoveById() {
    logTestes = Fixture.from(Log.class).gimme(LogTemplate.VALIDO);

    Categoria categoriaTeste = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTeste = SERVICE_CATEGORIA.persist(categoriaTeste);

    Usuario usuarioTeste = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTeste = SERVICE_USUARIO.persist(usuarioTeste);

    logTestes.setCategoria(categoriaTeste);
    logTestes.setUsuario(usuarioTeste);

    logTestes = SERVICE.persist(logTestes);

    SERVICE.remove(logTestes.getId());
  }

  /**
   * Testa recuperar um log através de seu id.
   */
  @Test
  public void testGetById() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Log logTeste = SERVICE.getById(listaLogs.get(0).getId());

    assertEquals("Log diferente do esperado", listaLogs.get(0), logTeste);
  }

  /**
   * Testa recuperar um log pelo momento em que ele foi inserido.
   */
  @Test
  public void testGetByMomento() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Log logTeste = listaLogs.get(0);

    List<Log> listaLogsTeste = SERVICE.getByMomento(logTeste.getMomento(),
            logTeste.getMomento());

    for (Log log : listaLogsTeste) {
      assertEquals("Log diferente do esperado", logTeste, log);
    }
  }

  /**
   * Testa recuperar logs pela categoria.
   */
  @Test
  public void testGetByCategoria() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Categoria categoriaTeste = listaLogs.get(0).getCategoria();

    listaLogs = SERVICE.getByCategoria(categoriaTeste, null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    for (Log log : listaLogs) {
      assertEquals("Categoria diferente da esperada", categoriaTeste, log.getCategoria());
    }
  }

  /**
   * Testa recuperar logs pelo usuário.
   */
  @Test
  public void testGetByUsuario() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Usuario usuarioTeste = listaLogs.get(0).getUsuario();

    listaLogs = SERVICE.getByUsuario(usuarioTeste, null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    for (Log log : listaLogs) {
      assertEquals("Usuário diferente do esperado", usuarioTeste, log.getUsuario());
    }
  }

  /**
   * Test of getByCategoriaUsuario method, of class LogServiceBean.
   */
  @Test
  public void testGetByCategoriaUsuario() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Categoria categoriaTeste = listaLogs.get(0).getCategoria();
    Usuario usuarioTeste = listaLogs.get(0).getUsuario();

    List<Log> listaLogsTeste = SERVICE.getByCategoriaUsuario(categoriaTeste, usuarioTeste,
            null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogsTeste.size() > 0);

    for (Log log : listaLogsTeste) {
      assertEquals("Categoria diferente da esperada", categoriaTeste, log.getCategoria());
      assertEquals("Usuario diferente do esperado", usuarioTeste, log.getUsuario());
    }

  }
}
