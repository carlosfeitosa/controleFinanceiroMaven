package br.com.skull.core.service.locator;

import br.com.skull.core.service.dao.AbstractServiceBean;

/**
 * Interface para a construção do cache de serviços.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface ServiceCache {

  /**
   * Recupera o serviço do cache.
   *
   * @param serviceName nome do serviço
   *
   * @return serviço
   */
  public AbstractServiceBean getService(String serviceName);

  /**
   * Adiciona um serviço no cache.
   *
   * @param serviceName nome do serviço
   * @param servico serviço
   */
  public void addService(String serviceName, AbstractServiceBean servico);

  /**
   * Retorna a quantidade de serviços no cache.
   *
   * @return quantidade de serviços no cache
   */
  public int size();
}
