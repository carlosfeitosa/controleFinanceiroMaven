package br.com.skull.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilitária.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CoreDateUtil {

  public static String DATABASE_DATE_INICIO = "01/01/1980";

  private CoreDateUtil() {
  }

  /**
   * Retorna o início default de data do sistema.
   *
   * @return data default de início
   */
  public static Date firstDate() {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    Date dataFormatada = null;

    try {
      dataFormatada = formato.parse(DATABASE_DATE_INICIO);
    } finally {
      return dataFormatada;
    }
  }

  /**
   * Retorna o momento atual.
   *
   * @return momento atual
   */
  public static Date now() {
    return Calendar.getInstance().getTime();
  }

  /**
   * Retorna o valor default se o valor for nulo.
   *
   * @param valor valor a ser verificado
   * @param valorDefault valor caso nulo
   *
   * @return valor ou valor nulo
   */
  public static Date valorDefaulSeValorNulo(Date valor, Date valorDefault) {
    if (null != valor) {
      return valor;
    }

    return valorDefault;
  }
}
