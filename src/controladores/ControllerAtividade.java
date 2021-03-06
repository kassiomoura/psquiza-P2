package controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import modulos.Atividade;
import util.Validador;

/**
 * Controller da atividade.
 * 
 * @author matheusfls-ccc (Matheus Filipe de Lima Souza)
 *
 */
public class ControllerAtividade {

	/**
	 * Mapa que guarda as atividades cadastradas.
	 * 
	 */
	private HashMap<String, Atividade> atividades;

	/**
	 * Validador, que verifica os parâmetros dos métodos para tratamento.
	 * 
	 */
	private Validador validador;

	/**
	 * Código utilizado para identificar as atividades.
	 * 
	 */
	private int codigo;

	/**
	 * Construtor do controller, que sera utilizado pelo controller geral.
	 * 
	 */
	public ControllerAtividade() {
		this.atividades = new HashMap<>();
		this.validador = new Validador();
		this.codigo = 1;
	}
	
	/**
	 * Indica qual será a próxima atividade, da atividade passada como parâmetro. 
	 * 
	 * @param idPrecedente - Atividade antecessora
	 * @param idSubsquente - Atividade sucessora
	 */
	public void defineProximaAtividade(String idPrecedente, String idSubsquente) {
		if (this.existeLoop(idSubsquente)) {
			throw new IllegalArgumentException("Criacao de loops negada.");
		} else if (!this.atividades.containsKey(idPrecedente)) {
			throw new IllegalArgumentException("Atividade nao encontrada.");
		} else if (!this.atividades.containsKey(idSubsquente)) {
			throw new IllegalArgumentException("Atividade nao encontrada.");
		} else { 
			String proximoID = this.atividades.get(idSubsquente).getCodigo();
			this.atividades.get(idPrecedente).setProximaAtividade(proximoID);
		}
	}
	
	/**
	 * Remove a próxima atividade, da atividade passada como parâmetro
	 * 
	 * @param idPrecedente - Atividade a ter sua sucessora removida
	 */
	public void tiraProximaAtividade(String idPrecedente) {
		this.buscaAtividade(idPrecedente).removeProximaAtividade();
	}
	
	private Atividade buscaAtividade(String codigo) {
		if (!this.atividades.containsKey(codigo)) {
			throw new IllegalArgumentException("Atividade nao encontrada.");
		} else {
			return this.atividades.get(codigo);
		}
	}
	
	private boolean existeLoop(String codigo) {
		HashSet<Atividade> listaLoop = new HashSet<>();
		String idAtual = codigo;
		Atividade atual;
		while (true) {
			atual = this.buscaAtividade(idAtual);
			if (atual.getProximaAtividade().equals("")) {
				return false;
			} else if (listaLoop.contains(atual)) {
				return true;
			} else {
				idAtual = atual.getProximaAtividade();
			}
		}
	}
	
	/**
	 * Conta quantas atividades existem depois da atividade passada como parâmetro
	 * 
	 * @param idPrecedente - Atividade a ser contada
	 * @return - Inteiro representando quantas atividades existem após
	 */
	public int contaProximos(String idPrecedente) {
		int contador = 0;
		Atividade atual;
		String idAtual = idPrecedente;
		while (true) {
			atual = this.buscaAtividade(idAtual);
			if (atual.getProximaAtividade().equals("")) {
				return contador;
			} else {
				contador ++;
				idAtual = atual.getProximaAtividade();
			}
		}
	}
	
	/**
	 * Retorna a atividade, contado a partir da posição passada como parâmetro
	 * 
	 * @param idAtividade - Atividade base da contagem
	 * @param enesimaAtividade - Posicao da atividade que se quer
	 * @return - Código da atividade representada pela posicao que se quer
	 */
	public String pegaProximo(String idAtividade, int enesimaAtividade) {
		int codigo = enesimaAtividade;
		Atividade atual = this.buscaAtividade(idAtividade);
		while (codigo > 0) {
			if (atual.getProximaAtividade().equals("")) {
				throw new IllegalArgumentException("Atividade inexistente.");
			} else {
				codigo -= 1;
				atual = this.atividades.get("A" + codigo);
			}
		}
		return atual.getCodigo();
	}
	
	/**
	 * Percorre as atividades subsequentes, procurando pelo maior risco dentre elas.
	 * 
	 * @param idAtividade - Atividade base, é a partir dela que se busca o maior risco
	 * @return - Código da atividade com maior risco
	 */
	public String pegaMaiorRiscoAtividades(String idAtividade) {
		if (this.atividades.get(idAtividade).getProximaAtividade().equals("")) {
			throw new IllegalArgumentException("Nao existe proxima atividade.");
		}
		Atividade atual = this.buscaAtividade(idAtividade);
		Atividade riscoMaior = atual;
		while (true) {
			if (atual.getProximaAtividade() == null) {
				break;
			} else {
				String riscoDoMaior = riscoMaior.getRisco();
				String riscoAtual = atual.getRisco();
				if ("ALTO".equals(riscoAtual)) {
					return atual.getCodigo();
				} else if ("MEDIO".equals(riscoAtual) && "BAIXO".equals(riscoDoMaior)) {
					riscoMaior = atual;
				}
			}
			atual = this.atividades.get(riscoMaior.getProximaAtividade());
		}
		return riscoMaior.getCodigo();
	}

	/**
	 * Metodo que passa os parametros para cadastrar a atividade no sistema.
	 * 
	 * @param descricao O objetivo da atividade.
	 * @param nivelRisco O nivel de risco que a atividade apresenta.
	 * @param descricaoRisco A descrição que explica o nivel de risco apresentado.
	 * @return O código da atividade cadastrada.
	 */
	public String cadastraAtividade(String descricao, String nivelRisco, String descricaoRisco) {
		this.validador.valida(descricao, "Campo Descricao nao pode ser nulo ou vazio.");
		this.validador.valida(nivelRisco, "Campo nivelRisco nao pode ser nulo ou vazio.");
		this.validador.valida(descricaoRisco, "Campo descricaoRisco nao pode ser nulo ou vazio.");
		this.validador.validaNivelRisco(nivelRisco, "Valor invalido do nivel do risco.");
		Atividade atividade = new Atividade(descricao, nivelRisco, descricaoRisco);
		atividade.concatenaCodigo(this.codigo);
		this.atividades.put((atividade.getCodigo()), atividade);
		this.codigo += 1;
		return atividade.getCodigo();
	}

	/**
	 * Método que passa os parâmetros paras apagar uma atividade do sistema.
	 * 
	 * @param codigo O código da atividade a ser apagada.
	 */
	public void apagaAtividade(String codigo) {
		this.validador.valida(codigo, "Campo codigo nao pode ser nulo ou vazio.");
		if (!this.atividades.containsKey(codigo)) {
			throw new IllegalArgumentException("Atividade nao encontrada");
		} else {
			this.atividades.remove(codigo);
		}

	}

	/**
	 * Método que passa os parâmetros para o cadastrao do item à atividade indicada.
	 * 
	 * @param codigo O código da atividade.
	 * @param item   O código do item a ser cadastrado.
	 */
	public void cadastraItem(String codigo, String item) {
		this.validador.valida(codigo, "Campo codigo nao pode ser nulo ou vazio.");
		this.validador.valida(item, "Item nao pode ser nulo ou vazio.");
		if (!this.atividades.containsKey(codigo)) {
			throw new IllegalArgumentException("Atividade nao encontrada");
		} else {
			this.atividades.get(codigo).cadastraItem(item);
		}
	}

	/**
	 * Método que exibe informações sobre a atividade e seus respectivos itens.
	 * 
	 * @param codigo O código da atividade que se deseja.
	 * @return A representação textual da atividade.
	 */
	public String exibeAtividade(String codigo) {
		this.validador.valida(codigo, "Campo codigo nao pode ser nulo ou vazio.");
		if (!this.atividades.containsKey(codigo)) {
			throw new IllegalArgumentException("Atividade nao encontrada");
		} else {
			return this.atividades.get(codigo).toString();
		}
	}

	/**
	 * Método que retorna quantos itens ainda estão pendentes na atividade.
	 * 
	 * @param codigo O código da atividade.
	 * @return A quantia de itens pendentes.
	 */
	public int contaItensPendentes(String codigo) {
		this.validador.valida(codigo, "Campo codigo nao pode ser nulo ou vazio.");
		if (!this.atividades.containsKey(codigo)) {
			throw new IllegalArgumentException("Atividade nao encontrada");
		} else {
			return this.atividades.get(codigo).contaItensPendentes();
		}
	}

	/**
	 * Método que retorna quantos itens já foram realizados na atividade.
	 * 
	 * @param codigo O código da atividade.
	 * @return A quantia de itens realizados.
	 */
	public int contaItensRealizados(String codigo) {
		this.validador.valida(codigo, "Campo codigo nao pode ser nulo ou vazio.");
		if (!this.atividades.containsKey(codigo)) {
			throw new IllegalArgumentException("Atividade nao encontrada");
		} else {
			return this.atividades.get(codigo).contaItensRealizados();
		}
	}


	/**
     * Método retorna um Lista de atividades cadastradas.
     * 
     * @return A lista de atividades cadastradas.
     */
    public List<Atividade> getaAtividades() {
    	List<Atividade> listAtividade = new ArrayList<>();
    	for (String key : this.atividades.keySet()) 
			listAtividade.add(this.atividades.get(key));
		return listAtividade;
	}
	
	/**
	 * Método que retorna a atividade.
	 * 
	 * @param codigoAtividade O código da atividade.
	 * @return A atividade desejada.
	 */
	public Atividade getAtividade(String codigoAtividade) {
		return this.atividades.get(codigoAtividade);
	}

	/**
	 * Método que executa uma atividade já associada a uma pesquisa.
	 * 
	 * @param codigoAtividade O código da atividade.
	 * @param item            O item a ser executado.
	 * @param duracao         A quantidade de horas gastas nessa execução.
	 */
	public void executaAtividade(String codigoAtividade, int item, int duracao) {
		this.validador.valida(codigoAtividade, "Campo codigoAtividade nao pode ser nulo ou vazio.");
		this.validador.validaItem(item, "Item nao pode ser nulo ou negativo.");
		this.validador.validaItem(duracao, "Duracao nao pode ser nula ou negativa.");
		if(this.atividades.get(codigoAtividade).isEhAssociada()) {
			if(item -1 > this.atividades.size()) {
				throw new IllegalArgumentException("Item nao encontrado.");
			}
			else if(this.atividades.get(codigoAtividade).getStatus(item).equals("REALIZADO")) {
				throw new IllegalArgumentException("Item ja executado.");
			}else {
				this.atividades.get(codigoAtividade).executaAtividade(codigoAtividade, item, duracao);
			}
		}else {
			throw new IllegalArgumentException("Atividade sem associacoes com pesquisas.");
		}
		
		
		
	}

	/**
	 * Método que cadastra os resultados obtidos por item da atividade.
	 * 
	 * @param codigoAtividade O identificador da atividade.
	 * @param resultado       O resultado da atividade.
	 * @return O número identificador do resultado.
	 */
	public int cadastraResultado(String codigoAtividade, String resultado) {
		this.validador.valida(codigoAtividade, "Campo codigoAtividade nao pode ser nulo ou vazio.");
		this.validador.valida(resultado, "Resultado nao pode ser nulo ou vazio.");
		return this.atividades.get(codigoAtividade).addResultado(resultado);
	}

	/**
	 * Remove os resultados obtidos por item da atividade.
	 * 
	 * @param codigoAtividade Código da atividade.
	 * @param numeroResultado Número identificador do resultado.
	 * @return "True" se a remoção for bem sucedida e "False" se a remocão não acontecer.
	 */
	public boolean removeResultado(String codigoAtividade, int numeroResultado) {
		this.validador.valida(codigoAtividade, "Campo codigoAtividade nao pode ser nulo ou vazio.");
		this.validador.validaItem(numeroResultado, "numeroResultado nao pode ser nulo ou negativo.");
		if (this.atividades.containsKey(codigoAtividade)) {
			return this.atividades.get(codigoAtividade).removeResultado(numeroResultado);
		} else {
			throw new IllegalArgumentException("Atividade nao encontrada");
		}

	}

	/**
	 * Retorna a quantidade de horas gastas em determinada atividade.
	 * 
	 * @param codigoAtividade O código da atividade.
	 * @return A quantidade de horas gastas em determinada atividade.
	 */
	public int getDuracao(String codigoAtividade) {
		this.validador.valida(codigoAtividade, "Campo codigoAtividade nao pode ser nulo ou vazio.");
		if (this.atividades.containsKey(codigoAtividade)) {
			return this.atividades.get(codigoAtividade).getDuracao();
		} else {
			throw new IllegalArgumentException("Atividade nao encontrada");
		}
	}

	/**
	 * Lista todos os resultados de uma determinada atividade.
	 * 
	 * @param codigoAtividade O código da atividade.
	 * @return Os resultados de uma determinada atividade.
	 */
	public String listaResultados(String codigoAtividade) {
		this.validador.valida(codigoAtividade, "Campo codigoAtividade nao pode ser nulo ou vazio.");
		if(this.atividades.containsKey(codigoAtividade)) {
			return this.atividades.get(codigoAtividade).listaResultados();
		}else {
			throw new IllegalArgumentException("Atividade nao encontrada");
		}
		
	}
	
	/**
     * Método retorna um Lista de atividades cadastradas
     * 
     * @return A lista de atividades cadastradas.
     */
    public List<Atividade> getAtividades() {
    	List<Atividade> listAtividade = new ArrayList<>();
    	for (String key : this.atividades.keySet()) 
			listAtividade.add(this.atividades.get(key));
		return listAtividade;
	}

}