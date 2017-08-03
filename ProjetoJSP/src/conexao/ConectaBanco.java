package conexao;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConectaBanco {
	//Conexção SQL Java
		private static Connection connection = null;

		// Método estático que faz sempre a chamada para conectar
		static {
			conectar();
		}

		public ConectaBanco() {
			conectar();
		}

		/**
		 * Realiza a conexção com o banco de dados
		 */
		private static void conectar() {
			try {
				
				if (connection == null) {
					Class.forName("com.mysql.jdbc.Driver"); // driver mysql
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto_jsp", "root", "1234");// url do banco de dados com user e senha
					connection.setAutoCommit(false);// nao dar commit automatico
					connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // tipo da transação no banco de dados
				}
			} catch (Exception e) {
				throw new RuntimeException("Erro ao conectar com a base de dados."
						+ e);
			}

		}

		/**
		 * Retorna a conex�o com o banco de dados
		 * @return Connection
		 */
		public static Connection getConnection() {
			return connection;
		}

	}
