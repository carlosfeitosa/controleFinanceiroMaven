package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.LogServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Log;
import br.com.skull.core.service.dao.entity.impl.Usuario;
import br.com.skull.core.util.CoreDateUtil;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Bean para controle de persistência de log.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class LogServiceBeanImpl extends
        AbstractServiceBeanImpl<Log, LogServiceBeanImpl> implements LogServiceBean {

  private static final String QUERY_POR_ID = "Log.findById";
  private static final String QUERY_POR_MOMENTO = "Log.findByMomento";
  private static final String QUERY_POR_CATEGORIA_MOMENTO = "Log.findByCategoriaMomento";
  private static final String QUERY_POR_USUARIO_MOMENTO = "Log.findByUsuarioMomento";
  private static final String QUERY_POR_CATEGORIA_USUARIO_MOMENTO
          = "Log.findByCategoriaUsuarioMomento";

  private static final String ATTR_ID = "id";
  private static final String ATTR_CATEGORIA = "categoria";
  private static final String ATTR_USUARIO = "usuario";
  private static final String ATTR_INICIO = "inicioMomento";
  private static final String ATTR_TERMINO = "terminoMomento";

  public LogServiceBeanImpl() {
    super(Log.class, LogServiceBeanImpl.class);
  }

  @Override
  public Log getById(long id) {
    logger.info("Recuperando log por id");
    logger.debug("ID: {}", id);

    return em.createNamedQuery(QUERY_POR_ID, Log.class)
            .setParameter(ATTR_ID, id).getSingleResult();
  }

  @Override
  public List<Log> getByMomento(Date inicio, Date termino) {
    logger.info("Recuperando log por momento");
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_MOMENTO, Log.class)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Log> getByCategoria(Categoria categoria, Date inicio, Date termino) {
    logger.info("Recuperando log por categoria e momento");
    logger.debug("Categoria: {}", categoria);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CATEGORIA_MOMENTO, Log.class)
            .setParameter(ATTR_CATEGORIA, categoria)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Log> getByUsuario(Usuario usuario, Date inicio, Date termino) {
    logger.info("Recuperando log por usuário e momento");
    logger.debug("Usuário: {}", usuario);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_USUARIO_MOMENTO, Log.class)
            .setParameter(ATTR_USUARIO, usuario)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Log> getByCategoriaUsuario(Categoria categoria, Usuario usuario,
          Date inicio, Date termino) {
    logger.info("Recuperando log por categoria, usuário e momento");
    logger.debug("Categoria: {}", categoria);
    logger.debug("Usuário: {}", usuario);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CATEGORIA_USUARIO_MOMENTO, Log.class)
            .setParameter(ATTR_CATEGORIA, categoria)
            .setParameter(ATTR_USUARIO, usuario)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

}
