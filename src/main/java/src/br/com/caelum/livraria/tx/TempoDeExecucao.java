package src.br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Log
public class TempoDeExecucao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@AroundInvoke
    public Object log(InvocationContext contexto) throws Exception {

        long inicio = System.currentTimeMillis();

        String metodo = contexto.getMethod().getName();

        Object proceder = contexto.proceed();

        long fim = System.currentTimeMillis();

        long resultado = fim - inicio;

        System.out.println("M?todo executado: " + metodo + " Tempo execu??o: " + resultado);

        return proceder;    
    }    

}
