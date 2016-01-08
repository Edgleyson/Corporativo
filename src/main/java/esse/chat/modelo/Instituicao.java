package esse.chat.modelo;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name="TB_INSTITUICAO")
@NamedQueries(
        {
            @NamedQuery(
                    name = Instituicao.INSTITUICOES,
                    query = "SELECT i FROM Instituicao i ORDER BY i.nome"
            ),
            @NamedQuery(
                    name = Instituicao.INSTITUICAO_POR_SIGLA,
                    query = "SELECT i FROM Instituicao i WHERE i.sigla = ?1"
            )            
        }
)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Instituicao extends Entidade implements Serializable {
    public static final String INSTITUICAO_POR_SIGLA = "EditoraPorNome";
    public static final String INSTITUICOES = "Editoras";   
    @Expose
    @XmlAttribute(required = true)
    @NotBlank                                                   
    @Size (max = 255)
    @Column(name="TXT_NOME")
    private String nome;
    @Expose
    @XmlAttribute(required = true)
    @NotNull
    @Size (max = 10)
    @Column(name="TXT_SIGLA", unique = true)
    private String sigla;
    @Expose
    @XmlAttribute(required = true)
    @NotNull
    @CNPJ
    @Column(name="TXT_CNPJ")
    private String cnpj;
    @Expose
    @Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID")
    private Endereco endereco;
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="fones")
    @Valid
    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="ID_INSTITUICAO", referencedColumnName="ID")
    private Collection<Fone> fones = new ArrayList<>();
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="emails")
    @Valid
    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="ID_INSTITUICAO", referencedColumnName="ID")
    private Collection<Email> emails = new ArrayList<>();
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="cursos")
    @Valid
    @OneToMany(mappedBy="instituicao", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    private Collection<Curso> cursos = new ArrayList<>();

    public Instituicao() {
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public Endereco criarEndereco() {
        this.setEndereco(new Endereco());
        return getEndereco();
    }

    @XmlTransient
    public Collection<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Collection<Curso> cursos) {
        for (Curso curso : cursos) {
            this.adicionaCurso(curso);
            curso.setInstituicao(this);
        }
    }
    
    public void adicionaCurso(Curso curso){
        if (!this.cursos.contains(curso))
            cursos.add(curso);            
        curso.setInstituicao(this);
    }
    
    public void removeCurso(Curso curso){
        if (cursos != null) {
            cursos.remove(curso);
        }
    } 
    
    @XmlTransient
    public Collection<Email> getEmails() {
        return emails;
    }

    public void setEmails(Collection<Email> emails) {
        for (Email email : emails) {
            this.adicionaEmail(email);
        }
    }
    
    public void adicionaEmail(Email email){
        if (!this.emails.contains(email)) {
            emails.add(email);
        }
    }
    
    public void removeEmail(Email email){
        if (emails != null) {
            emails.remove(email);
        }
    } 
    
    @XmlTransient
    public Collection<Fone> getFones() {
        return fones;
    }
    
    public void setFones(Collection<Fone> fones) {
        for (Fone fone : fones) {
            this.adicionaFone(fone);
        }
    }
    
    public void adicionaFone(Fone fone){
        if (!this.fones.contains(fone))
            fones.add(fone);
    }
    
    public void removeFone(Fone fone){
        if (fones != null) {
            fones.remove(fone);
        }
    } 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Instituicao)) {
            return false;
        }
        Instituicao other = (Instituicao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }else if (this.id == null && other.id == null) {
            return this.cnpj.equals(other.cnpj);
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Instituicao{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", cnpj=" + cnpj + '}';
    }

        
}
