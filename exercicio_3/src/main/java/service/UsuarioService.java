package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_LOGIN = 2;
	private final int FORM_ORDERBY_SEXO = 3;
	
	
	public UsuarioService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_LOGIN);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umUser = "";
		if(tipo != FORM_INSERT) {
			umUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo Produto</a></b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";
			umUser += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String name, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuario";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + usuario.getId();
				name = "Atualizar Usuario (ID " + usuario.getId() + ")";
				buttonLabel = "Atualizar";
			}
			umUser += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td>&nbsp;Login: <input class=\"input--register\" type=\"text\" name=\"login\" value=\""+ usuario.getLogin() +"\"></td>";
			umUser += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"text\" name=\"sexo\" value=\""+ usuario.getSexo() +"\"></td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";
			umUser += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (ID " + usuario.getId() + ")</b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td>&nbsp;Login: "+ usuario.getLogin() +"</td>";
			umUser += "\t\t\t<td>Sexo: "+ usuario.getSexo() +"</td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-USUARIO>", umUser);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Usuarios</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_LOGIN + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_SEXO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> usuarios;
		if (orderBy == FORM_ORDERBY_ID) {                 	usuarios = usuarioDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_LOGIN) {		usuarios = usuarioDAO.getOrderByLogin();
		} else if (orderBy == FORM_ORDERBY_SEXO) {			usuarios = usuarioDAO.getOrderBySexo();
		} else {											usuarios = usuarioDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario p : usuarios) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getLogin() + "</td>\n" +
            		  "\t<td>" + p.getSexo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUsuario('" + p.getId() + "', '" + p.getLogin() + "', '" + p.getSexo() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USUARIO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		int codigo = Integer.parseInt(request.queryParams("codigo"));
		String login = request.queryParams("login");
		String senha = request.queryParams("senha");
		char sexo = request.queryParams("sexo").charAt(0);
		String resp = "";
		
		Usuario usuario = new Usuario(codigo, login, senha, sexo);
		
		if(usuarioDAO.insert(usuario) == true) {
            resp = "Usuário (" + login + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuário (" + login + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_LOGIN);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Usuario usuario = (Usuario) usuarioDAO.get(id);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_LOGIN);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
        	usuario.setLogin(request.queryParams("login"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuario.setSexo(request.queryParams("sexo").charAt(0));
        	
        	usuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuario (ID " + id + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (ID " + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":codigo"));
        Usuario usuario = usuarioDAO.get(id);
        String resp = "";       

        if (usuario != null) {
            usuarioDAO.delete(id);
            response.status(200); // success
            resp = "Usuario (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}