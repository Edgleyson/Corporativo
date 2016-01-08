package esse.chat.jsf.beans;

import esse.chat.modelo.Entidade;
import esse.chat.excecao.ExcecaoNegocio;
import esse.chat.excecao.MensagemExcecao;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolationException;

/**
 * @param <T> Tipo da classe.
 */
public abstract class Bean<T extends Entidade> {

    protected T entidade;
    
    @PostConstruct
    public void init() {
        iniciarCampos();
    }    

    protected abstract void iniciarCampos();

    protected abstract boolean salvar(T entidade) throws ExcecaoNegocio;

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

    public void salvar() {
        try {
            boolean sucesso = salvar(entidade);
            if (sucesso) {
                adicionarMessagem(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso!");
            }
        } catch (ExcecaoNegocio ex) {
            adicionarMessagem(FacesMessage.SEVERITY_WARN, ex.getMessage());
        } catch (EJBException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                MensagemExcecao mensagemExcecao = new MensagemExcecao(ex.getCause());
                adicionarMessagem(FacesMessage.SEVERITY_WARN, mensagemExcecao.getMensagem());
            } else {
                throw ex;
            }
        } finally {
            iniciarCampos();
        }
    }

    protected void adicionarMessagem(FacesMessage.Severity severity, String mensagem) {
        FacesMessage message = new FacesMessage(severity, mensagem, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
