package br.com.skull.core.service.locator.impl;

import br.com.skull.core.service.dao.AbstractServiceBean;
import br.com.skull.core.service.locator.ServiceLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Classe para recuperar serviços.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class ServiceLocatorImpl implements ServiceLocator {

  private static ServiceCacheImpl cache;
  private static Logger logger;
  private static Context contexto;

  private static ServiceLocator instance;

  /**
   * Inicializa o logger e cache.
   */
  private ServiceLocatorImpl() throws NamingException {
    logger = LoggerFactory.getLogger(this.getClass());
    cache = new ServiceCacheImpl();

    contexto = new InitialContext();
  }

  /**
   * Retorna instância do service locator.
   *
   * @return instância do service locator
   *
   * @throws javax.naming.NamingException caso não consiga recuperar o contexto inicial
   */
  public static ServiceLocator getInstance() throws NamingException {
    if (null == instance) {
      instance = new ServiceLocatorImpl();
    }

    return instance;
  }

  @Override
  public AbstractServiceBean getService(String serviceName) throws NamingException {
    logger.info("Recuperando serviço");
    logger.debug(serviceName);

    AbstractServiceBean servico = cache.getService(serviceName);

    if (null != servico) {
      return servico;
    }

    servico = (AbstractServiceBean) contexto.lookup(serviceName);
    cache.addService(serviceName, servico);

    return servico;
  }
}
