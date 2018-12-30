package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.fixture.template.CategoriaTemplate;
import br.com.skull.core.service.dao.fixture.template.ContaTemplate;

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
 * Classe de testes para o serviço DAO de Conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class ContaServiceBeanImplTest {

  private static ContaServiceBean SERVICE;
  private static CategoriaServiceBean SERVICE_CATEGORIA;

  private static final List<Conta> LISTA_ENTIDADE = new ArrayList<>();

  private Conta contaTestes;

  /**
   * Inicializa serviços.
   *
   * @throws NamingException caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (ContaServiceBean) EnterpriseRunner.getContainer().getContext()
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
    contaTestes = new Conta();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != contaTestes) && (null != contaTestes.getId())) {
      LISTA_ENTIDADE.add(contaTestes);
    }
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
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (Conta conta : LISTA_ENTIDADE) {
      SERVICE.remove(conta);

      if (null != conta.getCategoria()) {
        SERVICE_CATEGORIA.remove(conta.getCategoria());
      }
    }
  }

  /**
   * Testa persistir uma conta.
   */
  @Test
  public void testPersist() {
    contaTestes = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    contaTestes.setCategoria(novaCategoria);

    contaTestes = SERVICE.persist(contaTestes);

    assertTrue("Id da conta não é maior que zero", (contaTestes.getId() > 0));
  }

  /**
   * Teste para garantir que uma exceção será lançada caso uma categoria não seja informada.
   */
  @Test(expected = EJBException.class)
  public void testFailPersistirSemCategoria() {
    contaTestes = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);

    contaTestes = SERVICE.persist(contaTestes);

    assertTrue("Id da conta não é maior que zero", (contaTestes.getId() > 0));
  }

  /**
   * Testa para falhar na tentativa de persistir uma conta nula.
   */
  @Test(expected = EJBException.class)
  public void testPersistFailNull() {
    contaTestes = SERVICE.persist(null);
  }

  /**
   * Teste para garantir que uma exceção será lançada caso uma conta inválida seja informada.
   */
  @Test(expected = EJBException.class)
  public void testFailPersistirContaInvalida() {
    contaTestes = Fixture.from(Conta.class).gimme(ContaTemplate.INVALIDO);

    contaTestes = SERVICE.persist(contaTestes);

    assertTrue("Id da conta não é maior que zero", (contaTestes.getId() > 0));
  }

  /**
   * Teste para validar a recuperação de todas as categorias.
   */
  @Test
  public void testGetTodas() {
    contaTestes = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    contaTestes.setCategoria(novaCategoria);

    contaTestes = SERVICE.persist(contaTestes);

    List<Conta> listaContas = SERVICE.getTodas();

    assertTrue("Quantidade de itens não é maior que zero", (listaContas.size() > 0));
  }

  /**
   * Teste para garantir que uma conta pode ser excluída por id.
   */
  @Test
  public void testRemovePorId() {
    contaTestes = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    contaTestes.setCategoria(novaCategoria);

    contaTestes = SERVICE.persist(contaTestes);

    SERVICE.remove(contaTestes.getId());
  }

  /**
   * Teste para garantir que uma conta pode ser excluída por conta.
   */
  @Test
  public void testRemovePorConta() {
    contaTestes = Fixture.from(Conta.class).gimme(ContaTemplate.VALIDO);
    Categoria novaCategoria = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    novaCategoria = SERVICE_CATEGORIA.persist(novaCategoria);

    contaTestes.setCategoria(novaCategoria);

    contaTestes = SERVICE.persist(contaTestes);

    SERVICE.remove(contaTestes);
  }

  /**
   * Teste para recuperar uma conta através do id.
   */
  @Test
  public void testGetById() {
    List<Conta> listaContas = SERVICE.getTodas();

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    Conta contaEsperada = listaContas.get(0);
    Conta contaTeste = SERVICE.getById(contaEsperada.getId());

    assertEquals("Contas comparadas não são iguais", contaEsperada, contaTeste);
  }

  /**
   * Teste para recuperar uma conta por nome.
   */
  @Test
  public void testGetByName() {
    List<Conta> listaTodasContas = SERVICE.getTodas();

    assertTrue("Lista de contas não é maior que zero", listaTodasContas.size() > 0);

    List<Conta> listaContas = SERVICE.getByNome(listaTodasContas.get(0).getNome());

    assertTrue("Lista de contas não é maior que zero", listaContas.size() > 0);

    for (Conta conta : listaContas) {
      assertEquals("Nome da conta diferente da experada", listaTodasContas.get(0).getNome(),
              conta.getNome());
    }
  }

  /**
   * Testa recuperar uma conta por categoria.
   */
  @Test
  public void testGetByCategoria() {
    List<Conta> listaTodasContas = SERVICE.getTodas();

    assertTrue("Lista de contas não é maior que zero", listaTodasContas.size() > 0);

    List<Conta> listaContas = SERVICE.getByCategoria(listaTodasContas.get(0).getCategoria());

    assertTrue("Lista de contas não é maior que zero",
            (listaContas.size() > 0));

    for (Conta conta : listaContas) {
      assertEquals("Categorias comparadas não são iguais",
              listaTodasContas.get(0).getCategoria(), conta.getCategoria());
    }

  }
}
