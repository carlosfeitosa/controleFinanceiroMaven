package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria de contas com
 * tipo errado
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaContasTipoErradoException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria de contas "
          + "com o tipo informado: ";

  public CategoriaContasTipoErradoException(String descricaoTipo) {
    super(MSG_ERROR.concat(descricaoTipo));
  }

}
