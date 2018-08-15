package br.com.skull.core.service.locator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.NamingException;

/**
 * Classe de testes do cache de serviços.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class ServiceCacheTest {

  private static final String NOME_SERVICO_CATEGORIA = "Categoria";
  private static final String NOME_SERVICO_CONTA = "Conta";

  private static CategoriaServiceBean SERVICE_CATEGORIA;
  private static ContaServiceBean SERVICE_CONTA;

  private final ServiceCacheImpl cache;

  public ServiceCacheTest() {
    cache = new ServiceCacheImpl();
  }

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  /**
   * Prepara os serviços para o teste.
   *
   * @throws NamingException caso não encontre o serviço
   */
  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE_CATEGORIA = (CategoriaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaServiceBeanImpl");

    SERVICE_CONTA = (ContaServiceBean) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaServiceBeanImpl");
  }

  @Before
  public void setUp() {
    cache.addService(NOME_SERVICO_CATEGORIA, SERVICE_CATEGORIA);
  }

  /**
   * Test of getService method, of class ServiceCacheImpl.
   */
  @Test
  public void testGetService() {
    CategoriaServiceBean categoriaTestes
            = (CategoriaServiceBean) cache.getService(NOME_SERVICO_CATEGORIA);

    assertTrue("Serviço diferente do esperado", categoriaTestes instanceof CategoriaServiceBean);
  }

  /**
   * Test of addService method, of class ServiceCacheImpl.
   */
  @Test
  @Repeat(times = 3)
  public void testAddService() {
    cache.addService(NOME_SERVICO_CONTA, SERVICE_CONTA);

    assertEquals("Quantidade de serviços diferente do esperado", 2, cache.size());
  }

  /**
   * Test of size method, of class ServiceCacheImpl.
   */
  @Test
  public void testSize() {
    assertTrue("Tamanho do cache não é maior que zero", cache.size() > 0);
  }
}
