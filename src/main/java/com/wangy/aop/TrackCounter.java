package com.wangy.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用基于注解的切面时，必须开启&#64;{@link Aspect}注解，自定义&#64;{@link Pointcut}和&#64;{@link AfterReturning}通知
 *
 * @author wangy
 * @version 1.0
 * @date 2020/3/12 / 13:49
 */
//@Aspect
public class TrackCounter {
    private Map<Integer, Integer> trackCounts = new HashMap<>();

//    @Pointcut("execution( * com.wangy.aop.disk.BlankDisk.playTrack(int)) && args(trackNumber)")
//    public void trackPlayed(int trackNumber) {
//    }

    //    @AfterReturning(value = "trackPlayed(trackNumber)", argNames = "trackNumber")
    public void countTrack(int trackNumber) {
        int currentCount = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, currentCount + 1);
    }

    public int getPlayCount(int trackNumber) {
        return trackCounts.getOrDefault(trackNumber, 0);
    }

//    @Around(value = "trackPlayed(trackNumber)", argNames = "trackNumber")
    public void aroundTest(ProceedingJoinPoint jp, int trackNumber) throws Throwable {

        System.out.println("electricity on ...");

        // do some judgement

        jp.proceed();

        System.out.println("play end...");
    }
}
