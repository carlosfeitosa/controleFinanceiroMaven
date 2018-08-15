package br.com.skull.core.service.dao.impl;

import br.com.skull.core.service.dao.LancamentoServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;
import br.com.skull.core.util.CoreDateUtil;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Bean para controle de persistência de lançamento.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class LancamentoServiceBeanImpl extends
        AbstractServiceBeanImpl<Lancamento, LancamentoServiceBeanImpl> implements
        LancamentoServiceBean {

  private static final String QUERY_POR_ID = "Lancamento.findById";
  private static final String QUERY_POR_CONTA = "Lancamento.findByContaMomento";
  private static final String QUERY_POR_CONTA_TIPO = "Lancamento.findByContaTipoMomento";
  private static final String QUERY_POR_CONTA_CATEGORIA = "Lancamento.findByContaCategoriaMomento";
  private static final String QUERY_POR_CONTA_TIPO_CATEGORIA
          = "Lancamento.findByContaTipoCategoriaMomento";

  private static final String ATTR_ID = "id";
  private static final String ATTR_CONTA = "conta";
  private static final String ATTR_TIPO = "tipo";
  private static final String ATTR_CATEGORIA = "categoria";
  private static final String ATTR_INICIO = "inicioMomento";
  private static final String ATTR_TERMINO = "terminoMomento";

  public LancamentoServiceBeanImpl() {
    super(Lancamento.class, LancamentoServiceBeanImpl.class);
  }

  @Override
  public Lancamento getById(long id) {
    logger.info("Recuperando lançamento por id");
    logger.debug("ID: {}", id);

    return em.createNamedQuery(QUERY_POR_ID, Lancamento.class)
            .setParameter(ATTR_ID, id).getSingleResult();
  }

  @Override
  public List<Lancamento> getByConta(Conta conta, Date inicio, Date termino) {
    logger.info("Recuperando lançamento por conta");
    logger.debug("Conta: {}", conta);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CONTA, Lancamento.class)
            .setParameter(ATTR_CONTA, conta)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Lancamento> getByContaTipo(Conta conta, TipoLancamentoEnum tipo,
          Date inicio, Date termino) {
    logger.info("Recuperando lançamento por conta e tipo");
    logger.debug("Conta: {}", conta);
    logger.debug("Tipo: {}", tipo);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CONTA_TIPO, Lancamento.class)
            .setParameter(ATTR_CONTA, conta)
            .setParameter(ATTR_TIPO, tipo.getCodigo())
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Lancamento> getByContaTipo(Conta conta, long tipo,
          Date inicio, Date termino) {
    logger.info("Recuperando lançamento por conta e tipo");
    logger.debug("Conta: {}", conta);
    logger.debug("Tipo: {}", tipo);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CONTA_TIPO, Lancamento.class)
            .setParameter(ATTR_CONTA, conta)
            .setParameter(ATTR_TIPO, tipo)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Lancamento> getByContaCategoria(Conta conta, Categoria categoria,
          Date inicio, Date termino) {
    logger.info("Recuperando lançamento por conta e categoria");
    logger.debug("Conta: {}", conta);
    logger.debug("Categoria: {}", categoria);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CONTA_CATEGORIA, Lancamento.class)
            .setParameter(ATTR_CONTA, conta)
            .setParameter(ATTR_CATEGORIA, categoria)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Lancamento> getByContaTipoCategoria(Conta conta, TipoLancamentoEnum tipo,
          Categoria categoria, Date inicio, Date termino) {
    logger.info("Recuperando lançamento por conta, tipo e categoria");
    logger.debug("Conta: {}", conta);
    logger.debug("Tipo: {}", tipo);
    logger.debug("Categoria: {}", categoria);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CONTA_TIPO_CATEGORIA, Lancamento.class)
            .setParameter(ATTR_CONTA, conta)
            .setParameter(ATTR_TIPO, tipo.getCodigo())
            .setParameter(ATTR_CATEGORIA, categoria)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

  @Override
  public List<Lancamento> getByContaTipoCategoria(Conta conta, long codigoTipo,
          Categoria categoria, Date inicio, Date termino) {
    logger.info("Recuperando lançamento por conta, tipo e categoria");
    logger.debug("Conta: {}", conta);
    logger.debug("Tipo: {}", codigoTipo);
    logger.debug("Categoria: {}", categoria);
    logger.debug("Início: {}", inicio);
    logger.debug("Término: {}", termino);

    inicio = CoreDateUtil.valorDefaulSeValorNulo(inicio, CoreDateUtil.firstDate());
    termino = CoreDateUtil.valorDefaulSeValorNulo(termino, CoreDateUtil.now());
    logger.debug("Início após validação de default: {}", inicio);
    logger.debug("Término após validação de default: {}", termino);

    return em.createNamedQuery(QUERY_POR_CONTA_TIPO_CATEGORIA, Lancamento.class)
            .setParameter(ATTR_CONTA, conta)
            .setParameter(ATTR_TIPO, codigoTipo)
            .setParameter(ATTR_CATEGORIA, categoria)
            .setParameter(ATTR_INICIO, inicio)
            .setParameter(ATTR_TERMINO, termino).getResultList();
  }

}
