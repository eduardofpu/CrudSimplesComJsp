package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexao.ConectaBanco;
import entidade.Produtos;

/**
 * Classe Responsável pelas operações no banco de dados
 * 
 * @author Eduardo
 * 
 */
public class DaoProdutos {
	private Connection connection;
	
	
	public DaoProdutos(){
		connection = ConectaBanco.getConnection();
	}
	
	/**
	 * Constroi a instruçãoo para o banco de dados
	 * @param imagen
	 * @param insert
	 * @throws Exception
	 */
	private void constroiStatement(Produtos produto, PreparedStatement insert)
			throws Exception {
		insert.setString(1, produto.getProduto());
		
	}
	
	
	/**
	 * Atualiza o objeto no banco de dados
	 * @param imagem
	 * @throws Exception
	 */
	private void atualiza(Produtos produto) throws Exception {
		String sql = "UPDATE produtos SET produto=? where id = " + produto.getId();
		PreparedStatement update = connection.prepareStatement(sql);

		constroiStatement(produto, update);
		update.execute();
		connection.commit();
	}
	
	
	/**
	 * Grava ou atualiza o registro no banco de dados
	 * @param imagen
	 * @throws Exception
	 */
	public void salvarOuAtualizar(Produtos produto) throws Exception {
		try {
			if ( produto.getId() == null ||  produto.getId() <= 0) {// insere
				String sql = "INSERT INTO produtos(produto)VALUES ( ? );";
				PreparedStatement insert = connection.prepareStatement(sql);
				constroiStatement( produto, insert);
				insert.execute();
			} else {// atualiza
				atualiza( produto);
			}
			connection.commit();
		} catch (SQLException exception) {
			connection.rollback();
			exception.printStackTrace();
		}
	}
	

	/**
	 * Delete o registro no banco de dados
	 * @param codImg
	 * @throws Exception
	 */
	public void deleta(String codProduto) throws Exception {
		if (!codProduto.isEmpty() && codProduto != null) {
			String sql = "DELETE FROM produtos where id = " + codProduto;
			try {
				PreparedStatement delete = connection.prepareStatement(sql);
				delete.execute();
				connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Retorna o registro consultado pelo código passado por parametro
	 * @param cod
	 * @return Imagens
	 */
	public Produtos consulta(Integer cod) {
		Produtos retorno = new Produtos();
		try {
			String sql = "select * FROM produtos where id = " + cod;
			PreparedStatement find = connection.prepareStatement(sql);
			ResultSet resultSet = find.executeQuery();
			while (resultSet.next()) {
				retorno.setId(resultSet.getInt("id"));
				retorno.setProduto(resultSet.getString("produto"));
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return retorno;
	}
	
	
	/**
	 * Retorna uma lista com todos os registro de imagens do banco de dados
	 * @return List<Imagens>
	 */
	public List<Produtos> consultaTodos() {

		List<Produtos> retornoList = new ArrayList<Produtos>();
		String sql = "select id,produto FROM produtos order by id;";

		try {
			PreparedStatement find = connection.prepareStatement(sql);
			ResultSet resultSet = find.executeQuery();
			while (resultSet.next()) {
				Produtos retorno = new Produtos();
				retorno.setId(resultSet.getInt("id"));
				retorno.setProduto(resultSet.getString("produto"));
				

				retornoList.add(retorno);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return retornoList;
	}


	

}
