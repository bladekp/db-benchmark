package pl.bladekp.dbbenchmark.config;

import lombok.Getter;

import java.util.function.Supplier;

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

    public static void execute(Action action, ClientDatabaseEnum database) {
        set(database);
        action.run();
        clear();
    }

    public static <T> T execute(Supplier<T> action, ClientDatabaseEnum database){
        set(database);
        T result = action.get();
        clear();
        return result;
    }

    @Getter
    public enum ClientDatabaseEnum {
        MYSQL("mysql", "org.hibernate.dialect.MySQLDialect", false),
        H2("h2", "org.hibernate.dialect.H2Dialect", true),
        MONGO("mongo", "org.hibernate.ogm.datastore.mongodb.MongoDBDialect", true),
        POSTGRESQL("postgresql", "org.hibernate.dialect.PostgreSQL82Dialect", false),
        ORACLE("oracle", "org.hibernate.dialect.OracleDialect", false);

        private String datasourceNamespace;
        private String dialect;
        private boolean enabled;

        ClientDatabaseEnum(String datasourceNamespace, String dialect, boolean enabled) {
            this.datasourceNamespace = datasourceNamespace;
            this.dialect = dialect;
            this.enabled = enabled;
        }

        public static ClientDatabaseEnum defaultDatabase(){
            return H2;
        }
    }

    @FunctionalInterface
    public interface Action {
        void run();
    }
}