package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.UsuarioServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;
import javax.ejb.EJBException;
import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de Usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class UsuarioServiceTest {

  private static UsuarioServiceRemote SERVICE;

  private static final String NOME_USUARIO_TESTES = "__IGNORE-UsuarioTestes";
  private static final String NOME_APROXIMADO_USUARIO_TESTES = "__IGNORE-Usuario";
  private static final String EMAIL_NOME_USUARIO_TESTES = "testes";
  private static final String EMAIL_DOMINIO_USUARIO_TESTES = "@testes.com";
  private static final String PASSWORD_USUARIO_TESTES = "passwd";
  private static final TipoUsuarioEnum TIPO_USUARIO_TESTES = TipoUsuarioEnum.REGULAR;
  private static final long CODIGO_TIPO_USUARIO_TESTES = TIPO_USUARIO_TESTES.getCodigo();

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
    SERVICE = (UsuarioServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioService");
  }

  /**
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class UsuarioService.
   */
  @Test
  @Repeat(times = 3)
  public void testPersistEmailsDiferentes() {
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    novoUsuario = SERVICE.persist(novoUsuario);

    assertTrue("Id do usuário não é maior que zero", (novoUsuario.getId() > 0));
  }

  /**
   * Test of persist method, of class UsuarioService.
   * 
   * @throws javax.naming.NamingException caso não encontre o bean
   */
  @Test(expected = EJBException.class)
  public void testPersistEmailsIguais() throws NamingException {
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    SERVICE.persist(novoUsuario);

    novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    try {
      SERVICE.persist(novoUsuario);
    } finally {
      setUpClass();
    }
  }

  /**
   * Test of remove method, of class UsuarioService.
   */
  @Test
  public void testRemovePorUsuario() {
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
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
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
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
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
  public void testGetByNomeAproximado() {
    List<Usuario> listaUsuarios = SERVICE.getByNomeAproximado(NOME_APROXIMADO_USUARIO_TESTES);

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);
  }

  /**
   * Test of getByEmail method, of class UsuarioService.
   */
  @Test
  public void testGetByEmail() {
    List<Usuario> listaUsuarios = SERVICE.getByNomeAproximado(NOME_APROXIMADO_USUARIO_TESTES);

    if (listaUsuarios.size() > 0) {
      Usuario usuario = SERVICE.getByEmail(listaUsuarios.get(0).getEmail());

      assertEquals("E-mail diferente do esperado", listaUsuarios.get(0), usuario);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem usuários testáveis", false);
    }
  }

  /**
   * Test of getByTipo method, of class UsuarioService.
   */
  @Test
  public void testGetByTipoEnum() {
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    SERVICE.persist(novoUsuario);

    List<Usuario> listaUsuarios = SERVICE.getByTipo(TIPO_USUARIO_TESTES);

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);
  }

  /**
   * Test of getByTipo method, of class UsuarioService.
   */
  @Test
  public void testGetByTipoCodigo() {
    String incremento = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));

    Usuario novoUsuario = new Usuario();

    novoUsuario.setTipo(CODIGO_TIPO_USUARIO_TESTES);
    novoUsuario.setNome(NOME_USUARIO_TESTES);
    novoUsuario.setEmail(EMAIL_NOME_USUARIO_TESTES
            .concat(incremento)
            .concat(EMAIL_DOMINIO_USUARIO_TESTES));
    novoUsuario.setPassword(PASSWORD_USUARIO_TESTES);

    SERVICE.persist(novoUsuario);

    List<Usuario> listaUsuarios = SERVICE.getByTipo(CODIGO_TIPO_USUARIO_TESTES);

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Usuario> listaUsuario = SERVICE.getByNomeAproximado(NOME_APROXIMADO_USUARIO_TESTES);

    listaUsuario.forEach((usuario) -> {
      SERVICE.remove(usuario);
    });
  }

}
