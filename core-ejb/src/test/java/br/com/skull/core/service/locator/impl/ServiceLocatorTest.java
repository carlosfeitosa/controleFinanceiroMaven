package br.com.skull.core.service.locator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.locator.IServiceLocator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.NamingException;

/**
 * Classe de testes do ServiceLocator
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class ServiceLocatorTest {

  private static final String NOME_SERVICO_CATEGORIA = "java:global/classes/CategoriaService";
  private static final String NOME_ERRADO_SERVICO_CATEGORIA = "java:global/classes/CatoriaSere";

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Test of getInstance method, of class ServiceLocator.
   *
   * @throws javax.naming.NamingException caso não consiga recuperar a instância do service locator
   */
  @Test
  public void testGetInstance() throws NamingException {
    IServiceLocator locator1 = ServiceLocator.getInstance();
    IServiceLocator locator2 = ServiceLocator.getInstance();

    assertEquals("Instâncias não são iguais", locator1, locator2);
  }

  /**
   * Test of getService method, of class ServiceLocator.
   */
  @Test
  @Repeat(times = 3)
  public void testGetService() {
    try {
      CategoriaServiceRemote servico
              = (CategoriaServiceRemote) ServiceLocator.getInstance()
                      .getService(NOME_SERVICO_CATEGORIA);

      assertTrue("Serviço diferente do esperado", servico instanceof CategoriaServiceRemote);
    } catch (NamingException ex) {
      fail("Serviço não encontrado: ".concat(NOME_SERVICO_CATEGORIA));
    }
  }

  /**
   * Test of getService method, of class ServiceLocator.
   *
   * @throws javax.naming.NamingException por não conseguir localizar o serviço
   */
  @Test(expected = NamingException.class)
  public void testGetServiceFail() throws NamingException {
    ServiceLocator.getInstance().getService(NOME_ERRADO_SERVICO_CATEGORIA);
  }
}
