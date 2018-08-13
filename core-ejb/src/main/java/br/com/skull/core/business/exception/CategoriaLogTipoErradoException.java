package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria de log com tipo
 * errado
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaLogTipoErradoException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria de log "
          + "com o tipo informado: ";

  public CategoriaLogTipoErradoException(String descricaoTipo) {
    super(MSG_ERROR.concat(descricaoTipo));
  }

}
