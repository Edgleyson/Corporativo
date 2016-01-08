package esse.chat.modelo;

import com.google.gson.annotations.Expose;
import esse.chat.acesso.Grupo;
import esse.chat.controle.ValidaApelido;
import esse.chat.util.Encriptador;
import esse.chat.xml.AdaptadorData;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name="TB_USUARIO")
@NamedQueries({
    @NamedQuery(name = esse.chat.modelo.Usuario.USUARIOS, query = "SELECT u FROM Usuario u ORDER BY u.primeiroNome"),
    @NamedQuery(name = esse.chat.modelo.Usuario.USUARIO_POR_LOGIN, query = "SELECT u FROM Usuario u WHERE u.apelido = ?1"),
    @NamedQuery(name = esse.chat.modelo.Usuario.USUARIO_POR_CPF, query = "SELECT u FROM Usuario u WHERE u.cpf = ?1")
})
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "DISC_USUARIO",
//        discriminatorType = DiscriminatorType.STRING, length = 2)
//@DiscriminatorValue(value="US")
@Access(AccessType.FIELD)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario extends Entidade implements Serializable {
    public static final String USUARIO_POR_LOGIN = "UsuarioPorLogin";
    public static final String USUARIO_POR_CPF = "UsuarioPorCPF";
    public static final String USUARIOS = "Usuarios";
    @Expose    
    @XmlAttribute(required = true)
    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "{esse.chat.modelo.Usuario.primeiroNome}")
    @Column(name="TXT_NOME")
    private String primeiroNome;
    @Expose    
    @XmlAttribute(required = true)
    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "{esse.chat.modelo.Usuario.ultimoNome}")
    @Column(name="TXT_SOBRENOME")
    private String ultimoNome;
    @Expose    
    @XmlAttribute(required = true)
    @NotBlank
    @ValidaApelido (message = "{esse.chat.modelo.Usuario.apelido}")
    @Size (min=3, max = 10)
    @Column(name="TXT_APELIDO", unique = true)
    private String apelido;
    @Expose    
    @XmlAttribute(required = true)
    @NotBlank 
    @Size (max = 1)
    @Pattern(regexp = "M|F", message = "{esse.chat.modelo.Usuario.sexo}")
    @Column(name="TXT_SEXO")
    private String sexo;    
    @NotBlank 
//    @Size (min = 4, max = 10)
//    @Pattern(regexp = "((?=.*\\p{Digit})(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*\\p{Punct}).{4,10})", 
//            message = "{esse.chat.modelo.Usuario.senha}")
    @Column(name="TXT_SENHA")
    private String senha;
    @Expose    
    @XmlAttribute(required = true)
    @Column(name="TXT_SAL")
    private String sal;
    @Expose    
    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(AdaptadorData.class)
    @NotNull
    @Past
    @Column(name="DT_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date nascimento;
    @Expose    
    @XmlAttribute(required = true)
    @NotNull
    @CPF
    @Column(name="TXT_CPF")
    private String cpf;
    @XmlTransient
    @Transient
    private int idade;
    @Expose
    @XmlAttribute(required = true)
    @NotNull
    @org.hibernate.validator.constraints.Email
    @Column(name="TXT_EMAIL", length = 150)
    private String email;
    @Expose
    @Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_ENDERECO", referencedColumnName = "ID")
    private Endereco endereco;
    @Expose    
    @XmlElement  
    @XmlElementWrapper(name="emails")
    @Valid
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="ID_USUARIO", referencedColumnName="ID")
    private Collection<Email> emails = new ArrayList<>();
    @Expose    
    @XmlElement  
    @XmlElementWrapper(name="fones")
    @Valid
    @OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="ID_USUARIO", referencedColumnName="ID")
    private Collection<Fone> fones = new ArrayList<>();
    @XmlTransient
    @Valid
    @ManyToMany(mappedBy = "alunos")
    private Collection<Disciplina> disciplinas = new ArrayList<>();
    @XmlTransient
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_USUARIO_GRUPO", joinColumns = {
        @JoinColumn(name = "ID_USUARIO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_GRUPO")})
    private List<Grupo> grupos;


    @PrePersist
    public void gerarHash() {
        try {
            Encriptador enc = new Encriptador();
            setSal(enc.salgarSenha());
            setSenha(enc.encriptarSenha(sal+senha));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String nome) {

        this.primeiroNome = nome;
    }
    
    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String nome) {
        this.ultimoNome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
    
    public String getSal(){
        return sal;
    }
    
    public void setSal(String sal){
        this.sal = sal;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade(Date nascimento) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(nascimento);
        Calendar hoje = Calendar.getInstance();

        this.idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);


        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
            idade--;
        } else {
            if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                idade--;
            }
        }
        return idade;
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

    public Collection<Email> getEmails() {
        return emails;
    }

    public void setEmails(Collection<Email> emails) {
        for (Email umemail : emails) {
            this.adicionaEmail(umemail);
        }
    }
    
    public void adicionaEmail(Email email){
        if (!this.emails.contains(email))
            emails.add(email);
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void removeEmail(Email email){
        if (emails != null) {
            emails.remove(email);
        }
    }
    
    public String getEmail(){
        return email;
    }

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
    
    public void adicionarGrupo(Grupo grupo) {
        if (this.grupos == null) {
            this.grupos = new ArrayList<>();
        }
        
        this.grupos.add(grupo);
    }
    
    public List<Grupo> getGrupos() {
        return this.grupos;
    }
    
    public Collection<Disciplina> getDisciplinas() {
        return disciplinas;
    }
    
    public void setDisciplinas(Collection<Disciplina> disciplinas){
        for (Disciplina disciplina : disciplinas) {
            this.adicionaDisciplina(disciplina);
        }
    }
       
    public void adicionaDisciplina(Disciplina disciplina){
        if (!this.disciplinas.contains(disciplina)){
            disciplinas.add(disciplina);
        } 
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
         if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();  
        for (Object obj : fones) {  
           sb.append(obj.toString()).append(" ");  
        }
        StringBuilder sb2 = new StringBuilder();
        for (Object obj : emails) {  
           sb2.append(obj.toString()).append(" ");  
        } 
        return "Usuario: " + "id=" + id + ", nome=" + primeiroNome + " " + ultimoNome + ", apelido=" + apelido +
                ", sexo=" + sexo + ", senha=" + senha + ", nascimento=" + nascimento + 
                ", cpf=" + cpf + endereco.toString() + ", fone=" + sb.toString() +
                ", email=" + sb2.toString();
}
    
}
