package br.com.skull.core.business.impl;

import br.com.skull.core.business.CategoriaBusinessBean;
import br.com.skull.core.business.exception.CategoriaContaSemPaiException;
import br.com.skull.core.business.exception.CategoriaLancamentoSemPaiException;
import br.com.skull.core.business.exception.CategoriaLogSemPaiException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import javax.ejb.Stateless;

/**
 * Bean para controle de categorias.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class CategoriaBusinessBeanImpl extends
        AbstractBusinessBeanImpl<CategoriaBusinessBeanImpl> implements CategoriaBusinessBean {

  @EJB
  private CategoriaServiceBean service;

  public CategoriaBusinessBeanImpl() {
    super(CategoriaBusinessBeanImpl.class);
  }

  @Override
  public List<CategoriaDto> listarCategoriasPai() {
    logger.info("Listando todas as categorias pai");
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.CATEGORIA);

    return convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeContas() {
    logger.info("Listando todas as categorias de contas");
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.CONTA);

    return convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeLancamentos() {
    logger.info("Listando todas as categorias de lançamentos");
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.LANCAMENTO);

    return convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeLog() {
    logger.info("Listando todas as categorias de log");
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.LOG);

    return convert(listaEntidade);
  }

  @Override
  public CategoriaDto persistirCategoriaPai(CategoriaDto dto) throws CategoriaPaiNaoVaziaException {
    logger.info("Persistindo categoria pai");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (null != entidade.getCategoria()) {
      logger.info("Uma categoria pai não deve possuir uma categoria pai");
      logger.debug("Categoria {}", entidade.getCategoria());

      throw new CategoriaPaiNaoVaziaException(entidade.getCategoria().getNome());
    }

    entidade.setTipo(TipoCategoriaEnum.CATEGORIA.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeConta(CategoriaDto dto) throws
          CategoriaContaSemPaiException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (null == entidade.getCategoria()) {
      logger.info("A categoria precisa de uma categoria pai");

      throw new CategoriaContaSemPaiException();
    }

    entidade.setTipo(TipoCategoriaEnum.CONTA.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeLancamento(CategoriaDto dto) throws
          CategoriaLancamentoSemPaiException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (null == entidade.getCategoria()) {
      logger.info("A categoria precisa de uma categoria pai");

      throw new CategoriaLancamentoSemPaiException();
    }

    entidade.setTipo(TipoCategoriaEnum.LANCAMENTO.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeLog(CategoriaDto dto) throws CategoriaLogSemPaiException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (null == entidade.getCategoria()) {
      logger.info("A categoria precisa de uma categoria pai");

      throw new CategoriaLogSemPaiException();
    }

    entidade.setTipo(TipoCategoriaEnum.LOG.getCodigo());

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public void removerCategoria(CategoriaDto dto) {
    logger.info("Removendo categoria");
    logger.debug("Categoria: {}", dto);

    Categoria categoria = convert(dto);

    service.remove(categoria);
  }

  /**
   * Converte DTO em Entidade.
   *
   * @param dto dto de categoria
   *
   * @return entidade de categoria
   */
  private Categoria convert(CategoriaDto dto) {
    logger.info("Convertendo objetos");
    logger.debug("DTO: {}", dto);

    Categoria entidade;

    if (dto.getId() > 0) {
      entidade = service.getById(dto.getId());
    } else {
      entidade = new Categoria();
    }

    if (dto.getIdCategoriaPai() > 0) {
      Categoria categoriaPai = service.getById(dto.getIdCategoriaPai());

      entidade.setCategoria(categoriaPai);
    }

    if (null != dto.getTipo()) {
      entidade.setTipo(dto.getTipo().getCodigo());
    }

    entidade.setNome(dto.getNome());
    entidade.setDescricao(dto.getDescricao());

    logger.debug("Entidade: {}", entidade);

    return entidade;
  }

  /**
   * Converte Entidade em DTO.
   *
   * @param entidade entidade categoria
   *
   * @return dto de categoria
   */
  private CategoriaDto convert(Categoria entidade) {
    logger.info("Convertendo objetos");
    logger.debug("Entidade: {}", entidade);

    CategoriaDto dto = new CategoriaDto();

    dto.setId(entidade.getId());
    dto.setIdCategoriaPai(entidade.getCategoria().getId());
    dto.setNome(entidade.getNome());
    dto.setDescricao(entidade.getDescricao());
    dto.setTipo(TipoCategoriaEnum.valueOf(entidade.getTipo()));
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  /**
   * Converte lista de Entidade em lista de DTO.
   *
   * @param listaEntidade lista de entidade (Categoria)
   *
   * @return lista de DTO
   */
  private List<CategoriaDto> convert(List<Categoria> listaEntidade) {
    if (0 == listaEntidade.size()) {
      return null;
    }

    List<CategoriaDto> listaDtos = new ArrayList<>();

    for (Categoria entidade : listaEntidade) {
      listaDtos.add(convert(entidade));
    }

    return listaDtos;
  }

}
