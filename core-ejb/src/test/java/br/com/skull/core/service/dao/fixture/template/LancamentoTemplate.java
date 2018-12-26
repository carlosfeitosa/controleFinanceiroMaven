package br.com.skull.core.service.dao.fixture.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.skull.core.service.dao.entity.impl.Lancamento;
import br.com.skull.core.service.dao.enums.TipoLancamentoEnum;

/**
 * Fixture para lançamento (entidade).
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class LancamentoTemplate implements TemplateLoader {

  public static String VALIDO = "VALIDO";
  public static String INVALIDO = "INVALIDO";

  @Override
  public void load() {
    Fixture.of(Lancamento.class)
            .addTemplate(VALIDO, loadLancamentoValido())
            .addTemplate(INVALIDO, loadLancamentoInvalido());
  }

  /**
   * Carrega template de Lançamento válida.
   *
   * @return Rule
   */
  private Rule loadLancamentoValido() {
    return new Rule() {
      {
        add("tipo", random(TipoLancamentoEnum.CREDITO.getCodigo(),
                TipoLancamentoEnum.CREDITO_PROGRAMADO.getCodigo(),
                TipoLancamentoEnum.DEBITO.getCodigo(),
                TipoLancamentoEnum.DEBITO_PROGRAMADO.getCodigo()));
        add("momento", instant("now"));
        add("valor", random(9.99, 99.99, 9999.99, 876.99, 92374434.23, 12345678911.55));
        add("descricao", name());
      }
    };
  }

  /**
   * Carrega template de Lançamento inválido.
   *
   * @return Rule
   */
  private Rule loadLancamentoInvalido() {
    return new Rule() {
      {
        add("tipo", null);
        add("momento", null);
        add("valor", null);
        add("descricao", null);
      }
    };
  }

}
