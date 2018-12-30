package br.com.skull.core.business.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.skull.core.business.model.ContaDto;

/**
 * Template de fixture para conta.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class ContaDtoTemplate implements TemplateLoader {

  public static String VALIDA = "VALIDO_SEM_PAI";
  public static String INVALIDA = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(ContaDto.class)
            .addTemplate(VALIDA, loadContaDtoValida())
            .addTemplate(INVALIDA, loadContaDtoInvalida());
  }

  /**
   * Carrega modelo válido de Conta dto.
   *
   * @return conta dto
   */
  private Rule loadContaDtoValida() {
    return new Rule() {
      {
        add("nome", name());
        add("descricao", name());
      }
    };
  }

  /**
   * Carrega um modelo inválido de Conta dto.
   *
   * @return conta dto
   */
  private Rule loadContaDtoInvalida() {
    return new Rule() {
      {
        add("nome", null);
        add("descricao", null);
      }
    };
  }

}
