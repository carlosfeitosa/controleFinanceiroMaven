package br.com.skull.core.service.locator.impl;

import br.com.skull.core.service.dao.AbstractServiceRemote;
import br.com.skull.core.service.locator.IServiceCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação da interface de cache de serviços.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class ServiceCache implements IServiceCache {

  private final Map<String, AbstractServiceRemote> services;

  public ServiceCache() {
    services = new HashMap<>();
  }

  @Override
  public AbstractServiceRemote getService(String serviceName) {
    return services.get(serviceName);
  }

  @Override
  public void addService(String serviceName, AbstractServiceRemote servico) {
    if (null == services.get(serviceName)) {
      services.put(serviceName, servico);
    }
  }

  @Override
  public int size() {
    return services.size();
  }
}
