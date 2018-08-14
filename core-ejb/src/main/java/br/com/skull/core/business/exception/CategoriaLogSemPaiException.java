package br.com.skull.core.business.exception;

/**
 * Exceção para o bean de categoria, lançada na tentativa de persistir uma categoria de log sem pai
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaLogSemPaiException extends Exception {

  private static final String MSG_ERROR = "Não é possível persistir uma categoria de log "
          + "sem pai";

  public CategoriaLogSemPaiException() {
    super(MSG_ERROR);
  }

}
