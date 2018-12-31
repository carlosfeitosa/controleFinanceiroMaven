package br.com.skull.core.business;

import br.com.skull.core.business.exception.CategoriaContaSemPaiException;
import br.com.skull.core.business.exception.CategoriaLancamentoSemPaiException;
import br.com.skull.core.business.exception.CategoriaLogSemPaiException;
import br.com.skull.core.business.exception.CategoriaPaiNaoVaziaException;
import br.com.skull.core.business.model.impl.CategoriaDto;

import java.util.List;

/**
 * Interface para bean que controla as categorias.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface CategoriaBusinessBean {

  /**
   * Recupera categoria por id.
   *
   * @param id identificador da categoria
   *
   * @return categoria
   */
  public CategoriaDto listarCategoriaPorId(long id);

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
   */
  public CategoriaDto persistirCategoriaPai(CategoriaDto dto) throws CategoriaPaiNaoVaziaException;

  /**
   * Persistir uma categoria de contas.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   *
   * @throws CategoriaContaSemPaiException caso a categoria não tenha pai
   */
  public CategoriaDto persistirCategoriaDeConta(CategoriaDto dto) throws
          CategoriaContaSemPaiException;

  /**
   * Persistir uma categoria de lançamento.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   *
   * @throws CategoriaLancamentoSemPaiException caso a categoria não tenha pai
   */
  public CategoriaDto persistirCategoriaDeLancamento(CategoriaDto dto) throws
          CategoriaLancamentoSemPaiException;

  /**
   * Persistir uma categoria de log.
   *
   * @param dto categoria para adicionar
   *
   * @return DTO atualizado
   *
   * @throws CategoriaLogSemPaiException caso a categoria não tenha pai
   */
  public CategoriaDto persistirCategoriaDeLog(CategoriaDto dto) throws CategoriaLogSemPaiException;

  /**
   * Remove uma categoria.
   *
   * @param dto categoria para remover
   */
  public void removerCategoria(CategoriaDto dto);
}
