package src.br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import src.br.com.caelum.livraria.dao.VendaDAO;
import src.br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	Venda venda;
	
	@Inject
	VendaDAO vendaDao;
	
	public List<Venda> getVendas(){
		List<Venda> vendas  = this.vendaDao.listaVendas();
		return vendas;
	}
	
	public BarChartModel getVendasModel(){
		
		BarChartModel model = new BarChartModel();
		model.setAnimate(true);
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");
		
		List<Venda> vendas = getVendas();
		for(Venda venda : vendas){
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
	
		model.addSeries(vendaSerie);
		
		return model;
		
	}
}
