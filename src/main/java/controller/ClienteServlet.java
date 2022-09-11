package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cliente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.cmdline.getopt.GetOpt;

import DAO.ClienteDAO;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ClienteServlet() {
        super();
    }
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpf = request.getParameter("txtCpf");
		String nome = request.getParameter("txtNome");
		String email = request.getParameter("txtEmail");
		String limiteCartao = request.getParameter("txtLimite");
		String dtNasc = request.getParameter("txtDtNasc");
		String button = request.getParameter("btn");
		
		List<Cliente> lista = new ArrayList<Cliente>();
		Cliente cliente = validarCliente(cpf,nome,email,limiteCartao,dtNasc,button);
		String saida = "";
		String error = "";
		
		try {
			ClienteDAO dao = new ClienteDAO();
			switch (button) {
			case "Buscar":
				cliente = dao.read(cliente);
				break;
			case "Inserir":
				saida = dao.create(cliente);
				break;
			case "Atualizar":
				saida = dao.update(cliente);
				break;
			case "Excluir":
				saida = dao.delete(cliente);
				break;
			case "Listar":
				lista = dao.list();
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			if(e.getMessage().contains("PRIMARY KEY")) {
				error = "O CPF digitado já está cadastrado";
			}else {
				error = "Aconteceu um erro no banco de dados";
			}
			System.out.println("ops=" + e.getMessage());					
		}	catch (Exception e) {
			e.printStackTrace();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("saida", saida);
			request.setAttribute("error", error);
			request.setAttribute("lista", lista);
			request.setAttribute("cliente", cliente);
			rd.forward(request, response);			
		}
		
	}

	private Cliente validarCliente(String cpf, String nome, String email,
									String limiteCartao, String dtNasc, String button) {
		Cliente cli = new Cliente();
		
		switch (button) {
		case "Buscar":
			cli.setCpf(tratarCpf(cpf));
			break;
		case "Inserir":
			cli.setCpf(tratarCpf(cpf));
			cli.setNome(nome);
			cli.setEmail(email);
			cli.setLimiteDoCartao(Double.parseDouble(limiteCartao));
			cli.setDataNascimento(LocalDate.parse(dtNasc));
			break;
		case "Atualizar":
			cli.setCpf(tratarCpf(cpf));
			cli.setNome(nome);
			cli.setEmail(email);
			cli.setLimiteDoCartao(Double.parseDouble(limiteCartao));
			cli.setDataNascimento(LocalDate.parse(dtNasc));
			break;
		case "Excluir":
			cli.setCpf(tratarCpf(cpf));
			break;
		default:
			break;
		}
		return cli;
	}
	
	private String tratarCpf(String cpf) {
		String cpfr = cpf.replace(".", "");
		cpfr = cpfr.replace("-", "");
		cpfr = cpfr.replace(" ", "");
		return cpfr;
	}

}
