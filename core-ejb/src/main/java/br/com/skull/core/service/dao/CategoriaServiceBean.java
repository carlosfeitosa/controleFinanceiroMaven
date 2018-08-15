package br.com.skull.core.service.dao;

import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

import java.util.List;

/**
 * Interface para o serviço de categoria.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public interface CategoriaServiceBean extends AbstractServiceBean<Categoria> {

  /**
   * Lista todas as categorias.
   *
   * @return todas as categorias
   */
  List<Categoria> getTodas();

  /**
   * Lista categoria por id.
   *
   * @param id identificador
   *
   * @return categoria encontrada
   */
  Categoria getById(long id);

  /**
   * Lista categoria por nome.
   *
   * @param nome nome da categoria
   *
   * @return categoria encontrada
   */
  List<Categoria> getByNome(String nome);

  /**
   * Lista categorias por nome aproximado.
   *
   * @param nome nome aproximado da categoria
   *
   * @return lista de categorias parecidas
   */
  List<Categoria> getByNomeAproximado(String nome);

  /**
   * Lista categorias por tipo.
   *
   * @param codigoTipo tipo de categoria
   *
   * @return lista de categorias encontradas
   */
  List<Categoria> getByTipo(long codigoTipo);

  /**
   * Lista categorias por tipo.
   *
   * @param tipo tipo de categoria
   *
   * @return lista de categorias encontradas
   */
  List<Categoria> getByTipo(TipoCategoriaEnum tipo);

  /**
   * Lista as categorias filhas de uma categoria.
   *
   * @param categoriaPai categoria pai
   *
   * @return lista de categorias encontradas
   */
  List<Categoria> getFilhas(Categoria categoriaPai);
}
