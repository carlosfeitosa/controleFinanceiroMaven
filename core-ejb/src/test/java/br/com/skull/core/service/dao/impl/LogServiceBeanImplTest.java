package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.LogServiceBean;
import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Log;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;
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
 * Classe de testes para o serviço DAO de log.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class LogServiceBeanImplTest {

  private static final String DESCRICAO_LOG_TESTES = "__IGNORE-LogTestes";
  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";
  private static final long CODIGO_TIPO_CATEGORIA_TESTES
          = TipoCategoriaEnum.CATEGORIA.getCodigo();
  private static final String NOME_USUARIO_TESTES = "__IGNORE-UsuarioTestes";
  private static final String EMAIL_NOME_USUARIO_TESTES = "testes";
  private static final String EMAIL_DOMINIO_USUARIO_TESTES = "@testes.com";
  private static final String PASSWORD_USUARIO_TESTES = "passwd";
  private static final long CODIGO_TIPO_USUARIO_TESTES = TipoUsuarioEnum.REGULAR.getCodigo();

  private static LogServiceBean SERVICE;
  private static CategoriaServiceBean SERVICE_CATEGORIA;
  private static UsuarioServiceBean SERVICE_USUARIO;

  private static Categoria CATEGORIA_TESTES;
  private static Usuario USUARIO_TESTES;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

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

    init();
  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class LogServiceBean.
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    Log logTeste = new Log();

    logTeste.setCategoria(CATEGORIA_TESTES);
    logTeste.setUsuario(USUARIO_TESTES);
    logTeste.setDescricao(DESCRICAO_LOG_TESTES);
    logTeste.setMomento(Calendar.getInstance().getTime());

    logTeste = SERVICE.persist(logTeste);

    assertTrue("Id do log não é maior que zero", (logTeste.getId() > 0));
  }

  /**
   * Test of remove method, of class LogServiceBean.
   */
  @Test
  public void testRemoveLog() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    SERVICE.remove(listaLogs.get(0));
  }

  /**
   * Test of remove method, of class LogServiceBean.
   */
  @Test
  public void testRemoveById() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    SERVICE.remove(listaLogs.get(0).getId());
  }

  /**
   * Test of getById method, of class LogServiceBean.
   */
  @Test
  public void testGetById() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Log logTeste = SERVICE.getById(listaLogs.get(0).getId());

    assertEquals("Log diferente do esperado", listaLogs.get(0), logTeste);
  }

  /**
   * Test of getByMomento method, of class LogServiceBean.
   */
  @Test
  public void testGetByMomento() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    List<Log> listaLogsTeste = SERVICE.getByMomento(listaLogs.get(0).getMomento(),
            listaLogs.get(0).getMomento());

    assertEquals("Log diferente do esperado", listaLogs.get(0), listaLogsTeste.get(0));
  }

  /**
   * Test of getByCategoria method, of class LogServiceBean.
   */
  @Test
  public void testGetByCategoria() {
    List<Log> listaLogs = SERVICE.getByCategoria(CATEGORIA_TESTES, null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    assertEquals("Categoria diferente da esperada", CATEGORIA_TESTES,
            listaLogs.get(0).getCategoria());
  }

  /**
   * Test of getByUsuario method, of class LogServiceBean.
   */
  @Test
  public void testGetByUsuario() {
    List<Log> listaLogs = SERVICE.getByUsuario(USUARIO_TESTES, null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    assertEquals("Usuário diferente do esperado", USUARIO_TESTES,
            listaLogs.get(0).getUsuario());
  }

  /**
   * Test of getByCategoriaUsuario method, of class LogServiceBean.
   */
  @Test
  public void testGetByCategoriaUsuario() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    List<Log> listaLogsTeste = SERVICE.getByCategoriaUsuario(listaLogs.get(0).getCategoria(),
            listaLogs.get(0).getUsuario(), null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogsTeste.size() > 0);

    assertEquals("Log diferente do esperado", listaLogs.get(0), listaLogsTeste.get(0));
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    listaLogs.forEach(SERVICE::remove);

    SERVICE_USUARIO.remove(USUARIO_TESTES);
    SERVICE_CATEGORIA.remove(CATEGORIA_TESTES);
  }

  /**
   * Inicializa as entidades do teste.
   */
  private static void init() {
    CATEGORIA_TESTES = new Categoria();

    CATEGORIA_TESTES.setNome(NOME_CATEGORIA_TESTES);
    CATEGORIA_TESTES.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    CATEGORIA_TESTES.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    CATEGORIA_TESTES = SERVICE_CATEGORIA.persist(CATEGORIA_TESTES);

    USUARIO_TESTES = new Usuario();

    USUARIO_TESTES.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    USUARIO_TESTES.setNome(NOME_USUARIO_TESTES);
    USUARIO_TESTES.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    USUARIO_TESTES.setPassword(PASSWORD_USUARIO_TESTES);

    USUARIO_TESTES = SERVICE_USUARIO.persist(USUARIO_TESTES);
  }

}
