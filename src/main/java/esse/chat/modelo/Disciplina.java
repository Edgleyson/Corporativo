package esse.chat.modelo;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.*;
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
@Table(name="TB_DISCIPLINA")
@NamedQueries({
    @NamedQuery(name = esse.chat.modelo.Disciplina.DISCIPLINAS, query = "SELECT d FROM Disciplina d ORDER BY d.nome"),
    @NamedQuery(name = esse.chat.modelo.Disciplina.DISCIPLINA_POR_CURSO, query = "SELECT d FROM Disciplina d WHERE d.curso.nome like ?1"),
    @NamedQuery(name = esse.chat.modelo.Disciplina.DISCIPLINA_POR_NOME, query = "SELECT d FROM Disciplina d WHERE d.nome like ?1")
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Disciplina extends Entidade implements Serializable {
    public static final String DISCIPLINA_POR_NOME = "DisciplinaPorNome";
    public static final String DISCIPLINA_POR_CURSO = "DisciplinaPorCurso";
    public static final String DISCIPLINAS = "Disciplinas";
    @Expose
    @XmlAttribute(required = true)
    @NotBlank
    @Size (max = 255)
    @Column (name = "TXT_NOME")
    private String nome;
    @Expose
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="ID_CURSO", referencedColumnName="ID")
    private Curso curso;
    @Expose
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="ID_PROFESSOR", referencedColumnName="ID")
    private Usuario professor;  
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="alunos")
    @Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="TB_DISCIPLINA_ALUNO", joinColumns={
        @JoinColumn(name="ID_DISCIPLINA")},
            inverseJoinColumns={
                @JoinColumn(name="ID_ALUNO")})    
    private Collection<Usuario> alunos; 
    @Expose
    @Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_SALA_CHAT", referencedColumnName = "ID")
    private SalaDeChat chat;
    @Expose    
    @XmlElement(required = true)  
    @XmlElementWrapper(name="monitores")
    @Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="TB_DISCIPLINA_MONITOR", joinColumns={
        @JoinColumn(name="ID_DISCIPLINA")},
            inverseJoinColumns={
                @JoinColumn(name="ID_ALUNO")})
    private Collection<Usuario> monitores;

    public Disciplina() {
        this.alunos = new ArrayList<>();
        this.monitores = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public SalaDeChat getChat(){
        return chat;
    }
    
    public void setChat(SalaDeChat chat){
        this.chat = chat;
        this.chat.setDisciplina(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }
    
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    @XmlTransient
    public Collection<Usuario> getAlunos() {
        return alunos;
    }
    
    public void setAlunos(Collection<Usuario> alunos) {
        for (Usuario aluno : alunos) {
            this.adicionaAluno(aluno);
        }
    }
    
    public void adicionaAluno(Usuario aluno) {
        if (!this.alunos.contains(aluno)) {
            alunos.add(aluno);
            aluno.adicionaDisciplina(this);
        }
    }
    
    public void removeAluno(Usuario aluno) {
        if (alunos != null) {
            {
                alunos.remove(aluno);
                aluno.removeDisciplina(this);
            }
        }
    }
    
    @XmlTransient
    public Collection<Usuario> getMonitores() {
        return monitores;
    }
    
    public void setMonitores(Collection<Usuario> monitores) {
        for (Usuario aluno : monitores) {
            this.adicionaMonitor(aluno);
        }
    }
    
    public void adicionaMonitor(Usuario aluno){
        if (!this.monitores.contains(aluno))
            monitores.add(aluno);
    }
    
    public void removeMonitor(Usuario aluno){
        if (monitores != null) {
            monitores.remove(aluno);
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
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        } else if (this.id == null && other.id == null) {
            return this.nome.equals(other.nome);
        }
        
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Disciplina{" + "id=" + id + ", nome=" + nome +  '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}