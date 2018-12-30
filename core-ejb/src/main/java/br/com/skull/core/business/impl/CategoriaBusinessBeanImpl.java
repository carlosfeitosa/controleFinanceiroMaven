package br.com.skull.core.business.impl;

import br.com.skull.core.business.CategoriaBusinessBean;
import br.com.skull.core.business.exception.CategoriaContaSemPaiException;
import br.com.skull.core.business.exception.CategoriaLancamentoSemPaiException;
import br.com.skull.core.business.exception.CategoriaLogSemPaiException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.business.model.converter.impl.CategoriaConverter;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.List;
import javax.ejb.EJB;

import javax.ejb.Stateless;

/**
 * Implementação da interface de negócio para Categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class CategoriaBusinessBeanImpl extends
        AbstractBusinessBeanImpl<CategoriaBusinessBeanImpl> implements CategoriaBusinessBean {

  @EJB
  private CategoriaServiceBean service;

  private final CategoriaConverter converter = new CategoriaConverter();

  public CategoriaBusinessBeanImpl() {
    super(CategoriaBusinessBeanImpl.class);
  }

  @Override
  public CategoriaDto listarCategoriaPorId(long id) {
    logger.info("Listando categoria por id");
    logger.debug("ID: {}", id);

    Categoria categoria = service.getById(id);

    return converter.convert(categoria);
  }

  @Override
  public List<CategoriaDto> listarCategoriasPai() {
    logger.info("Listando todas as categorias pai");

    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.CATEGORIA);

    return converter.convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeContas() {
    logger.info("Listando todas as categorias de contas");

    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.CONTA);

    return converter.convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeLancamentos() {
    logger.info("Listando todas as categorias de lançamentos");

    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.LANCAMENTO);

    return converter.convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeLog() {
    logger.info("Listando todas as categorias de log");

    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.LOG);

    return converter.convert(listaEntidade);
  }

  @Override
  public CategoriaDto persistirCategoriaPai(CategoriaDto dto) throws CategoriaPaiNaoVaziaException {
    logger.info("Persistindo categoria pai");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = converter.convert(dto);

    if (null != entidade.getCategoria()) {
      logger.info("Uma categoria pai não deve possuir uma categoria pai");
      logger.debug("Categoria {}", entidade.getCategoria());

      throw new CategoriaPaiNaoVaziaException(service.getById(
              entidade.getCategoria().getId()).getNome());
    }

    entidade.setTipo(TipoCategoriaEnum.CATEGORIA.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setTipo(TipoCategoriaEnum.valueOf(entidade.getTipo()));
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeConta(CategoriaDto dto) throws
          CategoriaContaSemPaiException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = converter.convert(dto);

    if (null == entidade.getCategoria()) {
      logger.info("A categoria precisa de uma categoria pai");

      throw new CategoriaContaSemPaiException();
    }

    entidade.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setDescricaoCategoriaPai(service.getById(entidade.getCategoria().getId()).getDescricao());
    dto.setTipo(TipoCategoriaEnum.valueOf(entidade.getTipo()));
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeLancamento(CategoriaDto dto) throws
          CategoriaLancamentoSemPaiException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = converter.convert(dto);

    if (null == entidade.getCategoria()) {
      logger.info("A categoria precisa de uma categoria pai");

      throw new CategoriaLancamentoSemPaiException();
    }

    entidade.setTipo(TipoCategoriaEnum.LANCAMENTO.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setDescricaoCategoriaPai(service.getById(entidade.getCategoria().getId()).getDescricao());
    dto.setTipo(TipoCategoriaEnum.valueOf(entidade.getTipo()));
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeLog(CategoriaDto dto) throws CategoriaLogSemPaiException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = converter.convert(dto);

    if (null == entidade.getCategoria()) {
      logger.info("A categoria precisa de uma categoria pai");

      throw new CategoriaLogSemPaiException();
    }

    entidade.setTipo(TipoCategoriaEnum.LOG.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setDescricaoCategoriaPai(service.getById(entidade.getCategoria().getId()).getDescricao());
    dto.setTipo(TipoCategoriaEnum.valueOf(entidade.getTipo()));
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public void removerCategoria(CategoriaDto dto) {
    logger.info("Removendo categoria");
    logger.debug("Categoria: {}", dto);

    Categoria categoria = converter.convert(dto);

    service.remove(categoria);
  }

}
