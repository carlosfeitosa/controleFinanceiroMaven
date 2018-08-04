package br.com.skull.core.junit.listener;

import br.com.skull.core.junit.runner.EnterpriseRunner;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

/**
 * Listener para iniciar e encerrar o container de testes.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class EnterpriseRunnerContainerListener extends RunListener {

  @Override
  public void testRunFinished(Result result) throws Exception {
    super.testRunFinished(result);

    EnterpriseRunner.closeContainer();
  }

}
