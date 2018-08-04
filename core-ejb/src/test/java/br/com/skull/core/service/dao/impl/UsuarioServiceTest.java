package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.service.dao.UsuarioServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class UsuarioServiceTest {

  private static EJBContainer CONTAINER;
  private static UsuarioServiceRemote SERVICE;

  private static final String NOME_USUARIO_TESTES = "__IGNORE-UsuarioTestes";
  private static final String EMAIL_NOME_USUARIO_TESTES = "testes";
  private static final String EMAIL_DOMINIO_USUARIO_TESTES = "@testes.com";
  private static final String PASSWORD_USUARIO_TESTES = "passwd";
  private static final long TIPO_USUARIO_TESTES = TipoUsuarioEnum.REGULAR.getCodigo();

  public UsuarioServiceTest() {
  }

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa container e serviços.
   *
   * @throws NamingException caso não ache o bean do serviço
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    CONTAINER = javax.ejb.embeddable.EJBContainer.createEJBContainer();

    SERVICE = (UsuarioServiceRemote) CONTAINER.getContext()
            .lookup("java:global/classes/UsuarioService");
  }

  /**
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDownClass() {
    cleanUp();

    CONTAINER.close();
  }

  /**
   * Test of persist method, of class UsuarioService.
   */
  @Test
  @Repeat(times = 3)
  public void testPersistEmailsDiferentes() {
    System.out.println("|--> [persist() - unicos]");

    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    novoUsuario = SERVICE.persist(novoUsuario);

    assertTrue("Id do usuário não é maior que zero", (novoUsuario.getId() > 0));
  }

  /**
   * Test of remove method, of class UsuarioService.
   */
  @Test
  public void testRemovePorUsuario() {
    System.out.println("|--> [remove(Usuaario)]");

    List<Usuario> listaUsuarios = SERVICE.getByNome(NOME_USUARIO_TESTES);

    if (listaUsuarios.size() > 0) {
      SERVICE.remove(listaUsuarios.get(0));
    } else {
      assertTrue("Não é possível realizar o teste porque não existem usuários testáveis", false);
    }
  }

  /**
   * Test of remove method, of class UsuarioService.
   */
  @Test
  public void testRemovePorId() {
    System.out.println("|--> [remove(id)]");

    List<Usuario> listaUsuarios = SERVICE.getByNome(NOME_USUARIO_TESTES);

    if (listaUsuarios.size() > 0) {
      SERVICE.remove(listaUsuarios.get(0).getId());
    } else {
      assertTrue("Não é possível realizar o teste porque não existem usuários testáveis", false);
    }
  }

  /**
   * Test of getTodos method, of class UsuarioService.
   */
  @Test
  public void testGetTodos() {
    System.out.println("|--> [getTodos()");

    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    SERVICE.persist(novoUsuario);

    List<Usuario> listaTodosUsuarios = SERVICE.getTodos();

    assertTrue("Lista de usuários menor que zero", listaTodosUsuarios.size() > 0);
  }

  /**
   * Test of getById method, of class UsuarioService.
   */
  @Test
  public void testGetById() {
    System.out.println("|--> [testGetById()");

    List<Usuario> listaUsuarios = SERVICE.getByNome(NOME_USUARIO_TESTES);

    if (listaUsuarios.size() > 0) {
      Usuario usuarioEsperado = listaUsuarios.get(0);
      Usuario usuarioTeste = SERVICE.getById(usuarioEsperado.getId());

      assertEquals("Usuários comparados não são iguais", usuarioEsperado, usuarioTeste);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem usuários testáveis", false);
    }
  }

  /**
   * Test of getByNome method, of class UsuarioService.
   */
  @Test
  public void testGetByNome() {
    System.out.println("|--> [testGetByNome()");

    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    SERVICE.persist(novoUsuario);

    List<Usuario> listaUsuarios = SERVICE.getByNome(NOME_USUARIO_TESTES);

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);
  }

  /**
   * Test of getByNomeAproximado method, of class UsuarioService.
   */
  @Test
  @Ignore
  public void testGetByNomeAproximado() {
  }

  /**
   * Test of getByEmail method, of class UsuarioService.
   */
  @Test
  @Ignore
  public void testGetByEmail() {
  }

  /**
   * Test of getByTipo method, of class UsuarioService.
   */
  @Test
  @Ignore
  public void testGetByTipo_TipoUsuarioEnum() {
  }

  /**
   * Test of getByTipo method, of class UsuarioService.
   */
  @Test
  @Ignore
  public void testGetByTipo_long() {
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {

  }

}
