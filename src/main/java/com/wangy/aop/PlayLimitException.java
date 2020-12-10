package com.wangy.aop;

/**
 * @author wangy
 * @version 1.0
 * @date 2020/12/10 / 23:25
 */
public class PlayLimitException extends Throwable {

    public PlayLimitException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
