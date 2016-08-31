package br.com.equipe.joinsocial.controll.excecoes;

//exceção lançada caso o campo nome completo esteja vazio
public class NmCompletoVazioException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		
		return "Campo obrigatório!";
	}

}
