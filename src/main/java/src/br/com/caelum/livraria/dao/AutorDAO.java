package src.br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import src.br.com.caelum.livraria.modelo.Autor;

public class AutorDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	DAO<Autor> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Autor>(this.em,Autor.class);
	}

	public void adiciona(Autor t) {
		dao.adiciona(t);
	}

	public void remove(Autor t) {
		dao.remove(t);
	}

	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public List<Autor> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}
	
	

}
