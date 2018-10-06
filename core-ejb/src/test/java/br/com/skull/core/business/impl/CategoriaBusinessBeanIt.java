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
import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.junit.runner.EnterpriseRunner;

import org.junit.AfterClass;
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
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDown() {
    cleanUp();
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
    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoria = BEAN.persistirCategoriaPai(categoria);

    CategoriaDto categoriaRecuperada = BEAN.listarCategoriaPorId(categoria.getId());

    assertEquals("Categorias não são iguais", categoria, categoriaRecuperada);

    String nomeCategoriaEdit = categoria.getNome().concat(categoria.getNome());

    categoria.setNome(nomeCategoriaEdit);

    categoria = BEAN.persistirCategoriaPai(categoria);

    LISTA_DTO_PAI.add(categoria);

    List<CategoriaDto> listaCategorias = BEAN.listarCategoriasPai();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategorias) {
      if (categoria.getId() == item.getId()) {
        itemFound = true;

        assertEquals("Categorias não são iguais", categoria, item);
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
    CategoriaDto categoriaPai = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    LISTA_DTO_PAI.add(categoriaPai);

    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoria.setIdCategoriaPai(categoriaPai.getId());

    categoria = BEAN.persistirCategoriaDeConta(categoria);

    LISTA_DTO.add(categoria);

    List<CategoriaDto> listaCategoriasDeContas = BEAN.listarCategoriasDeContas();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategoriasDeContas) {
      if (categoria.getId() == item.getId()) {
        itemFound = true;

        assertEquals("Categorias não são iguais", categoria, item);
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
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    BEAN.persistirCategoriaDeConta(categoria);
  }

  /**
   * Teste integrado para persistir e listar categorias de lançamento.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriasDeLancamentos() throws Exception {
    CategoriaDto categoriaPai = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    LISTA_DTO_PAI.add(categoriaPai);

    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoria.setIdCategoriaPai(categoriaPai.getId());

    categoria = BEAN.persistirCategoriaDeLancamento(categoria);

    LISTA_DTO.add(categoria);

    List<CategoriaDto> listaCategoriasDeLancamento = BEAN.listarCategoriasDeLancamentos();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategoriasDeLancamento) {
      if (categoria.getId() == item.getId()) {
        assertEquals("Categorias não são iguais", categoria, item);

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
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    BEAN.persistirCategoriaDeLancamento(categoria);
  }

  /**
   * Teste integrado para persistir e listar categorias de log.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriasDeLog() throws Exception {
    CategoriaDto categoriaPai = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    LISTA_DTO_PAI.add(categoriaPai);

    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoria.setIdCategoriaPai(categoriaPai.getId());

    categoria = BEAN.persistirCategoriaDeLog(categoria);

    LISTA_DTO.add(categoria);

    List<CategoriaDto> listaCategoriasDeLog = BEAN.listarCategoriasDeLog();

    boolean itemFound = false;

    for (CategoriaDto item : listaCategoriasDeLog) {
      if (categoria.getId() == item.getId()) {
        assertEquals("Categorias não são iguais", categoria, item);

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
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    BEAN.persistirCategoriaDeLog(categoria);
  }

  /**
   * Teste para garantir que uma categoria pai não pode ter uma categoria pai.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test(expected = CategoriaPaiNaoVaziaException.class)
  public void testPersistirCategoriaPaiCategoriaPaiNaoVazia() throws Exception {
    CategoriaDto categoriaPai = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    LISTA_DTO_PAI.add(categoriaPai);

    CategoriaDto categoria = Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO_SEM_PAI);

    categoria.setIdCategoriaPai(categoriaPai.getId());

    BEAN.persistirCategoriaPai(categoria);
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
}
