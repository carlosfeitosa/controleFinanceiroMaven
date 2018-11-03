package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;
import br.com.skull.core.service.dao.fixture.template.CategoriaTemplate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;

/**
 * Classe de testes para o serviço DAO de Categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class CategoriaServiceBeanImplTest {

  private static CategoriaServiceBean SERVICE;

  private static final List<Categoria> LISTA_ENTIDADE = new ArrayList<>();

  private Categoria categoriaTestes;

  /**
   * Inicializa serviços.
   *
   * @throws Exception caso não encontre o bean
   */
  @BeforeClass
  public static void setUpClass() throws Exception {
    SERVICE = (CategoriaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaServiceBeanImpl");

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.service.dao.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    categoriaTestes = new Categoria();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != categoriaTestes) && (null != categoriaTestes.getId())) {
      LISTA_ENTIDADE.add(categoriaTestes);
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
   * Testa persistir uma categoria.
   */
  @Test
  public void testPersist() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    assertTrue("Id da categoria não é maior que zero", (categoriaTestes.getId() > 0));
  }

  /**
   * Testa persistir uma categoria com uma categoria pai.
   */
  @Test
  public void testPersistComCategoriaPai() {
    Categoria categoriaPai = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaPai = SERVICE.persist(categoriaPai);

    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    categoriaTestes.setCategoria(categoriaPai);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    assertTrue("Id da categoria não é maior que zero", (categoriaTestes.getId() > 0));
  }

  /**
   * Testa falha na tentativa de persistir uma categoria com categoria pai inválida.
   */
  @Test(expected = EJBException.class)
  public void testPersistComCategoriaPaiFail() {
    Categoria categoriaPai = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    categoriaTestes.setCategoria(categoriaPai);

    categoriaTestes = SERVICE.persist(categoriaTestes);
  }

  /**
   * Testa falha na tentativa de persistir uma categoria nula.
   */
  @Test(expected = EJBException.class)
  public void testPersistFailNull() {
    categoriaTestes = SERVICE.persist(null);
  }

  /**
   * Testa falha na tentativa de persistir uma categoria inválida.
   */
  @Test(expected = EJBException.class)
  public void testPersistFail() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.INVALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);
  }

  /**
   * Testa recuperar todas as categorias.
   */
  @Test
  public void testGetTodas() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    List<Categoria> listaCategorias = SERVICE.getTodas();

    assertTrue("Quantidade de itens não é maior que zero", (listaCategorias.size() > 0));
  }

  /**
   * Testa remover uma categoria por id.
   */
  @Test
  public void testRemovePorId() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    SERVICE.remove(categoriaTestes.getId());

    categoriaTestes = null;
  }

  /**
   * Testa remover uma categoria.
   */
  @Test
  public void testRemovePorCategoria() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    SERVICE.remove(categoriaTestes);

    categoriaTestes = null;
  }

  /**
   * Testa recuperar uma categoria por id.
   */
  @Test
  public void testGetById() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    Categoria categoriaTeste = SERVICE.getById(categoriaTestes.getId());

    assertEquals("Categorias comparadas não são iguais", categoriaTestes, categoriaTeste);
  }

  /**
   * Testa recuperar uma lista de categorias por nome.
   */
  @Test
  public void testGetPorNome() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    List<Categoria> listaCategorias = SERVICE.getByNome(categoriaTestes.getNome());

    assertTrue("Lista de categoria não é maior que zero", listaCategorias.size() > 0);

    for (Categoria categoriaRecuperada : listaCategorias) {
      assertEquals("Nome da categoria diferente da experada", categoriaTestes.getNome(),
              categoriaRecuperada.getNome());
    }
  }

  /**
   * Testa recuperar uma lista de categorias por nome aproximado.
   */
  @Test
  public void testGetCategoriaspPorNomeAproximado() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    String nomeCategoriaParcial = categoriaTestes.getNome()
            .substring(0, Math.round(categoriaTestes.getNome().length() / 2));

    List<Categoria> listaCategorias = SERVICE.getByNomeAproximado(nomeCategoriaParcial);

    assertTrue("Lista de categoria não é maior que zero", listaCategorias.size() > 0);

    for (Categoria categoriaRecuperada : listaCategorias) {
      assertTrue("Nome da categoria diferente da experada",
              categoriaRecuperada.getNome().contains(nomeCategoriaParcial));
    }
  }

  /**
   * Testa recuperar uma lista de categorias por seu tipo, em código.
   */
  @Test
  public void testGetCategoriasPorTipoCodigo() {
    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    List<Categoria> listaCategorias = SERVICE.getByTipo(categoriaTestes.getTipo());

    assertTrue("Lista de categoria não é maior que zero", listaCategorias.size() > 0);

    for (Categoria categoriaRecuperada : listaCategorias) {
      assertEquals("Codigo do tipo de categoria diferente do esperado",
              categoriaTestes.getTipo(), categoriaRecuperada.getTipo());
    }
  }

  /**
   * Testa recuperar uma lista de categorias por seu tipo enum.
   */
  @Test
  public void testGetCategoriasPorTipoEnumTipoCategoria() {
    TipoCategoriaEnum tipoTeste = TipoCategoriaEnum.CATEGORIA;

    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaTestes.setTipo(tipoTeste.getCodigo());

    categoriaTestes = SERVICE.persist(categoriaTestes);

    List<Categoria> listaCategorias = SERVICE.getByTipo(tipoTeste);

    assertTrue("Lista de categoria não é maior que zero", listaCategorias.size() > 0);

    for (Categoria categoriaRecuperada : listaCategorias) {
      assertEquals("Tipo de categoria diferente do esperado",
              tipoTeste.getCodigo(), categoriaRecuperada.getTipo());
    }
  }

  /**
   * Testa recuperar uma lista de categorias através da categoria pai.
   */
  @Test
  public void testGetFilhasPorCategoriaPai() {
    Categoria categoriaPai = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);

    categoriaPai = SERVICE.persist(categoriaPai);

    categoriaTestes = Fixture.from(Categoria.class).gimme(CategoriaTemplate.VALIDO);
    categoriaTestes.setCategoria(categoriaPai);

    categoriaTestes = SERVICE.persist(categoriaTestes);

    List<Categoria> listaCategoriasFilhas = SERVICE.getFilhas(categoriaPai);

    assertTrue("Lista de categorias filhas não é maior que zero",
            (listaCategoriasFilhas.size() > 0));

    for (Categoria categoriaFilha : listaCategoriasFilhas) {
      assertEquals("Categorias comparadas não são iguais", categoriaTestes, categoriaFilha);
    }
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (Categoria categoria : LISTA_ENTIDADE) {
      SERVICE.remove(categoria);

      if (null != categoria.getCategoria()) {
        SERVICE.remove(categoria.getCategoria());
      }
    }
  }
}
