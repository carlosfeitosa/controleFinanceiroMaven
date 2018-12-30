package br.com.skull.core.business.impl;

import br.com.skull.core.business.ContaBusinessBean;
import br.com.skull.core.business.model.ContaDto;
import br.com.skull.core.service.dao.CategoriaServiceBean;
import br.com.skull.core.service.dao.ContaServiceBean;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.entity.impl.Conta;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implementação da interface de negócio para Conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Stateless
public class ContaBusinessBeanImpl extends
        AbstractBusinessBeanImpl<ContaBusinessBeanImpl> implements ContaBusinessBean {

  @EJB
  private ContaServiceBean service;
  @EJB
  private CategoriaServiceBean serviceCategoria;

  public ContaBusinessBeanImpl() {
    super(ContaBusinessBeanImpl.class);
  }

  @Override
  public ContaDto listarContaPorId(long id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<ContaDto> listarContasPorId(List<Long> listaIds) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ContaDto persistirConta(ContaDto dto) {
    logger.info("Persistindo categoria pai");
    logger.debug("Conta: {}", dto);

    Conta entidade = convert(dto);

    if (null == entidade.getCategoria().getId()) {
      entidade.setCategoria(serviceCategoria.persist(entidade.getCategoria()));
    }

    entidade = service.persist(entidade);

    dto.setId(entidade.getId());
    dto.setManutencao(entidade.getManutencao());

    return dto;
  }

  @Override
  public void removerConta(ContaDto dto) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Converte DTO em Entidade.
   *
   * @param dto
   *
   * @return entidade
   */
  private Conta convert(ContaDto dto) {
    logger.info("Convertendo objetos");
    logger.debug("DTO: {}", dto);

    Conta entidade = new Conta();

    if (dto.getId() > 0) {
      entidade = service.getById(dto.getId());
    }

    entidade.setCategoria(new Categoria());

    if (null != dto.getCategoria()) {
      if (dto.getCategoria().getId() > 0) {
        entidade.setCategoria(serviceCategoria.getById(dto.getCategoria().getId()));
      }

      entidade.getCategoria().setNome(dto.getCategoria().getNome());
      entidade.getCategoria().setDescricao(dto.getCategoria().getDescricao());

      if (dto.getCategoria().getIdCategoriaPai() > 0) {
        entidade.getCategoria().setCategoria(serviceCategoria
                .getById(dto.getCategoria().getIdCategoriaPai()));
      }
    }

    entidade.setNome(dto.getNome());
    entidade.setDescricao(dto.getDescricao());

    logger.debug("Entidade: {}", entidade);

    return entidade;
  }

}
