package br.com.skull.core.junit.runner;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import javax.ejb.embeddable.EJBContainer;

/**
 * Runner para rodar testes com container EJB
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class EnterpriseRunner extends BlockJUnit4ClassRunner {

  private static EJBContainer container;

  /**
   * Inicializa container EJB.
   *
   * @param klass classe de teste
   *
   * @throws InitializationError caso não consiga inicializar
   */
  public EnterpriseRunner(Class<?> klass) throws InitializationError {
    super(klass);

    setUp();
  }

  @Override
  protected Statement methodInvoker(FrameworkMethod method, Object test) {
    System.out.println("|--> [*** Running EJB test: " + method.toString() + " *** ]");

    return super.methodInvoker(method, test);
  }

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
  public static void setUp() {
    System.out.println("|--> [*** INICIANDO CONTAINER *** ]");

    if (null == container) {
      container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
    }
  }

  /**
   * Finaliza o container.
   */
  public static void tearDown() {
    System.out.println("|--> [*** ENCERRANDO CONTAINER *** ]");

    container.close();
  }
}
