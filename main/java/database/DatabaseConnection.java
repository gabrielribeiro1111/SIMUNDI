package database;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("../home_workspace/SIMUNDI/src")
            .ignoreIfMissing()
            .load();

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        
        if (url == null || url.isBlank()) {
            throw new IllegalStateException(
                "DB_URL não configurada no arquivo .env"
            );
        }

        if (user == null || user.isBlank()) {
            throw new IllegalStateException(
                "DB_USER não configurado no arquivo .env"
            );
        }

        if (password == null) {
            password = "";
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }
        return DriverManager.getConnection(url, user, password);
    }
}
