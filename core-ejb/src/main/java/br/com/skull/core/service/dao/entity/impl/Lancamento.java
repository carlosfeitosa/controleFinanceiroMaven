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
 * Classe que representa a entidade "Lançamento".
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Entity
@Table(name = "LANCAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lancamento.findById", 
            query = "SELECT l FROM Lancamento l WHERE l.id = :id"),
    @NamedQuery(name = "Lancamento.findByContaMomento", 
            query = "SELECT l FROM Lancamento l "
            + "WHERE l.conta = :conta "
            + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento"),
    @NamedQuery(name = "Lancamento.findByContaTipoMomento", 
            query = "SELECT l FROM Lancamento l "
            + "WHERE l.tipo = :tipo "
            + "AND l.conta = :conta "
            + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento"),
    @NamedQuery(name = "Lancamento.findByContaCategoriaMomento", 
            query = "SELECT l FROM Lancamento l "
            + "WHERE l.categoria = :categoria "
            + "AND l.conta = :conta "
            + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento"),
    @NamedQuery(name = "Lancamento.findByContaTipoCategoriaMomento", 
            query = "SELECT l FROM Lancamento l "
            + "WHERE l.tipo = :tipo "
            + "AND l.categoria = :categoria "
            + "AND l.conta = :conta "
            + "AND l.momento BETWEEN :inicioMomento AND :terminoMomento")})
public class Lancamento implements Entidade, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Column(name = "TIPO")
  private long tipo;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MOMENTO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date momento;
  @Basic(optional = false)
  @NotNull
  @Column(name = "VALOR")
  private Double valor;
  @Size(max = 1000)
  @Column(name = "DESCRICAO")
  private String descricao;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MANUTENCAO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date manutencao;
  @NotNull
  @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  private Categoria categoria;
  @NotNull
  @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  private Conta conta;
  @NotNull
  @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  private Usuario usuario;
  
  /**
   * Prepara entidade para persistência.
   */
  @PrePersist
  public void setupPersist() {
    this.manutencao = Calendar.getInstance().getTime();
  }

  public Lancamento() {
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long value) {
    this.id = value;
  }

  public long getTipo() {
    return tipo;
  }

  public void setTipo(long value) {
    this.tipo = value;
  }

  public Date getMomento() {
    return momento;
  }

  public void setMomento(Date value) {
    this.momento = value;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double value) {
    this.valor = value;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String value) {
    this.descricao = value;
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

  public Conta getConta() {
    return conta;
  }

  public void setConta(Conta value) {
    this.conta = value;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario value) {
    this.usuario = value;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Lancamento)) {
      return false;
    }
    Lancamento other = (Lancamento) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "br.com.skull.core.model.Lancamento[ id=" + id 
            + " | momento=" + momento.toString() + " | valor=" + valor 
            + " | tipo=" + tipo + " | categoria=" + categoria + " ]";
  }
  
}
