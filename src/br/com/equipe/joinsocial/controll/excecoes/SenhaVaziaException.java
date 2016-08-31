package br.com.equipe.joinsocial.controll.excecoes;

//exceção lançada caso o campo senha esteja vazio
public class SenhaVaziaException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		
		return "Campo obrigatório!";
	}

}
