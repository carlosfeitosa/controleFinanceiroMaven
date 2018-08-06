package br.com.skull.core.service.dao.impl;

import static org.junit.Assert.*;

import br.com.skull.core.junit.rule.RepeatRule;
import br.com.skull.core.junit.runner.EnterpriseRunner;
import br.com.skull.core.service.dao.UsuarioContaServiceRemote;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.NamingException;

/**
 * Classe de testes para o serviço DAO de relação entre usuário e conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@RunWith(EnterpriseRunner.class)
public class UsuarioContaServiceTest {

  private static UsuarioContaServiceRemote SERVICE;

  @Rule
  public RepeatRule repeatRule = new RepeatRule();

  @BeforeClass
  public static void setUpClass() throws NamingException {
    SERVICE = (UsuarioContaServiceRemote) EnterpriseRunner.getContainer().getContext()
            .lookup("java:global/classes/UsuarioContaService");
  }

  @AfterClass
  public static void tearDownClass() {
    cleanUp();
  }

  /**
   * Test of persist method, of class UsuarioContaService.
   */
  @Test
  public void testPersist() {

  }

  /**
   * Test of remove method, of class UsuarioContaService.
   */
  @Test
  public void testRemove_GenericType() {

  }

  /**
   * Test of remove method, of class UsuarioContaService.
   */
  @Test
  public void testRemove_long() {

  }

  /**
   * Test of getById method, of class UsuarioContaService.
   */
  @Test
  public void testGetById() {

  }

  /**
   * Test of getByConta method, of class UsuarioContaService.
   */
  @Test
  public void testGetByConta() {

  }

  /**
   * Test of getByUsuario method, of class UsuarioContaService.
   */
  @Test
  public void testGetByUsuario() {

  }

  /**
   * Limpa as entidades criadas no teste.
   */
  private static void cleanUp() {

  }

}
