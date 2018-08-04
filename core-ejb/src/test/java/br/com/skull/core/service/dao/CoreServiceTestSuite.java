package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.impl.CategoriaServiceTest;
import br.com.skull.core.service.dao.impl.ContaServiceTest;
import br.com.skull.core.service.dao.impl.LancamentoServiceTest;
import br.com.skull.core.service.dao.impl.UsuarioServiceTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.ejb.embeddable.EJBContainer;

/**
 * Classe suite testes para os serviços DAO core.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CategoriaServiceTest.class, ContaServiceTest.class, 
    LancamentoServiceTest.class, UsuarioServiceTest.class})
public class CoreServiceTestSuite {

  private static EJBContainer container;

  /**
   * Retorna o container do test suite.
   *
   * @return container EJB
   */
  public static EJBContainer getContainer() {
    return container;
  }

  /**
   * Inicializa o container.
   */
  @BeforeClass
  public static void setUp() {
    System.out.println("|--> [*** INICIANDO CONTAINER *** ]");

    container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
  }

  /**
   * Finaliza o container.
   */
  @AfterClass
  public static void tearDown() {
    System.out.println("|--> [*** ENCERRANDO CONTAINER *** ]");

    container.close();
  }
}
