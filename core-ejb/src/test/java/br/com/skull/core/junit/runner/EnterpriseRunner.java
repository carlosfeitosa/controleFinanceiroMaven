package br.com.skull.core.junit.runner;

import br.com.skull.core.junit.listener.EnterpriseRunnerContainerListener;

import org.junit.runner.notification.RunNotifier;
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

  private int testNumber;

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

    testNumber = 0;

    startContainer();
  }

  @Override
  protected Statement methodInvoker(FrameworkMethod method, Object test) {
    testNumber++;

    String testPosition = "(".concat(Integer.toString(testNumber))
            .concat("/").concat(Integer.toString(testCount())).concat("): ");

    System.out.println("|--> [*** Rodando teste EJB: "
            .concat(testPosition).concat(method.toString()).concat(" *** ]"));

    return super.methodInvoker(method, test);
  }

  @Override
  public void run(RunNotifier notifier) {
    notifier.addListener(new EnterpriseRunnerContainerListener());

    super.run(notifier);
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
  private static void startContainer() {
    if (null == container) {
      System.out.println("|--> [*** Iniciando EJB container ***]");

      container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
    }
  }

  /**
   * Finaliza o container.
   */
  public static void closeContainer() {
    if (null != container) {
      System.out.println("|--> [*** Encerrando EJB container ***]");

      container.close();
    }
  }
}
