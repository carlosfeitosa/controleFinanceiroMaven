package br.com.skull.core.business.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import br.com.skull.core.business.model.CategoriaDto;

/**
 * Fixture para CagegoriaDto.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaDtoTemplate implements TemplateLoader {

  public static String VALIDO_SEM_PAI = "VALIDO_SEM_PAI";
  public static String INVALIDO = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(CategoriaDto.class)
            .addTemplate(VALIDO_SEM_PAI, loadCategoriaDtoValidaSemPai())
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
