package pl.bladekp.dbbenchmark.config;

public class ClientDatabaseContextHolder {

    private static ThreadLocal<ClientDatabaseEnum> CONTEXT = new ThreadLocal<>();

    public static void set(ClientDatabaseEnum clientDatabase) {
        CONTEXT.set(clientDatabase);
    }

    public static ClientDatabaseEnum getClientDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public enum ClientDatabaseEnum {
        MYSQL, H2, MONGO, POSTGRESQL
    }
}