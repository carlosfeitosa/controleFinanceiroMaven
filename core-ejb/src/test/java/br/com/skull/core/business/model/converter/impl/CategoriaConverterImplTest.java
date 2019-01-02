package br.com.skull.core.business.model.converter.impl;

import static org.junit.Assert.assertEquals;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.skull.core.business.fixture.template.CategoriaDtoTemplate;
import br.com.skull.core.business.model.converter.Converter;
import br.com.skull.core.business.model.impl.CategoriaDto;
import br.com.skull.core.service.dao.entity.impl.Categoria;
import br.com.skull.core.service.dao.fixture.template.CategoriaTemplate;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Case de testes para o conversor de categoria (dto <--> entidade).
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
public class CategoriaConverterImplTest {

  private static Converter<CategoriaDto, Categoria> CONVERTER;

  /**
   * Inicializa a classe de testes.
   */
  @BeforeClass
  public static void setUpClass() {
    CONVERTER = new CategoriaConverterImpl();

    FixtureFactoryLoader.loadTemplates("br.com.skull.core.service.dao.fixture.template");
    FixtureFactoryLoader.loadTemplates("br.com.skull.core.business.fixture.template");
  }

  /**
   * Testa se uma entidade pode ser convertida em dto, sem pai nem id.
   */
  @Test
  public void testConvertFromEntidadeSemPaiSemId() {
    Categoria entidade = Fixture.from(Categoria.class).gimme(
            CategoriaTemplate.VALIDO);

    CategoriaDto dto = CONVERTER.convert(entidade);

    if (null != entidade.getId()) {
      assertEquals("Identificadores não são iguais", entidade.getId().longValue(), dto.getId());
    }

    assertEquals("Nomes não são iguais", entidade.getNome(), dto.getNome());
    assertEquals("Descrições não são iguais", entidade.getDescricao(), dto.getDescricao());
    assertEquals("Tipo não são iguais", entidade.getTipo(), dto.getTipo().getCodigo());
  }

  /**
   * Testa se uma entidade pode ser convertida em dto, sem pai.
   */
  @Test
  public void testConvertFromEntidadeSemPai() {
    Categoria entidade = Fixture.from(Categoria.class).gimme(
            CategoriaTemplate.VALIDO_COM_ID_E_MANUTENCAO);

    CategoriaDto dto = CONVERTER.convert(entidade);

    if (null != entidade.getId()) {
      assertEquals("Identificadores não são iguais", entidade.getId().longValue(), dto.getId());
    }

    assertEquals("Nomes não são iguais", entidade.getNome(), dto.getNome());
    assertEquals("Descrições não são iguais", entidade.getDescricao(), dto.getDescricao());
    assertEquals("Tipo não são iguais", entidade.getTipo(), dto.getTipo().getCodigo());
    assertEquals("Manutenção não são iguais", entidade.getManutencao(), dto.getManutencao());
  }

  /**
   * Testa se uma entidade pode ser convertida em dto.
   */
  @Test
  public void testConvertFromEntidadeComPai() {
    Categoria pai = Fixture.from(Categoria.class).gimme(
            CategoriaTemplate.VALIDO_COM_ID_E_MANUTENCAO);

    Categoria entidade = Fixture.from(Categoria.class).gimme(
            CategoriaTemplate.VALIDO_COM_ID_E_MANUTENCAO);
    entidade.setCategoria(pai);

    CategoriaDto dto = CONVERTER.convert(entidade);

    assertEquals("Identificadores não são iguais", entidade.getId().longValue(), dto.getId());
    assertEquals("Nomes não são iguais", entidade.getNome(), dto.getNome());
    assertEquals("Descrições não são iguais", entidade.getDescricao(), dto.getDescricao());
    assertEquals("Tipo não são iguais", entidade.getTipo(), dto.getTipo().getCodigo());
    assertEquals("Ids da categorias pais não são iguais",
            entidade.getCategoria().getId().longValue(), dto.getIdCategoriaPai());
    assertEquals("Manutenção não são iguais", entidade.getManutencao(), dto.getManutencao());
  }

  /**
   * Testa se um dto pode ser convertido em entidade.
   */
  @Test
  public void testConvertFromDtoSemPaiSemId() {
    CategoriaDto dto = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO);

    Categoria entidade = CONVERTER.convert(dto);

    if (dto.getId() > 0) {
      assertEquals("Identificadores não são iguais", dto.getId(), entidade.getId().longValue());
    }

    assertEquals("Nomes não são iguais", dto.getNome(), entidade.getNome());
    assertEquals("Descrições não são iguais", dto.getDescricao(), entidade.getDescricao());
    assertEquals("Tipo não são iguais", dto.getTipo().getCodigo(), entidade.getTipo());
  }

  /**
   * Testa se um dto pode ser convertido em entidade.
   */
  @Test
  public void testConvertFromDtoSemPai() {
    CategoriaDto dto = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO_COM_ID);

    Categoria entidade = CONVERTER.convert(dto);

    if (dto.getId() > 0) {
      assertEquals("Identificadores não são iguais", dto.getId(), entidade.getId().longValue());
    }

    assertEquals("Nomes não são iguais", dto.getNome(), entidade.getNome());
    assertEquals("Descrições não são iguais", dto.getDescricao(), entidade.getDescricao());
    assertEquals("Tipo não são iguais", dto.getTipo().getCodigo(), entidade.getTipo());
    assertEquals("Manutenção não são iguais", dto.getManutencao(), entidade.getManutencao());
  }

  /**
   * Testa se um dto pode ser convertido em entidade.
   */
  @Test
  public void testConvertFromDtoComPai() {
    CategoriaDto pai = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO_COM_ID);

    CategoriaDto dto = Fixture.from(CategoriaDto.class).gimme(CategoriaDtoTemplate.VALIDO_COM_ID);
    dto.setIdCategoriaPai(pai.getId());

    Categoria entidade = CONVERTER.convert(dto);

    if (dto.getId() > 0) {
      assertEquals("Identificadores não são iguais", dto.getId(), entidade.getId().longValue());
    }

    assertEquals("Nomes não são iguais", dto.getNome(), entidade.getNome());
    assertEquals("Descrições não são iguais", dto.getDescricao(), entidade.getDescricao());
    assertEquals("Tipo não são iguais", dto.getTipo().getCodigo(), entidade.getTipo());
    assertEquals("Ids da categorias pais não são iguais", dto.getIdCategoriaPai(),
            entidade.getCategoria().getId().longValue());
    assertEquals("Manutenção não são iguais", dto.getManutencao(), entidade.getManutencao());
  }

  /**
   * Testa a conversão de uma lista de entidades em uma lista de dtos (sem pai).
   */
  @Test
  public void testConvertListaEntidadesSemPai() {
    int qndItens = 10;

    List<Categoria> listaEntidades = new ArrayList<>();

    for (int x = 0; x < qndItens; x++) {
      listaEntidades.add(Fixture.from(Categoria.class).gimme(
              CategoriaTemplate.VALIDO_COM_ID_E_MANUTENCAO));
    }

    List<CategoriaDto> listaDtos = CONVERTER.convert(listaEntidades);

    assertEquals("Quantidade de itens na lista diferente do esperado", qndItens, listaDtos.size());

    for (int x = 0; x < listaDtos.size(); x++) {
      assertEquals("Identificadores não são iguais", listaEntidades.get(x).getId().longValue(),
              listaDtos.get(x).getId());
      assertEquals("Nomes não são iguais", listaEntidades.get(x).getNome(),
              listaDtos.get(x).getNome());
      assertEquals("Descrições não são iguais", listaEntidades.get(x).getDescricao(),
              listaDtos.get(x).getDescricao());
      assertEquals("Tipo não são iguais", listaEntidades.get(x).getTipo(),
              listaDtos.get(x).getTipo().getCodigo());
      assertEquals("Manutenção não são iguais", listaEntidades.get(x).getManutencao(),
              listaDtos.get(x).getManutencao());
    }

  }

  /**
   * Testa a conversão de uma lista de entidades em uma lista de dtos.
   */
  @Test
  public void testConvertListaEntidadesComPai() {
    int qndItens = 10;

    List<Categoria> listaEntidades = new ArrayList<>();

    for (int x = 0; x < qndItens; x++) {
      Categoria categoria = Fixture.from(Categoria.class).gimme(
              CategoriaTemplate.VALIDO_COM_ID_E_MANUTENCAO);
      categoria.setCategoria(Fixture.from(Categoria.class).gimme(
              CategoriaTemplate.VALIDO_COM_ID_E_MANUTENCAO));

      listaEntidades.add(categoria);
    }

    List<CategoriaDto> listaDtos = CONVERTER.convert(listaEntidades);

    assertEquals("Quantidade de itens na lista diferente do esperado", qndItens, listaDtos.size());

    for (int x = 0; x < listaDtos.size(); x++) {
      assertEquals("Identificadores não são iguais", listaEntidades.get(x).getId().longValue(),
              listaDtos.get(x).getId());
      assertEquals("Nomes não são iguais", listaEntidades.get(x).getNome(),
              listaDtos.get(x).getNome());
      assertEquals("Descrições não são iguais", listaEntidades.get(x).getDescricao(),
              listaDtos.get(x).getDescricao());
      assertEquals("Tipo não são iguais", listaEntidades.get(x).getTipo(),
              listaDtos.get(x).getTipo().getCodigo());
      assertEquals("Ids da categorias pais não são iguais",
              listaEntidades.get(x).getCategoria().getId().longValue(),
              listaDtos.get(x).getIdCategoriaPai());
      assertEquals("Manutenção não são iguais", listaEntidades.get(x).getManutencao(),
              listaDtos.get(x).getManutencao());
    }

  }

}
