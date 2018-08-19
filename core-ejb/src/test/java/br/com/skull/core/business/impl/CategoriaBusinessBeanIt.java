package br.com.skull.core.business.impl;

import br.com.skull.core.business.CategoriaBusinessBean;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.model.CategoriaDto;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.naming.NamingException;

/**
 * Classe de testes para a o bean de categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(Arquillian.class)
public class CategoriaBusinessBeanIt {

  private static final String NOME_CATEGORIA_TESTES = "__IGNORE-CategoriaTestes";
  private static final String DESCRICAO_CATEGORIA_TESTES = "Descrição categoria de testes"
          + " - não utilizar esta categoria";

  @EJB
  private CategoriaBusinessBean bean;

  /**
   * Realiza o deploy do container de testes.
   *
   * @return deploy
   */
  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
            .addPackages(true, "br.com.skull.core")
            .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
  }

  @BeforeClass
  public static void setUpClass() throws NamingException {

  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() throws NamingException {
    //  bean = (CategoriaBusinessBean) EnterpriseRunner.getContainer().getContext()
    //          .lookup("java:global/core-ejb-1.0-SNAPSHOT/CategoriaBusinessBeanImpl");
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

    bean.persistirCategoriaPai(categoria);
  }

  /**
   * Test of persistirCategoriaPai method, of class CategoriaBean.
   *
   * @throws CategoriaPaiNaoVaziaException caso haja indicado uma categoria pai
   */
  @Test(expected = CategoriaPaiNaoVaziaException.class)
  public void testPersistirCategoriaPaiCategoriaPaiNaoVazia() throws CategoriaPaiNaoVaziaException {
    CategoriaDto categoriaPai = new CategoriaDto();

    categoriaPai.setNome(NOME_CATEGORIA_TESTES);
    categoriaPai.setDescricao(DESCRICAO_CATEGORIA_TESTES);

    categoriaPai = bean.persistirCategoriaPai(categoriaPai);

    CategoriaDto categoria = new CategoriaDto();

    categoria.setNome(NOME_CATEGORIA_TESTES);
    categoria.setDescricao(DESCRICAO_CATEGORIA_TESTES);
    categoria.setIdCategoriaPai(categoriaPai.getId());

    bean.persistirCategoriaPai(categoria);
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
