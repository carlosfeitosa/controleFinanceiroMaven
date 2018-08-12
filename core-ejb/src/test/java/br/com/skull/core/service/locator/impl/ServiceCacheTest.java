package br.com.skull.core.service.locator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.rule.RepeatRule.Repeat;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
import br.com.skull.core.service.dao.ContaServiceRemote;

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

  private static CategoriaServiceRemote SERVICE_CATEGORIA;
  private static ContaServiceRemote SERVICE_CONTA;

  private final ServiceCache cache;

  public ServiceCacheTest() {
    cache = new ServiceCache();
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
    SERVICE_CATEGORIA = (CategoriaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/CategoriaService");

    SERVICE_CONTA = (ContaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/ContaService");
  }

  @Before
  public void setUp() {
    cache.addService(NOME_SERVICO_CATEGORIA, SERVICE_CATEGORIA);
  }

  /**
   * Test of getService method, of class ServiceCache.
   */
  @Test
  public void testGetService() {
    CategoriaServiceRemote categoriaTestes
            = (CategoriaServiceRemote) cache.getService(NOME_SERVICO_CATEGORIA);

    assertTrue("Serviço diferente do esperado", categoriaTestes instanceof CategoriaServiceRemote);
  }

  /**
   * Test of addService method, of class ServiceCache.
   */
  @Test
  @Repeat(times = 3)
  public void testAddService() {
    cache.addService(NOME_SERVICO_CONTA, SERVICE_CONTA);

    assertEquals("Quantidade de serviços diferente do esperado", 2, cache.size());
  }

  /**
   * Test of size method, of class ServiceCache.
   */
  @Test
  public void testSize() {
    assertTrue("Tamanho do cache não é maior que zero", cache.size() > 0);
  }
}
