package br.com.skull.core.service.locator;

import br.com.skull.core.service.dao.AbstractServiceRemote;

import javax.naming.NamingException;

/**
 * Interface para implementação do Service Locator, responsável por localizar os serviços EJB.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface IServiceLocator {

  /**
   * Localiza e retorna um serviço.
   *
   * @param serviceName nome do serviço
   *
   * @return serviço
   *
   * @throws NamingException caso não encontre o serviço
   */
  public AbstractServiceRemote getService(String serviceName) throws NamingException;
}
