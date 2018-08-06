package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.*;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.LogServiceRemote;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de log.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class LogServiceTest {

  private static LogServiceRemote SERVICE;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (LogServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/LogService");
  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class LogService.
   */
  @Test
  public void testPersist() {

  }

  /**
   * Test of remove method, of class LogService.
   */
  @Test
  public void testRemove_GenericType() {

  }

  /**
   * Test of remove method, of class LogService.
   */
  @Test
  public void testRemove_long() {

  }

  /**
   * Test of getById method, of class LogService.
   */
  @Test
  public void testGetById() {

  }

  /**
   * Test of getByMomento method, of class LogService.
   */
  @Test
  public void testGetByMomento() {

  }

  /**
   * Test of getByCategoriaMomento method, of class LogService.
   */
  @Test
  public void testGetByCategoriaMomento() {

  }

  /**
   * Test of getByUsuarioMomento method, of class LogService.
   */
  @Test
  public void testGetByUsuarioMomento() {

  }

  /**
   * Test of getByCategoriaUsuarioMomento method, of class LogService.
   */
  @Test
  public void testGetByCategoriaUsuarioMomento() {

  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {

  }

}
