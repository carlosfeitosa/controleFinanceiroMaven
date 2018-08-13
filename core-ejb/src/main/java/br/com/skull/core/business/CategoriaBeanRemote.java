package br.com.skull.core.business;

import br.com.skull.core.business.exception.CategoriaContasTipoErradoException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.exception.CategoriaPaiTipoErradoException;
import br.com.skull.core.business.model.CategoriaDto;

import java.util.List;
import javax.ejb.Remote;

/**
 * Interface para o Bean que controla as categorias.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Remote
public interface CategoriaBeanRemote {

  /**
   * Lista todas as categorias pai.
   *
   * @return lista de categorias
   */
  public List<CategoriaDto> listarCategoriasPai();

  /**
   * Lista todas as categorias de contas.
   *
   * @return lista de categorias
   */
  public List<CategoriaDto> listarCategoriasDeContas();

  /**
   * Lista todas as categorias de lançamento.
   *
   * @return lista de categorias
   */
  public List<CategoriaDto> listarCategoriasDeLancamentos();

  /**
   * Lista todas as categorias de log.
   *
   * @return lista de categorias
   */
  public List<CategoriaDto> listarCategoriasDeLog();

  /**
   * Persistir uma categoria pai.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   *
   * @throws CategoriaPaiNaoVaziaException caso a categoria tenha uma categoria pai
   * @throws CategoriaPaiTipoErradoException caso o tipo de categoria esteja errada
   */
  public CategoriaDto persistirCategoriaPai(CategoriaDto dto)
          throws CategoriaPaiNaoVaziaException, CategoriaPaiTipoErradoException;

  /**
   * Persistir uma categoria de contas.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   *
   * @throws CategoriaContasTipoErradoException caso informado o tipo errado
   */
  public CategoriaDto persistirCategoriaDeContas(CategoriaDto dto) throws
          CategoriaContasTipoErradoException;

  /**
   * Persistir uma categoria de lançamento.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   */
  public CategoriaDto persistirCategoriaDeLancamento(CategoriaDto dto);

  /**
   * Persistir uma categoria de log.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   */
  public CategoriaDto persistirCategoriaDeLog(CategoriaDto dto);

  /**
   * Remove uma categoria.
   *
   * @param dto categoria para remover
   */
  public void removerCategoria(CategoriaDto dto);
}
