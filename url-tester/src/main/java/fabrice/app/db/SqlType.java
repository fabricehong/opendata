package fabrice.app.db;

/**
 * @author Fabrice Hong -- Liip AG
 * @date 03.09.15
 */
public enum SqlType {

    INTEGER("INTEGER");
    private final String repr;


    private SqlType(String repr) {
        this.repr = repr;
    }

    public String getRepr() {
        return repr;
    }
}
