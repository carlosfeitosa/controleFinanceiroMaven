package br.com.skull.core.business.impl;

import br.com.skull.core.business.CategoriaBeanRemote;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;

import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.junit.runner.EnterpriseRunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.NamingException;

/**
 * Classe de testes para a o bean de categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class CategoriaBeanIt {

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";

  private static CategoriaBeanRemote BEAN;

  @BeforeClass
  public static void setUpClass() throws NamingException {
    BEAN = (CategoriaBeanRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaBean");
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After

  public void tearDown() {
  }

  /**
   * Test of listarCategoriasPai method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testListarCategoriasPai() {

  }

  /**
   * Test of listarCategoriasDeContas method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testListarCategoriasDeContas() {

  }

  /**
   * Test of listarCategoriasDeLancamentos method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testListarCategoriasDeLancamentos() {

  }

  /**
   * Test of listarCategoriasDeLog method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testListarCategoriasDeLog() {

  }

  /**
   * Test of persistirCategoriaPai method, of class CategoriaBean.
   *
   * @throws CategoriaPaiNaoVaziaException caso haja indicado uma categoria pai
   */
  @Test
  public void testPersistirCategoriaPai() throws CategoriaPaiNaoVaziaException {
    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    BEAN.persistirCategoriaPai(categoria);
  }

  /**
   * Test of persistirCategoriaDeConta method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testPersistirCategoriaDeContas() {

  }

  /**
   * Test of persistirCategoriaDeLancamento method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testPersistirCategoriaDeLancamento() {

  }

  /**
   * Test of persistirCategoriaDeLog method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testPersistirCategoriaDeLog() {

  }

  /**
   * Test of removerCategoria method, of class CategoriaBean.
   */
  @Test
  @Ignore
  public void testRemoverCategoria() {

  }
}
