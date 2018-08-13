package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria de lançamento 
 * com tipo errado
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaLancamentoTipoErradoException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria de lançamento "
          + "com o tipo informado: ";

  public CategoriaLancamentoTipoErradoException(String descricaoTipo) {
    super(MSG_ERROR.concat(descricaoTipo));
  }

}
