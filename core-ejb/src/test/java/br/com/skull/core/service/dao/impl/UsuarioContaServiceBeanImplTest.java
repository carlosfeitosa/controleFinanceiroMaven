package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;
import br.com.skull.core.service.dao.UsuarioContaServiceBean;
import br.com.skull.core.service.dao.UsuarioServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.service.dao.entity.impl.UsuarioConta;
import br.com.skull.core.service.dao.fixture.template.CategoriaTemplate;
import br.com.skull.core.service.dao.fixture.template.ContaTemplate;
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
 * Classe de testes para o serviço DAO de relação entre usuário e conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class UsuarioContaServiceBeanImplTest {

  private static UsuarioContaServiceBean SERVICE;
  private static UsuarioServiceBean SERVICE_USUARIO;
  private static ContaServiceBean SERVICE_CONTA;
  private static CategoriaServiceBean SERVICE_CATEGORIA;

  private static final List<UsuarioConta> LISTA_ENTIDADE = new ArrayList<>();

  private UsuarioConta usuarioContaTestes;

  /**
   * Instância os serviços para o teste.
   *
   * @throws NamingException caso não encontre os beans
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (UsuarioContaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioContaServiceBeanImpl");

    SERVICE_USUARIO = (UsuarioServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioServiceBeanImpl");

    SERVICE_CONTA = (ContaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaServiceBeanImpl");

    SERVICE_CATEGORIA = (CategoriaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaServiceBeanImpl");

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.service.dao.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    usuarioContaTestes = new UsuarioConta();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != usuarioContaTestes) && (null != usuarioContaTestes.getId())) {
      LISTA_ENTIDADE.add(usuarioContaTestes);
    }
  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Teste para persistir Usuario x Conta.
   */
  @Test
  public void testPersist() {
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);

    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    usuarioContaTestes.setConta(novaConta);
    usuarioContaTestes.setUsuario(novoUsuario);

    usuarioContaTestes = SERVICE.persist(usuarioContaTestes);

    assertTrue("Id da relação entre usuário e conta não é maior que zero",
            (usuarioContaTestes.getId() > 0));
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
    SERVICE.persist(usuarioContaTestes);
  }

  /**
   * Testa remover uma relação de usuário conta por modelo.
   */
  @Test
  public void testRemovePorUsuarioConta() {
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);
    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    usuarioContaTestes.setConta(novaConta);
    usuarioContaTestes.setUsuario(novoUsuario);

    usuarioContaTestes = SERVICE.persist(usuarioContaTestes);

    SERVICE.remove(usuarioContaTestes);
  }

  /**
   * Testa remover uma relação de usuário conta por id.
   */
  @Test
  public void testRemovePorId() {
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    Conta novaConta = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    novaConta.setCategoria(novaCategoria);
    novaConta = SERVICE_CONTA.persist(novaConta);

    Usuario novoUsuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALIDO);
    novoUsuario = SERVICE_USUARIO.persist(novoUsuario);

    usuarioContaTestes.setConta(novaConta);
    usuarioContaTestes.setUsuario(novoUsuario);

    usuarioContaTestes = SERVICE.persist(usuarioContaTestes);

    SERVICE.remove(usuarioContaTestes.getId());
  }

  /**
   * Testa recuperar uma relação usuário conta por id.
   */
  @Test
  public void testGetById() {
    List<UsuarioConta> listaUsuarioContaTeste
            = SERVICE.getByConta(LISTA_ENTIDADE.get(0).getConta());

    assertTrue("Lista de relações entre usuários e conta não é maior que zero",
            listaUsuarioContaTeste.size() > 0);

    UsuarioConta usuarioContaTeste = SERVICE.getById(listaUsuarioContaTeste.get(0).getId());

    assertEquals("Relação entre usuário e conta diferente do esperado",
            listaUsuarioContaTeste.get(0), usuarioContaTeste);
  }

  /**
   * Testa recuperar uma relação usuário conta por conta.
   */
  @Test
  public void testGetByConta() {
    List<UsuarioConta> listaUsuarioContaTeste
            = SERVICE.getByConta(LISTA_ENTIDADE.get(0).getConta());

    assertTrue("Lista de relações entre usuário e conta não é maior que zero",
            listaUsuarioContaTeste.size() > 0);

    for (UsuarioConta usuarioConta : listaUsuarioContaTeste) {
      assertEquals("Conta diferente da esperada", LISTA_ENTIDADE.get(0).getConta(),
              usuarioConta.getConta());
    }
  }

  /**
   * Testa recuperar uma relação usuário conta por usuário.
   */
  @Test
  public void testGetByUsuario() {
    List<UsuarioConta> listaUsuarioContaTeste
            = SERVICE.getByUsuario(LISTA_ENTIDADE.get(0).getUsuario());

    assertTrue("Lista de relações entre usuário e conta não é maior que zero",
            listaUsuarioContaTeste.size() > 0);

    for (UsuarioConta usuarioConta : listaUsuarioContaTeste) {
      assertEquals("Usuário diferente do esperado", LISTA_ENTIDADE.get(0).getUsuario(),
              usuarioConta.getUsuario());
    }
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (UsuarioConta usuarioConta : LISTA_ENTIDADE) {
      SERVICE.remove(usuarioConta);

      SERVICE_CONTA.remove(usuarioConta.getConta());
      SERVICE_CATEGORIA.remove(usuarioConta.getConta().getCategoria());
      SERVICE_USUARIO.remove(usuarioConta.getUsuario());
    }
  }
}
