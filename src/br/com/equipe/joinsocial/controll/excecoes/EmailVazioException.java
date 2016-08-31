package br.com.equipe.joinsocial.controll.excecoes;

//exceção lançada caso o campo e-mail esteja vazio
public class EmailVazioException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		
		return "Campo obrigatório!";
	}

}
