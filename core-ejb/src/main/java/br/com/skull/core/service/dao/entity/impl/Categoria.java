package br.com.skull.core.service.dao.entity.impl;

import br.com.skull.core.service.dao.entity.Entidade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe que representa a entidade "Categoria"
 *
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Entity
@Table(name = "CATEGORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll",
          query = "SELECT c FROM Categoria c"),
    @NamedQuery(name = "Categoria.findById",
          query = "SELECT c FROM Categoria c WHERE c.id = :id"),
    @NamedQuery(name = "Categoria.findByNome",
          query = "SELECT c FROM Categoria c WHERE c.nome = :nome"),
    @NamedQuery(name = "Categoria.findByNomeAproximado",
          query = "SELECT c FROM Categoria c WHERE c.nome LIKE :nome"),
    @NamedQuery(name = "Categoria.findByTipo",
          query = "SELECT c FROM Categoria c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Categoria.findByCategoriaPai",
          query = "SELECT c FROM Categoria c WHERE c.categoria = :categoriaPai")})
public class Categoria implements Entidade, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 150)
  @Column(name = "NOME")
  private String nome;
  @Size(max = 1000)
  @Column(name = "DESCRICAO")
  private String descricao;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TIPO")
  private long tipo;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MANUTENCAO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date manutencao;
  @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
  @ManyToOne
  private Categoria categoria;

  /**
   * Prepara entidade para persistência.
   */
  @PrePersist
  public void setupPersist() {
    this.manutencao = Calendar.getInstance().getTime();
  }

  public Categoria() {
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String value) {
    this.nome = value;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String value) {
    this.descricao = value;
  }

  public long getTipo() {
    return tipo;
  }

  public void setTipo(long value) {
    this.tipo = value;
  }

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria value) {
    this.categoria = value;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Categoria)) {
      return false;
    }
    Categoria other = (Categoria) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "br.com.skull.core.model.Categoria[ id=" + id + " | nome=" + nome + " ]";
  }
}
