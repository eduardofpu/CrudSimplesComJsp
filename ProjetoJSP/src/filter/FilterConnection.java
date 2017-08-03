package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import conexao.ConectaBanco;

/**
 * Responsável por intercetar as requisições e iniciar 
 * a conexção com o banco de dados quando o servidor estiver subindo
 * @author Eduardo
 *
 */
@WebFilter(filterName = "conexaoFilter")
public class FilterConnection implements Filter{

	private static Connection connection = null;

	public void destroy() {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			request.setCharacterEncoding("ISO-8859-1");//UTF-8
			connection = ConectaBanco.getConnection(); // obtem a conexção
			chain.doFilter(request, response);// executa a requisição
			connection.commit();// faz commit
			response.setCharacterEncoding("ISO-8859-1");//UTF-8
			response.setContentType("text/html; charset=ISO-8859-1");

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
				throw new RuntimeException(
						"Erro em rollback com a base de dados, commit não foi realizado."
								+ e);
			}
		}
	}

	// Inicia a conexção com o banco de dados quando o servidor inicia
	@SuppressWarnings("static-access")
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		connection = new ConectaBanco().getConnection();
	}
}
