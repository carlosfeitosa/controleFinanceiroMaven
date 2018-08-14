package br.com.skull.core.business.impl;

import br.com.skull.core.business.CategoriaBeanRemote;
import br.com.skull.core.business.exception.CategoriaContasTipoErradoException;
import br.com.skull.core.business.exception.CategoriaLancamentoTipoErradoException;
import br.com.skull.core.business.exception.CategoriaLogTipoErradoException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.exception.CategoriaPaiTipoErradoException;
import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.service.dao.CategoriaServiceRemote;
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
public class CategoriaBean extends AbstractBean<CategoriaBean> implements CategoriaBeanRemote {

  @EJB
  private CategoriaServiceRemote service;

  public CategoriaBean() {
    super(CategoriaBean.class);
  }

  @Override
  public List<CategoriaDto> listarCategoriasPai() {
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.CATEGORIA);

    return convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeContas() {
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.CONTA);

    return convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeLancamentos() {
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.LANCAMENTO);

    return convert(listaEntidade);
  }

  @Override
  public List<CategoriaDto> listarCategoriasDeLog() {
    List<Categoria> listaEntidade = service.getByTipo(TipoCategoriaEnum.LOG);

    return convert(listaEntidade);
  }

  @Override
  public CategoriaDto persistirCategoriaPai(CategoriaDto dto)
          throws CategoriaPaiNaoVaziaException, CategoriaPaiTipoErradoException {
    logger.info("Persistindo categoria pai");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (null != entidade.getCategoria()) {
      logger.info("Uma categoria pai não deve possuir uma categoria pai");
      logger.debug("Categoria {}", entidade.getCategoria());

      throw new CategoriaPaiNaoVaziaException(entidade.getCategoria().getNome());
    }

    if (entidade.getTipo() != TipoCategoriaEnum.CATEGORIA.getCodigo()) {
      logger.info("Uma categoria pai com tipo errado");
      logger.debug("Tipo: {}", TipoCategoriaEnum.valueOf(entidade.getTipo()));

      throw new CategoriaPaiTipoErradoException(TipoCategoriaEnum.valueOf(
              entidade.getTipo()).getDescricao());
    }

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeContas(CategoriaDto dto) throws
          CategoriaContasTipoErradoException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (entidade.getTipo() != TipoCategoriaEnum.CONTA.getCodigo()) {
      logger.info("Uma categoria pai com tipo errado");
      logger.debug("Tipo: {}", TipoCategoriaEnum.valueOf(entidade.getTipo()));

      throw new CategoriaContasTipoErradoException(TipoCategoriaEnum.valueOf(
              entidade.getTipo()).getDescricao());
    }

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeLancamento(CategoriaDto dto) throws
          CategoriaLancamentoTipoErradoException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (entidade.getTipo() != TipoCategoriaEnum.LANCAMENTO.getCodigo()) {
      logger.info("Uma categoria pai com tipo errado");
      logger.debug("Tipo: {}", TipoCategoriaEnum.valueOf(entidade.getTipo()));

      throw new CategoriaLancamentoTipoErradoException(TipoCategoriaEnum.valueOf(
              entidade.getTipo()).getDescricao());
    }

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public CategoriaDto persistirCategoriaDeLog(CategoriaDto dto) throws
          CategoriaLogTipoErradoException {
    logger.info("Persistindo categoria de conta");
    logger.debug("Categoria: {}", dto);

    Categoria entidade = convert(dto);

    if (entidade.getTipo() != TipoCategoriaEnum.LOG.getCodigo()) {
      logger.info("Uma categoria pai com tipo errado");
      logger.debug("Tipo: {}", TipoCategoriaEnum.valueOf(entidade.getTipo()));

      throw new CategoriaLogTipoErradoException(TipoCategoriaEnum.valueOf(
              entidade.getTipo()).getDescricao());
    }

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

    entidade.setNome(dto.getNome());
    entidade.setDescricao(dto.getDescricao());
    entidade.setTipo(dto.getTipo().getCodigo());

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
