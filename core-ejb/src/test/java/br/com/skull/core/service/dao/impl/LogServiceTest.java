package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.LogServiceRemote;
import br.com.skull.core.service.dao.UsuarioServiceRemote;
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
public class LogServiceTest {

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

  private static LogServiceRemote SERVICE;
  private static CategoriaServiceRemote SERVICE_CATEGORIA;
  private static UsuarioServiceRemote SERVICE_USUARIO;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Instancia os serviços para o testes.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (LogServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/LogService");

    SERVICE_CATEGORIA = (CategoriaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");

    SERVICE_USUARIO = (UsuarioServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioService");

  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class LogService.
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    Categoria novaCategoria = new Categoria();

    novaCategoria.setNome(NOME_CATEGORIA_TESTES);
    novaCategoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    novaCategoria.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Usuario novoUsuario = new Usuario();

    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    Log logTeste = new Log();

    logTeste.setCategoria(novaCategoria);
    logTeste.setUsuario(novoUsuario);
    logTeste.setDescricao(DESCRICAO_LOG_TESTES);
    logTeste.setMomento(Calendar.getInstance().getTime());

    logTeste = SERVICE.persist(logTeste);

    assertTrue("Id do log não é maior que zero", (logTeste.getId() > 0));
  }

  /**
   * Test of remove method, of class LogService.
   */
  @Test
  public void testRemoveLog() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    SERVICE.remove(listaLogs.get(0));
    SERVICE_CATEGORIA.remove(listaLogs.get(0).getCategoria());
    SERVICE_USUARIO.remove(listaLogs.get(0).getUsuario());
  }

  /**
   * Test of remove method, of class LogService.
   */
  @Test
  public void testRemoveById() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    SERVICE.remove(listaLogs.get(0).getId());
    SERVICE_CATEGORIA.remove(listaLogs.get(0).getCategoria());
    SERVICE_USUARIO.remove(listaLogs.get(0).getUsuario());
  }

  /**
   * Test of getById method, of class LogService.
   */
  @Test
  public void testGetById() {
    List<Log> listaLogs = SERVICE.getByMomento(null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    Log logTeste = SERVICE.getById(listaLogs.get(0).getId());

    assertEquals("Log diferente do esperado", listaLogs.get(0), logTeste);
  }

  /**
   * Test of getByMomento method, of class LogService.
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
   * Test of getByCategoria method, of class LogService.
   */
  @Test
  public void testGetByCategoria() {
    List<Categoria> listaCategorias = SERVICE_CATEGORIA.getByNome(NOME_CATEGORIA_TESTES);

    assertTrue("Lista de categorias não é maior que zero", listaCategorias.size() > 0);

    List<Log> listaLogs = SERVICE.getByCategoria(listaCategorias.get(0), null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    assertEquals("Categoria diferente da esperada", listaCategorias.get(0),
            listaLogs.get(0).getCategoria());
  }

  /**
   * Test of getByUsuario method, of class LogService.
   */
  @Test
  public void testGetByUsuario() {
    List<Usuario> listaUsuarios = SERVICE_USUARIO.getByNome(NOME_USUARIO_TESTES);

    assertTrue("Lista de usuários não é maior que zero", listaUsuarios.size() > 0);

    List<Log> listaLogs = SERVICE.getByUsuario(listaUsuarios.get(0), null, null);

    assertTrue("Lista de logs não é maior que zero", listaLogs.size() > 0);

    assertEquals("Usuário diferente do esperado", listaUsuarios.get(0),
            listaLogs.get(0).getUsuario());
  }

  /**
   * Test of getByCategoriaUsuario method, of class LogService.
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

    listaLogs.forEach((log) -> {
      SERVICE.remove(log);
      SERVICE_CATEGORIA.remove(log.getCategoria());
      SERVICE_USUARIO.remove(log.getUsuario());
    });
  }

}
