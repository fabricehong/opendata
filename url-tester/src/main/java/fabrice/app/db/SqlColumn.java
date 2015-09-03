package fabrice.app.db;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class SqlColumn {
    private String name;
    private SqlType type;
    private boolean nullable;


    public SqlColumn(String name, SqlType type, boolean nullable) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }

    public String toSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(" ").append(type.getRepr());
        if (!nullable) {
            stringBuilder.append(" ").append("not NULL");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return String.format("[%s|%s]", name, type.getRepr());
    }
}
