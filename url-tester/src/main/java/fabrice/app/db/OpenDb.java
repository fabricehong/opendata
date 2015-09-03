package fabrice.app.db;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class OpenDb {

    private String dbms = "mysql";
    private String serverName = "localhost";
    private String userName = "root";
    private Object password = "aslk";
    private int portNumber = 3306;
    private String dbName = "opendata";

    private Connection connection;

    public OpenDb() {
        try {
            connection = connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Connection connect() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + "://" +
                            this.serverName +
                            ":" + this.portNumber + "/" + this.dbName,
                    connectionProps);
        } else if (this.dbms.equals("derby")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + ":" +
                            this.dbName +
                            ";create=true",
                    connectionProps);
        }
        System.out.println("Connected to database");
        return conn;
    }

    public List<Map> getResults(String query)
            throws SQLException {

        List<Map> resultList = new ArrayList<Map>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                Map obj = new LinkedHashMap();
                for (int i=1; i<columnCount+1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    obj.put(column_name, getObject(rs, rsmd, i, column_name));
                }
                resultList.add(obj);
            }
        } catch (SQLException e ) {
            System.out.println(e);
            return null;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return resultList;
    }

    private Object getObject(ResultSet rs, ResultSetMetaData rsmd, int colIndex, String column_name) throws SQLException {
        Object value = null;
        if(rsmd.getColumnType(colIndex) == Types.ARRAY){
            value = rs.getArray(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.BIGINT){
            value = rs.getInt(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.BOOLEAN){
            value = rs.getBoolean(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.BLOB){
            value = rs.getBlob(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.DOUBLE){
            value = rs.getDouble(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.FLOAT){
            value = rs.getFloat(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.INTEGER){
            value = rs.getInt(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.NVARCHAR){
            value = rs.getNString(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.VARCHAR){
            value = rs.getString(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.TINYINT){
            value = rs.getInt(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.SMALLINT){
            value = rs.getInt(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.DATE){
            value = rs.getDate(column_name);
        }
        else if(rsmd.getColumnType(colIndex)== Types.TIMESTAMP){
            value = rs.getTimestamp(column_name);
        }
        else{
            value = rs.getObject(column_name);
        }

        return value;
    }

    public static void main(String[] args) throws SQLException, IOException {

        OpenDb openDb = new OpenDb();
        String query = "SELECT\n" +
                "    opendata.newTable.Model,\n" +
                "    opendata.newTable.Line1,\n" +
                "    opendata.otherTable.Gender1t\n" +
                "FROM\n" +
                "    opendata.newTable\n" +
                "INNER JOIN\n" +
                "    opendata.otherTable\n" +
                "ON\n" +
                "    (\n" +
                "        opendata.newTable.Model = opendata.otherTable.Modelr) ;";
        List<Map> maps = openDb.getResults(query);

        ObjectMapper objectMapper = new ObjectMapper();

        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maps);
        System.out.println(s);

    }

    public void createTable(SqlTable table) {
        try {
            Statement statement = connection.createStatement();
            String tableSql = table.createTableSql();
            System.out.println(tableSql);
            statement.executeUpdate(tableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertInTable(SqlTable table, String[] row) {
        int colSize = table.getColumns().size();
        if (colSize !=row.length) {
            throw new RuntimeException(String.format("The row have not the same size as expected. Table columns : %s, %s\nrow : %s, %s", colSize, table.getColumns(), row.length, Arrays.asList(row)));
        }
        try {
            Statement statement = connection.createStatement();
            String sql = table.insertSql(row);
            System.out.println(sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
