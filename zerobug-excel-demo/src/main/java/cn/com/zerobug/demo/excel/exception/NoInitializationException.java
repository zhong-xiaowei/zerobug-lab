package cn.com.zerobug.demo.excel.exception;

/**
 * @author zhongxiaowei
 * @contact zhongxiaowei.nice@gmail.com
 * @date 2022/8/25
 */
public class NoInitializationException extends RuntimeException {
    public NoInitializationException() {
        super();
    }

    public NoInitializationException(String message) {
        super(message);
    }

    public NoInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoInitializationException(Throwable cause) {
        super(cause);
    }

    protected NoInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
