package br.com.skull.core.service.locator.impl;

import br.com.skull.core.service.dao.AbstractServiceRemote;
import br.com.skull.core.service.locator.IServiceLocator;

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
public class ServiceLocator implements IServiceLocator {

  private static ServiceCache cache;
  private static Logger logger;
  private static Context contexto;

  private static IServiceLocator instance;

  /**
   * Inicializa o logger e cache.
   */
  private ServiceLocator() {
    logger = LoggerFactory.getLogger(this.getClass());
    cache = new ServiceCache();

    try {
      contexto = new InitialContext();
    } catch (NamingException ex) {
      logger.error("Erro ao iniciar contexto: ".concat(ex.getExplanation()));
    }

  }

  /**
   * Retorna instância do service locator.
   *
   * @return instância do service locator
   */
  public static IServiceLocator getInstance() {
    if (null == instance) {
      instance = new ServiceLocator();
    }

    return instance;
  }

  /**
   * Sobrescreve o contexto inicial.
   *
   * @param value contexto para sobrescrita
   */
  public static void setContext(Context value) {
    contexto = value;
  }

  @Override
  public AbstractServiceRemote getService(String serviceName) throws NamingException {
    logger.info("Recuperando serviço");
    logger.debug(serviceName);

    AbstractServiceRemote servico = cache.getService(serviceName);

    if (null != servico) {
      return servico;
    }

    servico = (AbstractServiceRemote) contexto.lookup(serviceName);
    cache.addService(serviceName, servico);

    return servico;
  }
}
