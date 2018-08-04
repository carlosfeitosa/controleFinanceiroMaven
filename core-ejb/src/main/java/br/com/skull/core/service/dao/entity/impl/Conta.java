package br.com.skull.core.service.dao.entity.impl;

import br.com.skull.core.service.dao.entity.IEntity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Classe que representa a entidade "Conta".
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 */
@Entity
@Table(name = "CONTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conta.findAll", 
            query = "SELECT c FROM Conta c"),
    @NamedQuery(name = "Conta.findById", 
            query = "SELECT c FROM Conta c WHERE c.id = :id"),
    @NamedQuery(name = "Conta.findByNome", 
            query = "SELECT c FROM Conta c WHERE c.nome = :nome"),
    @NamedQuery(name = "Conta.findByCategoria", 
            query = "SELECT c FROM Conta c WHERE c.categoria = :categoria")})
public class Conta implements IEntity, Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 300)
  @Column(name = "NOME")
  private String nome;
  @Size(max = 1000)
  @Column(name = "DESCRICAO")
  private String descricao;
  @Basic(optional = false)
  @NotNull
  @Column(name = "MANUTENCAO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date manutencao;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "conta")
  private Collection<Lancamento> lancamentoCollection;
  @JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  @NotNull
  private Categoria categoria;
  
  /**
   * Prepara entidade para persistência.
   */
  @PrePersist
  public void setupPersist() {
    this.manutencao = Calendar.getInstance().getTime();
  }

  public Conta() {
  }

  public Conta(Long id) {
    this.id = id;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long value) {
    this.id = value;
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

  public Date getManutencao() {
    return manutencao;
  }

  public void setManutencao(Date value) {
    this.manutencao = value;
  }

  @XmlTransient
  public Collection<Lancamento> getLancamentoCollection() {
    return lancamentoCollection;
  }

  public void setLancamentoCollection(Collection<Lancamento> value) {
    this.lancamentoCollection = value;
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
    if (!(object instanceof Conta)) {
      return false;
    }
    Conta other = (Conta) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "br.com.skull.core.model.Conta[ id=" + id + " | nome=" + nome + " ]";
  }
  
}
