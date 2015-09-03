package fabrice.app.db;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import fabrice.app.csv.util.SqlUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class SqlTable {
    private Collection<SqlColumn> columns;
    private String tableName;

    public SqlTable(String tableName, Collection<SqlColumn> columns) {
        this.columns = columns;
        this.tableName = tableName;
    }

    public String createTableSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE "+tableName+" ");
        stringBuilder.append("(");
        stringBuilder.append(Joiner.on(", ").join(Collections2.transform(columns, new Function<SqlColumn, String>() {
            @Override
            public String apply(SqlColumn sqlColumn) {
                return sqlColumn.toSql();
            }
        })));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String insertSql(String[] row) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO "+tableName+" VALUES (");
        stringBuilder.append(Joiner.on(", ").join(Collections2.transform(Arrays.asList(row), new Function<String, String>() {
            @Override
            public String apply(String s) {
                return SqlUtils.toValue(s);
            }
        })));
        stringBuilder.append(")");
        return stringBuilder.toString();

    }

    public Collection<SqlColumn> getColumns() {
        return columns;
    }
}
