package esse.chat.interceptador;

import esse.chat.modelo.Entidade;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public class ValidadorEntidadeInterceptador {

    private List<Entidade> getEntidades(InvocationContext contexto) {
        List<Entidade> entidades = new ArrayList<Entidade>();
        for (Object objeto : contexto.getParameters()) {
            if (objeto instanceof Entidade) {
                entidades.add((Entidade) objeto);
            }
        }

        return entidades;
    }

    @AroundInvoke
    public Object interceptar(InvocationContext contexto) throws Exception {
        List<Entidade> entidades = getEntidades(contexto);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        for (Entidade entidade : entidades) {
            Set<ConstraintViolation<Entidade>> violations = validator.validate(entidade);

            if (!violations.isEmpty()) {
                throw new RuntimeException(new ConstraintViolationException(violations));
            }
        }

        return contexto.proceed();
    }
}
