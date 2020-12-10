package com.wangy.aop;

import com.wangy.aop.disk.CompactDisk;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用基于注解的切面时，必须开启&#64;{@link Aspect}注解，自定义&#64;{@link Pointcut}切点和
 * 通知
 *
 * @author wangy
 * @version 1.0
 * @date 2020/3/12 / 13:49
 */
@Aspect
@Slf4j
public class TrackCounter {

    private Map<Integer, Integer> trackCounts = new HashMap<>();

    public int getPlayCount(int trackNumber) {
        return trackCounts.getOrDefault(trackNumber, 0);
    }

    @Pointcut("execution( * com.wangy.aop.disk.BlankDisk.playTrack(..)) && args(trackNumber)")
    public void pc1(int trackNumber){
    }

    @Pointcut("execution(* com.wangy.aop.disk.BlankDisk.playTrack(int))")
    public void pc2(){}

    @Before(value = "pc2()")
    public void init(){
        // do something
        log.info("start playing");
    }

    @AfterReturning(value = "pc1(trackNumber)")
    public void countTrack(int trackNumber) {
        log.info("Track {} played", trackNumber);
        trackCounts.put(trackNumber, getPlayCount(trackNumber) + 1);
    }

    @AfterThrowing(value = "pc1(trackNumber)",throwing = "ex", argNames = "trackNumber,ex")
    public void skipTrack(int trackNumber, Throwable ex) {
            log.warn(ex.getMessage());
            log.info("track {} skipped", trackNumber);
    }

    @After(value = "pc2()")
    public void after(){
        // do something
    }

    @Around(value = "pc1(trackNumber)")
    public Object aroundTest(ProceedingJoinPoint jp, int trackNumber) throws Throwable {
        int pl = 2;
        // do some judgement
        if (getPlayCount(trackNumber) > pl) {
            log.info("track {} has been played more than twice, skip this track", trackNumber);
            // change the behavior of pointcut method
            CompactDisk target = (CompactDisk) jp.getTarget();
            target.playTrack(-1);

            // this thrown can not be handled in afterThrowing advice
//            throw new PlayLimitException("track " + trackNumber + " has reach the play limit.");
        }else{
            return jp.proceed();
        }
        return null;
    }
}
