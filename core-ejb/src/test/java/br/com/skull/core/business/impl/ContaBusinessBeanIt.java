package br.com.skull.core.business.impl;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.skull.core.business.CategoriaBusinessBean;
import br.com.skull.core.business.ContaBusinessBean;
import br.com.skull.core.business.fixture.template.CategoriaDtoTemplate;
import br.com.skull.core.business.fixture.template.ContaDtoTemplate;
import br.com.skull.core.business.model.impl.CategoriaDto;
import br.com.skull.core.business.model.impl.ContaDto;

import br.com.skull.core.junit.runner.EnterpriseRunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBTransactionRolledbackException;
import javax.naming.NamingException;

/**
 * Classe de testes para a classe de negócio de conta (ContaBusinessBean).
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class ContaBusinessBeanIt {

  private static ContaBusinessBean BEAN;
  private static CategoriaBusinessBean BEAN_CATEGORIA;

  private static final List<ContaDto> LISTA_DTO = new ArrayList<>();

  private ContaDto contaTestes;

  /**
   * Inicializa o bean BO de conta.
   *
   * @throws NamingException caso não encontre os beans
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    try {
      BEAN = (ContaBusinessBean) EnterpriseRunner.getContainer().getContext()
              .lookup("java:global/core-ejb-1.0-SNAPSHOT/ContaBusinessBeanImpl");

      BEAN_CATEGORIA = (CategoriaBusinessBean) EnterpriseRunner.getContainer().getContext()
              .lookup("java:global/core-ejb-1.0-SNAPSHOT/CategoriaBusinessBeanImpl");
    } catch (NamingException ex) {
      System.out.println("Recuperando serviço no pacote de classes: ".concat(ex.getMessage()));
      BEAN = (ContaBusinessBean) EnterpriseRunner.getContainer().getContext()
              .lookup("java:global/classes/ContaBusinessBeanImpl");

      BEAN_CATEGORIA = (CategoriaBusinessBean) EnterpriseRunner.getContainer().getContext()
              .lookup("java:global/classes/CategoriaBusinessBeanImpl");
    }

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.business.fixture.template");
  }

  /**
   * Inicializa entidade de testes.
   */
  @Before
  public void setUp() {
    contaTestes = new ContaDto();
  }

  /**
   * Armazena entidade para cleanUp.
   */
  @After
  public void tearDown() {
    if (((null != contaTestes) && (contaTestes.getId() > 0))
            || ((null != contaTestes.getCategoria()) && (contaTestes.getCategoria().getId() > 0))) {
      LISTA_DTO.add(contaTestes);
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
    for (ContaDto conta : LISTA_DTO) {
      BEAN.removerConta(conta);

      BEAN_CATEGORIA.removerCategoria(conta.getCategoria());
    }

    LISTA_DTO.clear();
  }

  /**
   * Testa se estoura uma exceção na tentativa de salvar uma conta inválida.
   *
   * @throws Exception exceção esperada
   */
  @Test(expected = EJBTransactionRolledbackException.class)
  public void testExcecaoTentarPersistirContaInvalida() throws Exception {
    contaTestes = Fixture.from(ContaDto.class).gimme(ContaDtoTemplate.INVALIDA);

    BEAN.persistirConta(contaTestes);
  }

  /**
   * Testa se estoura uma exceção na tentativa de salvar uma conta inválida com uma categoria
   * válida.
   *
   * @throws Exception exceção esperada
   */
  @Test(expected = EJBTransactionRolledbackException.class)
  public void testExcecaoTentarPersistirContaInvalidaCategoriaValida() throws Exception {
    contaTestes = Fixture.from(ContaDto.class).gimme(ContaDtoTemplate.INVALIDA);
    contaTestes.setCategoria(Fixture.from(CategoriaDto.class)
            .gimme(CategoriaDtoTemplate.VALIDO));

    BEAN.persistirConta(contaTestes);
  }

}
