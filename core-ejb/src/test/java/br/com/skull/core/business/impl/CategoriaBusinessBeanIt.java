package br.com.skull.core.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

import br.com.skull.core.business.CategoriaBusinessBean;
import br.com.skull.core.business.exception.CategoriaContaSemPaiException;
import br.com.skull.core.business.exception.CategoriaLancamentoSemPaiException;
import br.com.skull.core.business.exception.CategoriaLogSemPaiException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.fixture.template.CategoriaDtoTemplate;
import br.com.skull.core.business.model.impl.CategoriaDto;
import br.com.skull.core.junit.runner.EnterpriseRunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.naming.NamingException;

/**
 * Classe de testes integrados para a o bean de categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class CategoriaBusinessBeanIt {

  private static CategoriaBusinessBean BEAN;

  private static final List<CategoriaDto> LISTA_DTO = new ArrayList<>();
  private static final List<CategoriaDto> LISTA_DTO_PAI = new ArrayList<>();

  CategoriaDto categoriaTestes;
  CategoriaDto categoriaPaiTestes;

  /**
   * Inicializa o bean BO de categoria.
   *
   * @throws NamingException caso não encontre os beans
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    try {
      BEAN = (CategoriaBusinessBean) EnterpriseRunner.getContainer().getContext()
              .lookup("java:global/core-ejb-1.0-SNAPSHOT/CategoriaBusinessBeanImpl");
    } catch (NamingException ex) {
      System.out.println("Recuperando serviço no pacote de classes: ".concat(ex.getMessage()));
      BEAN = (CategoriaBusinessBean) EnterpriseRunner.getContainer().getContext()
              .lookup("java:global/classes/CategoriaBusinessBeanImpl");
    }

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.business.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    categoriaTestes = new CategoriaDto();
    categoriaPaiTestes = new CategoriaDto();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if ((null != categoriaTestes) && (categoriaTestes.getId() > 0)) {
      LISTA_DTO.add(categoriaTestes);
    }

    if ((null != categoriaPaiTestes) && (categoriaPaiTestes.getId() > 0)) {
      LISTA_DTO_PAI.add(categoriaPaiTestes);
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
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    for (CategoriaDto categoria : LISTA_DTO) {
      BEAN.removerCategoria(categoria);
    }

    for (CategoriaDto categoria : LISTA_DTO_PAI) {
      BEAN.removerCategoria(categoria);
    }

    LISTA_DTO.clear();
    LISTA_DTO_PAI.clear();
  }

  /**
   * Teste integrado para validar levantamento de exceção tentando persistir uma categoria inválida.
   *
   * @throws Exception (EJBTransactionRolledbackException)
   */
  @Test(expected = EJBTransactionRolledbackException.class)
  public void testExcecaoTentarPersistirCategoriaPaiInvalida() throws Exception {
    CategoriaDto categoria = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.INVALIDO);

    BEAN.persistirCategoriaPai(categoria);
  }

  /**
   * Teste integrado para validar levantamento de exceção tentando persistir numa categoria nula.
   *
   * @throws Exception (EJBException)
   */
  @Test(expected = EJBException.class)
  public void testExcecaoTentarPersistirCategoriaPaiNula() throws Exception {
    BEAN.persistirCategoriaPai(null);
  }

  /**
   * Teste integrado para listar e persistir categoria pai.
   *
   * @throws java.lang.Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarPorIdECategoriasPai() throws Exception {
    categoriaPaiTestes = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    categoriaPaiTestes = BEAN.persistirCategoriaPai(categoriaPaiTestes);

    CategoriaDto categoriaRecuperada = BEAN.listarCategoriaPorId(categoriaPaiTestes.getId());

    assertEquals("Categorias não são iguais", categoriaPaiTestes, categoriaRecuperada);

    String nomeCategoriaEdit = categoriaPaiTestes.getNome().concat(categoriaPaiTestes.getNome());

    categoriaPaiTestes.setNome(nomeCategoriaEdit);

    categoriaPaiTestes = BEAN.persistirCategoriaPai(categoriaPaiTestes);

    List<CategoriaDto> listaCategorias = BEAN.listarCategoriasPai();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategorias) {
      if (categoriaPaiTestes.getId() == item.getId()) {
        itemFound = true;

        assertEquals("Categorias não são iguais", categoriaPaiTestes, item);
      }
    }

    if (!itemFound) {
      fail("Categoria pai persistida não foi encontrada");
    }
  }

  /**
   * Teste integrado para persistir e listar categorias de contas.
   *
   * @throws java.lang.Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriaDeContas() throws Exception {
    categoriaPaiTestes = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    categoriaPaiTestes = BEAN.persistirCategoriaPai(categoriaPaiTestes);

    categoriaTestes = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO);

    categoriaTestes.setIdCategoriaPai(categoriaPaiTestes.getId());

    categoriaTestes = BEAN.persistirCategoriaDeConta(categoriaTestes);

    List<CategoriaDto> listaCategoriasDeContas = BEAN.listarCategoriasDeContas();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategoriasDeContas) {
      if (categoriaTestes.getId() == item.getId()) {
        itemFound = true;

        assertEquals("Categorias não são iguais", categoriaTestes, item);
      }
    }

    if (!itemFound) {
      fail("Categoria persistida não foi encontrada");
    }
  }

  /**
   * Teste para garantir que uma categoria de conta não pode persistir sem uma categoria pai.
   *
   * @throws CategoriaContaSemPaiException caso não seja informado uma categoria pai
   */
  @Test(expected = CategoriaContaSemPaiException.class)
  public void testPersistirCategoriaDeContasCategoriaPaiVazia()
          throws CategoriaContaSemPaiException {
    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    BEAN.persistirCategoriaDeConta(categoria);
  }

  /**
   * Teste integrado para persistir e listar categorias de lançamento.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriasDeLancamentos() throws Exception {
    categoriaPaiTestes = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    categoriaPaiTestes = BEAN.persistirCategoriaPai(categoriaPaiTestes);

    categoriaTestes = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO);

    categoriaTestes.setIdCategoriaPai(categoriaPaiTestes.getId());

    categoriaTestes = BEAN.persistirCategoriaDeLancamento(categoriaTestes);

    List<CategoriaDto> listaCategoriasDeLancamento = BEAN.listarCategoriasDeLancamentos();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategoriasDeLancamento) {
      if (categoriaTestes.getId() == item.getId()) {
        assertEquals("Categorias não são iguais", categoriaTestes, item);

        itemFound = true;
      }
    }

    if (!itemFound) {
      fail("Categoria persistida não foi encontrada");
    }
  }

  /**
   * Teste para garantir que uma categoria de lançamento não pode persistir sem uma categoria pai.
   *
   * @throws CategoriaLancamentoSemPaiException caso não seja informado uma categoria pai
   */
  @Test(expected = CategoriaLancamentoSemPaiException.class)
  public void testPersistirCategoriaDeLancamentoCategoriaPaiVazia()
          throws CategoriaLancamentoSemPaiException {
    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    BEAN.persistirCategoriaDeLancamento(categoria);
  }

  /**
   * Teste integrado para persistir e listar categorias de log.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriasDeLog() throws Exception {
    categoriaPaiTestes = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    categoriaPaiTestes = BEAN.persistirCategoriaPai(categoriaPaiTestes);

    categoriaTestes = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO);

    categoriaTestes.setIdCategoriaPai(categoriaPaiTestes.getId());

    categoriaTestes = BEAN.persistirCategoriaDeLog(categoriaTestes);

    List<CategoriaDto> listaCategoriasDeLog = BEAN.listarCategoriasDeLog();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategoriasDeLog) {
      if (categoriaTestes.getId() == item.getId()) {
        assertEquals("Categorias não são iguais", categoriaTestes, item);

        itemFound = true;
      }
    }

    if (!itemFound) {
      fail("Categoria persistida não foi encontrada");
    }
  }

  /**
   * Teste para garantir que uma categoria de log não pode persistir sem uma categoria pai.
   *
   * @throws CategoriaLogSemPaiException caso não seja informado uma categoria pai
   */
  @Test(expected = CategoriaLogSemPaiException.class)
  public void testPersistirCategoriaDeLogCategoriaPaiVazia()
          throws CategoriaLogSemPaiException {
    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    BEAN.persistirCategoriaDeLog(categoria);
  }

  /**
   * Teste para garantir que uma categoria pai não pode ter uma categoria pai.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test(expected = CategoriaPaiNaoVaziaException.class)
  public void testPersistirCategoriaPaiCategoriaPaiNaoVazia() throws Exception {
    categoriaPaiTestes = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    categoriaPaiTestes = BEAN.persistirCategoriaPai(categoriaPaiTestes);

    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO);

    categoria.setIdCategoriaPai(categoriaPaiTestes.getId());

    BEAN.persistirCategoriaPai(categoria);
  }
}
