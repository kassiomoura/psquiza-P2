package controladores;

/**
 * Controloador responsavel por gerir o sistema.
 *
 */
public class ControllerGeral {

	/**
	 * Instância do controlador do fornecedor
	 *
	 */
	private ControllerProblemaObjetivo controllerProblemaObjetivo;

	/**
	 * Instância do controlador do fornecedor
	 *
	 */
	private ControllerPesquisador controllerPesquisador;

	/**
	 * Construtor que inicializa os controladores instâciados.
	 */
	public ControllerGeral() {
		this.controllerProblemaObjetivo = new ControllerProblemaObjetivo();
		this.controllerPesquisador = new ControllerPesquisador();
	}

	/**
	 * US2
	 */

	/**
	 * Método que passa ao controlador dos pesquisadores os parâmetros a serem
	 * cadastrados.
	 * 
	 * @param nome      o nome do pesquisador.
	 * @param funcao    a função do pesquisador.
	 * @param biografia a biografia do pesquisador.
	 * @param email     o email do pesquisador.
	 * @param foto      a foto do pesquisador.
	 */
	public void cadastraPesquisador(String nome, String funcao, String biografia, String email, String foto) {
		this.controllerPesquisador.cadastraPesquisador(nome, funcao, biografia, email, foto);
	}

	/**
	 * Método que passa ao controlador dos pesquisadores os parâmetros a serem
	 * alterados.
	 * 
	 * @param email     o email identificador do pesquisador.
	 * @param atributo  o atributo a ser alterado.
	 * @param novoValor o novo valor do atributo.
	 */
	public void alteraPesquisador(String email, String atributo, String novoValor) {
		this.controllerPesquisador.alteraPesquisador(email, atributo, novoValor);
	}

	/**
	 * Método que passa ao controlador dos pesquisadores o parâmetro para a
	 * desativação.
	 * 
	 * @param email o email identificador do pesquisador.
	 */
	public void desativaPesquisador(String email) {
		this.controllerPesquisador.desativaPesquisador(email);
	}

	/**
	 * Método que passa ao controlador dos pesquisadores o parâmetro para a
	 * ativação.
	 * 
	 * @param email o email identificador do pesquisador.
	 */
	public void ativaPesquisador(String email) {
		this.controllerPesquisador.ativaPesquisador(email);
	}

	/**
	 * Método que passa ao controlador dos pesquisadores o parâmetro para a
	 * exibição.
	 * 
	 * @param email o email identificador do pesquisador.
	 * @return a representação do pesquisador.
	 */
	public String exibePesquisador(String email) {
		return this.controllerPesquisador.exibePesquisador(email);
	}

	/**
	 * Método que passa ao controlador dos pesquisadores o parâmetro para a
	 * verificação do estado.
	 * 
	 * @param email o email identificador do pesquisador.
	 * @return o estado do pesquisador no sistema.
	 */
	public boolean pesquisadorEhAtivo(String email) {
		return this.controllerPesquisador.pesquisadorEhAtivo(email);
	}

	/**
	 * US3
	 */

	/**
	 * Método que passa ao controlador dos problemas e objetivos os parâmetros a
	 * serem cadastrados.
	 * 
	 * @param descricao   Descrição do problema.
	 * @param viabilidade Viabilidade do problema ser resolvido.
	 */
	public void cadastraProblema(String descricao, int viabilidade) {
		this.controllerProblemaObjetivo.cadastraProblema(descricao, viabilidade);
	}

	/**
	 * Método que passa ao controlador dos problemas e objetivos os parâmetros a
	 * serem cadastrados.
	 * 
	 * @param tipo        Tipo do objetivo que pode ser GERAL ou ESPECIFICO.
	 * @param descricao   Descricao do objetivo.
	 * @param aderencia   Problema Aderencia ao problema.
	 * @param viabilidade Viabilidade de o objetivo ser concretizado.
	 */
	public void cadastraObjetivo(String tipo, String descricao, int aderenciaProblema, int viabilidade) {
		this.controllerProblemaObjetivo.cadastraObjetivo(tipo, descricao, aderenciaProblema, viabilidade);
	}

	/**
	 * Método que passa ao controlador dos problemas e objetivos o parâmetro para a
	 * remoção.
	 * 
	 * @param codigo Codigo do problema.
	 */
	public void apagarProblema(String codigo) {
		this.controllerProblemaObjetivo.apagarProblema(codigo);
	}

	/**
	 * Método que passa ao controlador dos problemas e objetivos o parâmetro para a
	 * remoção.
	 * 
	 * @param codigo Codigo do objetivo.
	 */
	public void apagarObjetivo(String codigo) {
		this.controllerProblemaObjetivo.apagarObjetivo(codigo);
	}

	/**
	 * Método que passa ao controlador dos problemas e objetivos o parâmetro para a
	 * exibição.
	 * 
	 * @param codigo Codigo do problema.
	 * @return a represetação do problema.
	 */
	public String exibeProblema(String codigo) {
		return this.controllerProblemaObjetivo.exibeProblema(codigo);
	}

	/**
	 * Método que passa ao controlador dos problemas e objetivos o parâmetro para a
	 * exibição.
	 * 
	 * @param codigo Codigo do objetivo.
	 * @return a represetação do objetivo.
	 */
	public String exibeObjetivo(String codigo) {
		return this.controllerProblemaObjetivo.exibeObjetivo(codigo);
	}

}
