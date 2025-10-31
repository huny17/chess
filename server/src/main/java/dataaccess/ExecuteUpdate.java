package dataaccess;

import model.UserData;
import java.sql.*;
import static java.sql.Types.NULL;

public class ExecuteUpdate {

    public int executeUpdate(String statement, Object... params) throws DataAccessException{ //obj .. param ~ array
        try(Connection con = DatabaseManager.getConnection()){
            try(PreparedStatement ps = con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)){
                for(int i = 0; i < params.length; i++){  //reading through to update
                    Object param = params[i];
                    if(param instanceof String p){ ps.setString(i + 1, p);}
                    else if (param instanceof Integer p){ ps.setInt(i + 1, p);}
                    else if (param instanceof  UserData p){ ps.setString(i + 1, p.toString());}
                    else if (param == null){ ps.setNull(i + 1, NULL);}
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e){
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    public void configureDatabase(String[] createStatements) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()){
            for (String statement : createStatements){
                try (var preparedStatement = conn.prepareStatement(statement)){
                    preparedStatement.executeUpdate();
                }
            }
        } catch(SQLException e){
            throw new DataAccessException(String.format("Unable to configure database: %s", e.getMessage()));
        }
    }

}
