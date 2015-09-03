package fabrice.app.csv;

import fabrice.app.csv.util.SqlUtils;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public class CsvHeader {
    private final String name;
    private final String normalizedName;

    public CsvHeader(String name) {
        this.name = name;
        this.normalizedName = SqlUtils.toSqlName(name);
    }
    public String getNormalizedName() {
        return normalizedName;
    }


}
