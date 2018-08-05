package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJBException;

/**
 * Classe de testes para o serviço DAO de Categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class CategoriaServiceTest {

  private static CategoriaServiceRemote SERVICE;

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String NOME_CATEGORIA_TESTES_PAI = "__IGNORE-CategoriaTestesPai";
  private static final String NOME_CATEGORIA_TESTES_FILHA = "__IGNORE-CategoriaTestesFilha";
  private static final String NOME_CATEGORIA_TESTES_PARCIAL = "__IGNORE-";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";
  private static final long CODIGO_TIPO_CATEGORIA_TESTES
          = TipoCategoriaEnum.CATEGORIA.getCodigo();
  private static final TipoCategoriaEnum TIPO_CATEGORIA_ENUM_TESTES = TipoCategoriaEnum.CATEGORIA;

  public CategoriaServiceTest() {
  }

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Inicializa container e serviços.
   *
   * @throws Exception caso não encontre o bean
   */
  @BeforeClass
  public static void setUp() throws Exception {
    SERVICE = (CategoriaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");
  }

  /**
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDown() {
    cleanUp();
  }

  /**
   * Test of persist method, of class CategoriaService.
   */
  @Test
  @Repeat(times = 3)
  public void testPersist() {
    String sulfixo = Calendar.getInstance().getTime().toString();

    Categoria novaCategoria = new Categoria();

    novaCategoria.setNome(NOME_CATEGORIA_TESTES);
    novaCategoria.setDescricao(DESCRICAO_CATEGORIA_TESTES.concat(sulfixo));
    novaCategoria.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    novaCategoria = SERVICE.persist(novaCategoria);

    assertTrue("Id da categoria não é maior que zero", (novaCategoria.getId() > 0));
  }

  /**
   * Test of persist method, of class CategoriaService.
   */
  @Test(expected = EJBException.class)
  public void testPersistFail() {
    String sulfixo = Calendar.getInstance().getTime().toString();

    Categoria novaCategoria = new Categoria();

    novaCategoria.setDescricao(DESCRICAO_CATEGORIA_TESTES.concat(sulfixo));
    novaCategoria.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    novaCategoria = SERVICE.persist(novaCategoria);

    assertTrue("Id da categoria não é maior que zero", (novaCategoria.getId() > 0));
  }

  /**
   * Test of getCategoriasTodas method, of class CategoriaService.
   */
  @Test
  public void testGetTodas() {
    Categoria novaCategoria = new Categoria();

    novaCategoria.setNome(NOME_CATEGORIA_TESTES);
    novaCategoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    novaCategoria.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    SERVICE.persist(novaCategoria);

    List<Categoria> listaCategorias = SERVICE.getTodas();

    assertTrue("Quantidade de itens não é maior que zero", (listaCategorias.size() > 0));
  }

  /**
   * Test of remove method, of class CategoriaService.
   */
  @Test
  public void testRemovePorId() {
    List<Categoria> listaCategorias = SERVICE.getByNome(NOME_CATEGORIA_TESTES);

    if (listaCategorias.size() > 0) {
      SERVICE.remove(listaCategorias.get(0).getId());
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of remove method, of class CategoriaService.
   */
  @Test
  public void testRemovePorCategoria() {
    List<Categoria> listaCategorias = SERVICE.getByNome(NOME_CATEGORIA_TESTES);

    if (listaCategorias.size() > 0) {
      SERVICE.remove(listaCategorias.get(0));
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getById method, of class CategoriaService.
   */
  @Test
  public void testGetById() {
    List<Categoria> listaCategorias = SERVICE.getTodas();

    if (listaCategorias.size() > 0) {
      Categoria categoriaEsperada = listaCategorias.get(0);
      Categoria categoriaTeste = SERVICE.getById(categoriaEsperada.getId());

      assertEquals("Categorias comparadas não são iguais", categoriaEsperada, categoriaTeste);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getPorNome method, of class CategoriaService.
   */
  @Test
  public void testGetPorNome() {
    List<Categoria> listaCategorias = SERVICE.getByNome(NOME_CATEGORIA_TESTES);

    if (listaCategorias.size() > 0) {
      Categoria categoriaRecuperada = listaCategorias.get(0);

      assertTrue("Não foi possível recuperar categoria de testes",
              (categoriaRecuperada.getId() > 0));

      assertEquals("Nome da categoria diferente da experada", NOME_CATEGORIA_TESTES,
              categoriaRecuperada.getNome());
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getPorNomeAproximado method, of class CategoriaService.
   */
  @Test
  public void testGetCategoriaspPorNomeAproximado() {
    List<Categoria> listaCategorias = SERVICE.getByNomeAproximado(NOME_CATEGORIA_TESTES_PARCIAL);

    if (listaCategorias.size() > 0) {
      Categoria categoriaRecuperada = listaCategorias.get(0);

      assertTrue("Não foi possível recuperar categoria de testes",
              (categoriaRecuperada.getId() > 0));

      assertTrue("Nome da categoria diferente da experada",
              categoriaRecuperada.getNome().contains(NOME_CATEGORIA_TESTES_PARCIAL));
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getPorTipo method, of class CategoriaService.
   */
  @Test
  public void testGetCategoriasPorTipoCodigo() {
    List<Categoria> listaCategorias = SERVICE.getByTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    if (listaCategorias.size() > 0) {
      Categoria categoriaRecuperada = listaCategorias.get(0);

      assertTrue("Não foi possível recuperar categoria de testes",
              (categoriaRecuperada.getId() > 0));

      assertEquals("Codigo do tipo de categoria diferente do esperado",
              categoriaRecuperada.getTipo(), CODIGO_TIPO_CATEGORIA_TESTES);
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getCategoriasPorTipo method, of class CategoriaService.
   */
  @Test
  public void testGetCategoriasPorTipoEnumTipoCategoria() {
    List<Categoria> listaCategorias = SERVICE.getByTipo(TIPO_CATEGORIA_ENUM_TESTES);

    if (listaCategorias.size() > 0) {
      Categoria categoriaRecuperada = listaCategorias.get(0);

      assertTrue("Não foi possível recuperar categoria de testes",
              (categoriaRecuperada.getId() > 0));

      assertEquals("Tipo de categoria diferente do esperado",
              categoriaRecuperada.getTipo(), TIPO_CATEGORIA_ENUM_TESTES.getCodigo());
    } else {
      assertTrue("Não é possível realizar o teste porque não existem categorias testáveis", false);
    }
  }

  /**
   * Test of getCategoriasFilhas method, of class CategoriaService.
   */
  @Test
  public void testGetFilhasPorCategoriaPai() {
    Categoria categoriaPai = new Categoria();

    categoriaPai.setNome(NOME_CATEGORIA_TESTES_PAI);
    categoriaPai.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoriaPai.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);

    categoriaPai = SERVICE.persist(categoriaPai);

    Categoria categoriaFilha = new Categoria();

    categoriaFilha.setNome(NOME_CATEGORIA_TESTES_FILHA);
    categoriaFilha.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoriaFilha.setTipo(CODIGO_TIPO_CATEGORIA_TESTES);
    categoriaFilha.setCategoria(categoriaPai);

    categoriaFilha = SERVICE.persist(categoriaFilha);

    List<Categoria> listaCategoriasFilhas = SERVICE.getFilhas(categoriaPai);

    assertTrue("Lista de categorias filhas não é maior que zero",
            (listaCategoriasFilhas.size() > 0));

    assertEquals("Categorias comparadas não são iguais",
            categoriaFilha, listaCategoriasFilhas.get(0));
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<Categoria> listaCategorias = new ArrayList<>();

    listaCategorias.addAll(SERVICE.getByNome(NOME_CATEGORIA_TESTES));
    listaCategorias.addAll(SERVICE.getByNome(NOME_CATEGORIA_TESTES_FILHA));
    listaCategorias.addAll(SERVICE.getByNome(NOME_CATEGORIA_TESTES_PAI));

    listaCategorias.forEach((categoria) -> {
      SERVICE.remove(categoria);
    });
  }
}
