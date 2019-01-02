package br.com.skull.core.business.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import br.com.skull.core.business.model.impl.CategoriaDto;
import br.com.skull.core.service.dao.enums.TipoCategoriaEnum;

/**
 * Fixture para CagegoriaDto.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaDtoTemplate implements TemplateLoader {

  public static String VALIDO = "VALIDO";
  public static String VALIDO_COM_ID = "VALIDO_COM_ID";
  public static String INVALIDO = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(CategoriaDto.class)
            .addTemplate(VALIDO, loadCategoriaDtoValidaSemPai())
            .addTemplate(VALIDO_COM_ID, loadCategoriaDtoValidaSemPaiComIdComManutencao())
            .addTemplate(INVALIDO, loadCategoriaDtoInvalida());
  }

  /**
   * Carrega modelo válido de Categoria dto.
   *
   * @return categoria dto
   */
  private Rule loadCategoriaDtoValidaSemPai() {
    return new Rule() {
      {
        add("nome", name());
        add("descricao", name());
        add("tipo", random(TipoCategoriaEnum.CATEGORIA, TipoCategoriaEnum.CONTA,
                TipoCategoriaEnum.LANCAMENTO, TipoCategoriaEnum.LOG));
      }
    };
  }

  /**
   * Carrega modelo válido de Categoria dto.
   *
   * @return categoria dto
   */
  private Rule loadCategoriaDtoValidaSemPaiComIdComManutencao() {
    return new Rule() {
      {
        add("id", random(9L, 99L, 999L, 9999L, 99999L));
        add("nome", name());
        add("descricao", name());
        add("tipo", random(TipoCategoriaEnum.CATEGORIA, TipoCategoriaEnum.CONTA,
                TipoCategoriaEnum.LANCAMENTO, TipoCategoriaEnum.LOG));
      }
    };
  }

  /**
   * Carrega um modelo inválido de Categoria dto.
   *
   * @return categoria dto
   */
  private Rule loadCategoriaDtoInvalida() {
    return new Rule() {
      {
        add("nome", null);
        add("descricao", null);
      }
    };
  }

}
