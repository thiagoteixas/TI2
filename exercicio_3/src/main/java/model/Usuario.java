package model;

public class Usuario {
	private int id;
	private String login;
	private String senha;
	private char sexo;
	
	public Usuario() {
		id = -1;
		login = "";
		senha = "";
		sexo = '\'';
	}

	public Usuario(int id, String Login, String senha, char sexo) {
		setId(id);
		setLogin(Login);
		setSenha(senha);
		setSexo(sexo);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Id: " + id + "   Login: " + login + "   Sexo: " + sexo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}	
}