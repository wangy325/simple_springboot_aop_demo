package com.wangy.aop;

import com.wangy.aop.disk.CompactDisk;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Remove the &#64;{@link Aspect} annotation on class {@link TrackCounter} before run
 * this test. Or the SpringApplicationContext will create 2 Aspects when testing.
 *
 * cause aspect is configured in xml file and by using &#64;{@link Aspect}
 *
 * @author wangy
 * @version 1.0
 * @date 2020/12/10 / 12:31
 */
@SpringBootTest
@SpringJUnitConfig(locations = {"classpath:spring-aop.xml"})
@ActiveProfiles("xc")
public class TrackCountTestWithXml {

    @Autowired
    private CompactDisk cd;

    @Autowired
    private TrackCounter tc;

    @Test
    public void testTc() {
        cd.playTrack(1);
        cd.playTrack(1);
        cd.playTrack(1);

        cd.playTrack(2);

        cd.playTrack(4);
        cd.playTrack(4);

        cd.playTrack(6);
        cd.playTrack(6);
        cd.playTrack(6);
        try {
            cd.playTrack(6);
        } catch (Exception e) {
            //ignore
        }
        assertEquals(3, tc.getPlayCount(1));
        assertEquals(1, tc.getPlayCount(2));
        assertEquals(0, tc.getPlayCount(3));
        assertEquals(2, tc.getPlayCount(4));
        assertEquals(0, tc.getPlayCount(5));
        assertEquals(3, tc.getPlayCount(6));
    }
}

