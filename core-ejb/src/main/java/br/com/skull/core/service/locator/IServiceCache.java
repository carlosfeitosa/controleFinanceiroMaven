package br.com.skull.core.service.locator;

import br.com.skull.core.service.dao.AbstractServiceRemote;

/**
 * Interface para a construção do cache de serviços.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface IServiceCache {

  /**
   * Recupera o serviço do cache.
   *
   * @param serviceName nome do serviço
   *
   * @return serviço
   */
  public AbstractServiceRemote getService(String serviceName);

  /**
   * Adiciona um serviço no cache.
   *
   * @param serviceName nome do serviço
   * @param servico serviço
   */
  public void addService(String serviceName, AbstractServiceRemote servico);

  /**
   * Retorna a quantidade de serviços no cache.
   *
   * @return quantidade de serviços no cache
   */
  public int size();
}
