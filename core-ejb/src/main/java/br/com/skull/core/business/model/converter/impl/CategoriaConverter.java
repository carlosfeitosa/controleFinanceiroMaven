package br.com.skull.core.business.model.converter.impl;

import br.com.skull.core.business.model.CategoriaDto;
import br.com.skull.core.business.model.converter.Converter;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Conversor de Categoria (DTO <--> Entidade).
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaConverter extends AbstractConverter<CategoriaConverter>
        implements Converter<CategoriaDto, Categoria> {

  public CategoriaConverter() {
    super(CategoriaConverter.class);
  }

  @Override
  public CategoriaDto convert(Categoria entidade) {
    logger.info("Convertendo objetos");
    logger.debug("Entidade: {}", entidade);

    CategoriaDto dto = new CategoriaDto();

    dto.setId(entidade.getId());

    if (null != entidade.getCategoria()) {
      dto.setIdCategoriaPai(entidade.getCategoria().getId());
      dto.setDescricaoCategoriaPai(entidade.getCategoria().getDescricao());
    }

    dto.setNome(entidade.getNome());
    dto.setDescricao(entidade.getDescricao());
    dto.setTipo(TipoCategoriaEnum.valueOf(entidade.getTipo()));
    dto.setManutencao(entidade.getManutencao());

    logger.debug("DTO: {}", dto);

    return dto;
  }

  @Override
  public Categoria convert(CategoriaDto dto) {
    logger.info("Convertendo objetos");
    logger.debug("DTO: {}", dto);

    Categoria entidade = new Categoria();

    entidade.setId(dto.getId());
    entidade.setNome(dto.getNome());
    entidade.setDescricao(dto.getDescricao());
    entidade.setManutencao(dto.getManutencao());

    if (dto.getIdCategoriaPai() > 0) {
      entidade.setCategoria(new Categoria());
      entidade.getCategoria().setId(dto.getIdCategoriaPai());
      entidade.getCategoria().setDescricao(dto.getDescricaoCategoriaPai());
    }

    if (null != dto.getTipo()) {
      entidade.setTipo(dto.getTipo().getCodigo());
    }

    logger.debug("Entidade: {}", entidade);

    return entidade;
  }

  /**
   * Converte lista de Entidade em lista de DTO.
   *
   * @param listaEntidade lista de entidade (Categoria)
   *
   * @return lista de DTO
   */
  public List<CategoriaDto> convert(List<Categoria> listaEntidade) {
    List<CategoriaDto> listaDtos = new ArrayList<>();

    for (Categoria entidade : listaEntidade) {
      listaDtos.add(convert(entidade));
    }

    return listaDtos;
  }

}
