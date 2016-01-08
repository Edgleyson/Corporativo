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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="TB_CURSO")
@NamedQueries({
    @NamedQuery(name = esse.chat.modelo.Curso.CURSOS, query = "SELECT c FROM Curso c ORDER BY c.nome"),
    @NamedQuery(name = esse.chat.modelo.Curso.CURSOS_POR_INSTITUICAO, query = "SELECT c FROM Curso c WHERE c.instituicao.sigla = ?1"),
    @NamedQuery(name = esse.chat.modelo.Curso.CURSO_POR_NOME, query = "SELECT c FROM Curso c WHERE c.nome like ?1")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Curso extends Entidade implements Serializable {
    public static final String CURSO_POR_NOME = "CursoPorNome";
    public static final String CURSOS_POR_INSTITUICAO = "CursoPorInstituicao";
    public static final String CURSOS = "Cursos";
    @Expose
    @XmlAttribute(required = true)
    @NotBlank
    @Size ( max = 255)
    @Column (name = "TXT_NOME")
    private String nome;
    @Expose
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="ID_INSTITUICAO", referencedColumnName="ID")
    private Instituicao instituicao;
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="disciplinas")
    @Valid
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Disciplina> disciplinas;

    public Curso() {
        this.disciplinas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    
    @XmlTransient
    public Collection<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    
    public void setDisciplinas(Collection<Disciplina> disciplinas) {
        for (Disciplina disciplina : disciplinas) {
            this.adicionaDisciplina(disciplina);
        }
    }
    
    public void adicionaDisciplina(Disciplina disciplina){
        if (!this.disciplinas.contains(disciplina))
            disciplinas.add(disciplina);
        disciplina.setCurso(this);
    }
    
    public void removeDisciplina(Disciplina disciplina){
        if (disciplinas != null) {
            disciplinas.remove(disciplina);
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
        if (!(object instanceof Email)) {
            return false;
        }
        final Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        } else if (this.id == null && other.id == null) {
            return this.nome.equals(other.nome);
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Curso{" + "id=" + id + ", nome=" + nome + "}";
    }
}