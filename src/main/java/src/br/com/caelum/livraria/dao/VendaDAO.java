package src.br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import src.br.com.caelum.livraria.modelo.Venda;

public class VendaDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager manager;
	
	DAO<Venda> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Venda>(this.manager, Venda.class);
	}

	public void adiciona(Venda t) {
		dao.adiciona(t);
	}

	public void remove(Venda t) {
		dao.remove(t);
	}

	public void atualiza(Venda t) {
		dao.atualiza(t);
	}

	public List<Venda> listaTodos() {
		return dao.listaTodos();
	}

	public Venda buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public List<Venda> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}
	
	public List<Venda> listaVendas(){
		return manager.createQuery("select v from Venda v", Venda.class).getResultList();
	}

}
