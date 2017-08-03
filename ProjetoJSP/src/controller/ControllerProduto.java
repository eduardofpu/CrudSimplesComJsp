package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DaoProdutos;
import entidade.Produtos;

@WebServlet("/controllerProduto")
public class ControllerProduto extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Dao que realizar as opera��es com o banco de dados
	private DaoProdutos daoProdutos = new DaoProdutos();

	public ControllerProduto() {
		super();
	}

	// Respons�vel por interceptar requisi��es por GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String id = request.getParameter("codigoProduto");

		if (acao.equalsIgnoreCase("editar")) {// se estiver editando o registro

			Produtos produtosEditar = daoProdutos.consulta(Integer.parseInt(id));// consulta
																					// o
																					// registro

			// redireciona adicionando o registro a ser editado
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			request.setAttribute("produto", produtosEditar);
			view.forward(request, response);

		} else if (acao.equalsIgnoreCase("deletar")) {// se for deletar
			try {
				daoProdutos.deleta(id);// delete o registro

				// redireciona e faz a consulta de todos pra mostrar a lista
				// atualizada
				RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("listadeprodutos", daoProdutos.consultaTodos());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Respons�vel por interceptar requisi��es por POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");
			String id = request.getParameter("id");

			if (acao.equalsIgnoreCase("deletar")) {// se for deletar
				daoProdutos.deleta(id);// delete o registro

			} else if (acao.equalsIgnoreCase("salvar")) {// se for salvar e/ou
															// atualizar

				// ---------------------------Pega os parametros da
				// requisi��es----------------------------
				String produto = request.getParameter("produto");

				// --------------Seta os parametros para o objeto
				Produtos produtos = new Produtos();
				produtos.setId(id != null && !id.isEmpty() && id.trim() != null ? Integer.parseInt(id) : null);
				produtos.setProduto(produto);

				// ----------------salva no banco de dados o registro de
				// imagem----------------
				daoProdutos.salvarOuAtualizar(produtos);
			}

			// ----------------consulta todos e requireciona para
			// index.jsp------------
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			request.setAttribute("listadeprodutos", daoProdutos.consultaTodos());
			view.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
