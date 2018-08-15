package br.com.skull.core.service.locator.impl;

import br.com.skull.core.service.dao.AbstractServiceBean;
import br.com.skull.core.service.locator.ServiceCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação da interface de cache de serviços.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class ServiceCacheImpl implements ServiceCache {

  private final Map<String, AbstractServiceBean> services;

  public ServiceCacheImpl() {
    services = new HashMap<>();
  }

  @Override
  public AbstractServiceBean getService(String serviceName) {
    return services.get(serviceName);
  }

  @Override
  public void addService(String serviceName, AbstractServiceBean servico) {
    if (null == services.get(serviceName)) {
      services.put(serviceName, servico);
    }
  }

  @Override
  public int size() {
    return services.size();
  }
}
