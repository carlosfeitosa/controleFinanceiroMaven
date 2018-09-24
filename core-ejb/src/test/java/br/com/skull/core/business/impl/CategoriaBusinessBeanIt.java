package br.com.skull.core.business.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.business.CategoriaBusinessBean;
import br.com.skull.core.business.exception.CategoriaContaSemPaiException;
import br.com.skull.core.business.exception.CategoriaLancamentoSemPaiException;
import br.com.skull.core.business.exception.CategoriaLogSemPaiException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.junit.runner.EnterpriseRunner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 * Classe de testes integrados para a o bean de categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class CategoriaBusinessBeanIt {

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String NOME_EDICAO_CATEGORIA_TESTES = "__IGNORE-CategoriaTestesEdit";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";

  private static CategoriaBusinessBean BEAN;

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
  }

  /**
   * Finaliza serviços pendentes.
   */
  @AfterClass
  public static void tearDown() {
    cleanUp();
  }

  /**
   * Teste integrado para listar e persistir categoria pai.
   *
   * @throws java.lang.Exception caso não consiga persistir
   */
  @Test
  public void testListarVazioEPersistirCategoriasPai() throws Exception {
    cleanUp();

    List<CategoriaDto> listaCategorias = BEAN.listarCategoriasPai();

    assertEquals("Lista de categorias diferente de vazio", null, listaCategorias);

    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    categoria = BEAN.persistirCategoriaPai(categoria);

    CategoriaDto categoria2 = BEAN.listarCategoriaPorId(categoria.getId());

    categoria2.setNome(NOME_EDICAO_CATEGORIA_TESTES);

    BEAN.persistirCategoriaPai(categoria2);

    listaCategorias = BEAN.listarCategoriasPai();

    assertTrue("Lista de categorias não é maior que zero", listaCategorias.size() > 0);

    CategoriaDto ultimaCategoria = listaCategorias.get(listaCategorias.size() - 1);

    assertEquals("Nomes das categorias não são iguais", NOME_EDICAO_CATEGORIA_TESTES,
            categoria2.getNome());

    assertEquals("Nomes das categorias não são iguais", categoria2.getNome(),
            ultimaCategoria.getNome());
  }

  /**
   * Teste integrado para persistir e listar categorias de contas.
   *
   * @throws java.lang.Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriaDeContas() throws Exception {
    CategoriaDto categoriaPai = new CategoriaDto();

    categoriaPai.setNome(NOME_CATEGORIA_TESTES);
    categoriaPai.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoria.setIdCategoriaPai(categoriaPai.getId());

    BEAN.persistirCategoriaDeConta(categoria);

    List<CategoriaDto> listaCategoriasDeContas = BEAN.listarCategoriasDeContas();

    assertTrue("Lista de categorias não é maior que zero", listaCategoriasDeContas.size() > 0);

    CategoriaDto ultimaCategoria = listaCategoriasDeContas.get(0);

    assertEquals("Nome da categoria diferente", NOME_CATEGORIA_TESTES, ultimaCategoria.getNome());
  }

  /**
   * Teste para garantir que uma categoria de conta não pode persistir sem uma categoria pai.
   *
   * @throws CategoriaContaSemPaiException caso não seja informado uma categoria pai
   */
  @Test(expected = CategoriaContaSemPaiException.class)
  public void testPersistirCategoriaDeContasCategoriaPaiVazia()
          throws CategoriaContaSemPaiException {
    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    BEAN.persistirCategoriaDeConta(categoria);
  }

  /**
   * Teste integrado para persistir e listar categorias de lançamento.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriasDeLancamentos() throws Exception {
    CategoriaDto categoriaPai = new CategoriaDto();

    categoriaPai.setNome(NOME_CATEGORIA_TESTES);
    categoriaPai.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoria.setIdCategoriaPai(categoriaPai.getId());

    BEAN.persistirCategoriaDeLancamento(categoria);

    List<CategoriaDto> listaCategoriasDeLancamento = BEAN.listarCategoriasDeLancamentos();

    assertTrue("Lista de categorias não é maior que zero", listaCategoriasDeLancamento.size() > 0);

    CategoriaDto ultimaCategoria = listaCategoriasDeLancamento.get(0);

    assertEquals("Nome da categoria diferente", NOME_CATEGORIA_TESTES, ultimaCategoria.getNome());
  }

  /**
   * Teste para garantir que uma categoria de lançamento não pode persistir sem uma categoria pai.
   *
   * @throws CategoriaLancamentoSemPaiException caso não seja informado uma categoria pai
   */
  @Test(expected = CategoriaLancamentoSemPaiException.class)
  public void testPersistirCategoriaDeLancamentoCategoriaPaiVazia()
          throws CategoriaLancamentoSemPaiException {
    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    BEAN.persistirCategoriaDeLancamento(categoria);
  }

  /**
   * Teste integrado para persistir e listar categorias de log.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test
  public void testPersistirListarCategoriasDeLOg() throws Exception {
    CategoriaDto categoriaPai = new CategoriaDto();

    categoriaPai.setNome(NOME_CATEGORIA_TESTES);
    categoriaPai.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoria.setIdCategoriaPai(categoriaPai.getId());

    BEAN.persistirCategoriaDeLog(categoria);

    List<CategoriaDto> listaCategoriasDeLog = BEAN.listarCategoriasDeLog();

    assertTrue("Lista de categorias não é maior que zero", listaCategoriasDeLog.size() > 0);

    CategoriaDto ultimaCategoria = listaCategoriasDeLog.get(0);

    assertEquals("Nome da categoria diferente", NOME_CATEGORIA_TESTES, ultimaCategoria.getNome());
  }

  /**
   * Teste para garantir que uma categoria de log não pode persistir sem uma categoria pai.
   *
   * @throws CategoriaLogSemPaiException caso não seja informado uma categoria pai
   */
  @Test(expected = CategoriaLogSemPaiException.class)
  public void testPersistirCategoriaDeLogCategoriaPaiVazia()
          throws CategoriaLogSemPaiException {
    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    BEAN.persistirCategoriaDeLog(categoria);
  }

  /**
   * Teste para garantir que uma categoria pai não pode ter uma categoria pai.
   *
   * @throws Exception caso não consiga persistir
   */
  @Test(expected = CategoriaPaiNaoVaziaException.class)
  public void testPersistirCategoriaPaiCategoriaPaiNaoVazia() throws Exception {
    CategoriaDto categoriaPai = new CategoriaDto();

    categoriaPai.setNome(NOME_CATEGORIA_TESTES);
    categoriaPai.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    categoriaPai = BEAN.persistirCategoriaPai(categoriaPai);

    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoria.setIdCategoriaPai(categoriaPai.getId());

    BEAN.persistirCategoriaPai(categoria);
  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {
    List<CategoriaDto> listaCategorias = new ArrayList<>();

    List<CategoriaDto> listaCategoriasDeContas = BEAN.listarCategoriasDeContas();

    if (null != listaCategoriasDeContas) {
      listaCategorias.addAll(listaCategoriasDeContas);
    }

    List<CategoriaDto> listaCategoriasDeLancamento = BEAN.listarCategoriasDeLancamentos();

    if (null != listaCategoriasDeLancamento) {
      listaCategorias.addAll(listaCategoriasDeLancamento);
    }

    List<CategoriaDto> listaCategoriasDeLog = BEAN.listarCategoriasDeLog();

    if (null != listaCategoriasDeLog) {
      listaCategorias.addAll(listaCategoriasDeLog);
    }

    List<CategoriaDto> listaCategoriasPai = BEAN.listarCategoriasPai();

    if (null != listaCategoriasPai) {
      listaCategorias.addAll(listaCategoriasPai);
    }

    for (CategoriaDto categoria : listaCategorias) {
      BEAN.removerCategoria(categoria);
    }
  }
}
