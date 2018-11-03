package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.enums.TipoUsuarioEnum;
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
 * Classe de testes para o serviço DAO de Usuário.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class UsuarioServiceBeanImplTest {

  private static UsuarioServiceBean SERVICE;

  private static final List<Usuario> LISTA_ENTIDADE = new ArrayList<>();

  Usuario usuarioTestes;

  /**
   * Inicializa serviços.
   *
   * @throws NamingException caso não ache o bean do serviço
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (UsuarioServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioServiceBeanImpl");

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.service.dao.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    usuarioTestes = new Usuario();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != usuarioTestes) && (null != usuarioTestes.getId())) {
      LISTA_ENTIDADE.add(usuarioTestes);
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
   * Teste para persistir usuário.
   */
  @Test
  public void testPersist() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    assertTrue("Id do usuário não é maior que zero", (usuarioTestes.getId() > 0));
  }

  /**
   * Testa se uma exceção é lançada ao tentar persistir usuários diferentes com o mesmo e-mail.
   *
   * @throws javax.naming.NamingException caso não encontre o bean
   */
  @Test(expected = EJBException.class)
  public void testPersistEmailsIguais() throws NamingException {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    Usuario usuarioMesmoEmail = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioMesmoEmail.setEmail(usuarioTestes.getEmail());

    try {
      SERVICE.persist(usuarioMesmoEmail);

    } finally {
      setUpClass();
    }
  }

  /**
   * Testa se uma exceção está sendo levantada ao tentar salvar um usuário null.
   *
   * @throws javax.naming.NamingException caso não encontre o bean
   */
  @Test(expected = EJBException.class)
  public void testPersistFailNull() throws NamingException {
    try {
      SERVICE.persist(null);

    } finally {
      setUpClass();
    }
  }

  /**
   * Testa se uma exceção está sendo levantada ao tentar salvar um usuário inválido.
   *
   * @throws javax.naming.NamingException caso não encontre o bean
   */
  @Test(expected = EJBException.class)
  public void testPersistInvalido() throws NamingException {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.INVALIDO);

    try {
      usuarioTestes = SERVICE.persist(usuarioTestes);

    } finally {
      setUpClass();
    }
  }

  /**
   * Testa se é possível remover um usuário através do objeto Usuário.
   */
  @Test
  public void testRemovePorUsuario() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    SERVICE.remove(usuarioTestes);
  }

  /**
   * Testa se é possível remover um usuário através de seu id.
   */
  @Test
  public void testRemovePorId() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    SERVICE.remove(usuarioTestes.getId());
  }

  /**
   * Testa recuperar todos os usuários.
   */
  @Test
  public void testGetTodos() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    List<Usuario> listaTodosUsuarios = SERVICE.getTodos();

    assertTrue("Lista de usuários menor que zero", listaTodosUsuarios.size() > 0);
  }

  /**
   * Testa recuperar um usuário por id.
   */
  @Test
  public void testGetById() {
    List<Usuario> listaUsuarios = SERVICE.getTodos();

    assertTrue("Lista de usuários não é maior que zero", listaUsuarios.size() > 0);

    Usuario usuarioEsperado = listaUsuarios.get(0);
    Usuario usuarioTeste = SERVICE.getById(usuarioEsperado.getId());

    assertEquals("Usuários comparados não são iguais", usuarioEsperado, usuarioTeste);
  }

  /**
   * Testa recuperar por nome de usuário.
   */
  @Test
  public void testGetByNome() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    List<Usuario> listaUsuarios = SERVICE.getByNome(usuarioTestes.getNome());

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);

    assertEquals(usuarioTestes.getNome(), listaUsuarios.get(0).getNome());
  }

  /**
   * Testa recuperar por nome aproximado de um usuário.
   */
  @Test
  public void testGetByNomeAproximado() {
    List<Usuario> listaUsuarios = SERVICE.getTodos();

    String nomeAproximado = listaUsuarios.get(0).getNome().substring(0, 10);

    listaUsuarios = SERVICE.getByNomeAproximado(nomeAproximado);

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);

    for (Usuario usuario : listaUsuarios) {
      assertTrue("Nome aproximado não está contido no nome completo",
              usuario.getNome().contains(nomeAproximado));
    }
  }

  /**
   * Testa recuperar um usuário através de seu e-mail.
   */
  @Test
  public void testGetByEmail() {
    List<Usuario> listaUsuarios = SERVICE.getTodos();

    assertTrue("Lista de usuários não é maior que zero", listaUsuarios.size() > 0);

    Usuario usuario = SERVICE.getByEmail(listaUsuarios.get(0).getEmail());

    assertEquals("E-mail diferente do esperado", listaUsuarios.get(0), usuario);
  }

  /**
   * Testa recuperar usuários por tipo (enum).
   */
  @Test
  public void testGetByTipoEnum() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    List<Usuario> listaUsuarios
            = SERVICE.getByTipo(TipoUsuarioEnum.valueOf(usuarioTestes.getTipo()));

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);

    assertEquals("Tipo de usuário diferente do esperado",
            TipoUsuarioEnum.valueOf(usuarioTestes.getTipo()).getCodigo(),
            listaUsuarios.get(0).getTipo());
  }

  /**
   * Testa recuperar usuários por tipo (código).
   */
  @Test
  public void testGetByTipoCodigo() {
    usuarioTestes = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    usuarioTestes = SERVICE.persist(usuarioTestes);

    List<Usuario> listaUsuarios
            = SERVICE.getByTipo(usuarioTestes.getTipo());

    assertTrue("Lista de usuários menor que zero: ", listaUsuarios.size() > 0);

    assertEquals("Tipo de usuário diferente do esperado",
            usuarioTestes.getTipo(),
            listaUsuarios.get(0).getTipo());
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (Usuario usuario : LISTA_ENTIDADE) {
      SERVICE.remove(usuario);
    }
  }

}
