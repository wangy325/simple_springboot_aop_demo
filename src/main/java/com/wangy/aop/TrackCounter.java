package com.wangy.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangy
 * @version 1.0
 * @date 2020/3/12 / 13:49
 */
//@Aspect
public class TrackCounter {
    private Map<Integer, Integer> trackCounts = new HashMap<>();

//    @Pointcut("execution( * com.wangy.aop.disk.BlankDisk.playTrack(int)) && args(trackNumber)")
    public void trackPlayed(int trackNumber) {
    }

    @AfterReturning(value = "trackPlayed(trackNumber)", argNames = "trackNumber")
    public void countTrack(int trackNumber) {
        int currentCount = getPlayCount(trackNumber);
        trackCounts.put(trackNumber, currentCount + 1);
    }

    public int getPlayCount(int trackNumber) {
        return trackCounts.getOrDefault(trackNumber, 0);
    }
}
