package br.com.skull.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Classe de testes para o utilitário de data.
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CoreDateUtilTest {

  /**
   * Test of firstDate method, of class CoreDateUtil.
   *
   * @throws java.lang.Exception caso haja problemas na conversão de data
   */
  @Test
  public void testFirstDate() throws Exception {
    Date result = CoreDateUtil.firstDate();
    Calendar cal = Calendar.getInstance();
    cal.setTime(result);

    assertEquals("Dia é diferente do esperado", 1, cal.get(Calendar.DAY_OF_MONTH));
    assertEquals("Mês é diferente do esperado", 0, cal.get(Calendar.MONTH));
    assertEquals("Ano é diferente do esperado", 1980, cal.get(Calendar.YEAR));
  }

  /**
   * Test of now method, of class CoreDateUtil.
   */
  @Test
  public void testNow() {
    Date expResult = Calendar.getInstance().getTime();
    Date result = CoreDateUtil.now();
    assertEquals("Momento atual diferente do esperado", expResult.toString(), result.toString());
  }

  /**
   * Test of valorDefaulSeValorNulo method, of class CoreDateUtil.
   */
  @Test
  public void testValorDefaulSeValorNuloPassingNull() {
    Date valor = null;
    Date valorDefault = Calendar.getInstance().getTime();
    Date expResult = Calendar.getInstance().getTime();
    Date result = CoreDateUtil.valorDefaulSeValorNulo(valor, valorDefault);
    assertEquals("Valor default deferente do esperado", expResult.toString(), result.toString());
  }

  /**
   * Test of valorDefaulSeValorNulo method, of class CoreDateUtil.
   */
  @Test
  public void testValorDefaulSeValorNulo() {
    Date valor = Calendar.getInstance().getTime();
    Date valorDefault = null;
    Date expResult = valor;
    Date result = CoreDateUtil.valorDefaulSeValorNulo(valor, valorDefault);
    assertEquals("Valor default deferente do esperado", expResult.toString(), result.toString());
  }
}
