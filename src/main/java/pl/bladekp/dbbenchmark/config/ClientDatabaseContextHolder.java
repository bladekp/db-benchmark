package pl.bladekp.dbbenchmark.config;

import lombok.Getter;

public class ClientDatabaseContextHolder {

    private static ThreadLocal<ClientDatabaseEnum> CONTEXT = new ThreadLocal<>();

    private static void set(ClientDatabaseEnum clientDatabase) {
        CONTEXT.set(clientDatabase);
    }

    static ClientDatabaseEnum getClientDatabase() {
        return CONTEXT.get();
    }

    private static void clear() {
        CONTEXT.remove();
    }

    public static void execute(Action action, ClientDatabaseEnum database){
        set(database);
        action.run();
        clear();
    }

    @Getter
    public enum ClientDatabaseEnum {
        MYSQL("mysql", "org.hibernate.dialect.MySQLDialect", true),
        H2("h2", "org.hibernate.dialect.H2Dialect", true),
        MONGO("mongo", "org.hibernate.ogm.datastore.mongodb.MongoDBDialect", false),
        POSTGRESQL("postgresql", "org.hibernate.dialect.PostgreSQL82Dialect", true),
        ORACLE("oracle", "org.hibernate.dialect.OracleDialect", true);

        private String datasourceNamespace;
        private String dialect;
        private boolean enabled;

        ClientDatabaseEnum(String datasourceNamespace, String dialect, boolean enabled){
            this.datasourceNamespace = datasourceNamespace;
            this.dialect = dialect;
            this.enabled = enabled;
        }
    }

    @FunctionalInterface
    public interface Action {
        void run();
    }
}