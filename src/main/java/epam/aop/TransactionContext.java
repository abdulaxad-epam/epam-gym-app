package epam.aop;

import org.jboss.logging.Logger;

import java.util.UUID;

public class TransactionContext {
    private final Logger logger;
    private static final ThreadLocal<String> TRANSACTION_CONTEXT = new ThreadLocal<>();

    public static String getContext() {
        return TRANSACTION_CONTEXT.get();
    }

    public static void setContext(String transactionId) {
        TRANSACTION_CONTEXT.set(transactionId);
    }

    public static void clear(){
        TRANSACTION_CONTEXT.remove();
    }

    
    public TransactionContext(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz);
    }


    public void info(String message, Object... args) {
        logger.infov("TransactionID: {0} - " + message, mergeArgs(getContext(), args));
    }

    public void error(String message, Object... args) {
        logger.errorv("TransactionID: {0} - " + message, mergeArgs(getContext(), args));
    }

    public void debug(String message, Object... args) {
        logger.debugv("TransactionID: {0} - " + message, mergeArgs(getContext(), args));
    }

    public void warn(String message, Object... args) {
        logger.warnv("TransactionID: {0} - " + message, mergeArgs(getContext(), args));
    }

    public void resetTransactionId() {
        TRANSACTION_CONTEXT.set(UUID.randomUUID().toString());
    }

    private Object[] mergeArgs(String transactionId, Object... args) {
        Object[] newArgs = new Object[args.length + 1];
        newArgs[0] = transactionId;
        System.arraycopy(args, 0, newArgs, 1, args.length);
        return newArgs;
    }
}
