package pl.bladekp.dbbenchmark.config;

import lombok.Getter;

public class ClientDatabaseContextHolder {

    private static ThreadLocal<ClientDatabaseEnum> CONTEXT = new ThreadLocal<>();

    public static void set(ClientDatabaseEnum clientDatabase) {
        CONTEXT.set(clientDatabase);
    }

    static ClientDatabaseEnum getClientDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    @Getter
    public enum ClientDatabaseEnum {
        MYSQL("mysql", true),
        H2("h2", true),
        MONGO("mongo", false),
        POSTGRESQL("postgresql", true),
        ORACLE("oracle", false);

        private String datasourceNamespace;
        private boolean enabled;

        ClientDatabaseEnum(String datasourceNamespace, boolean enabled){
            this.datasourceNamespace = datasourceNamespace;
            this.enabled = enabled;
        }
    }
}