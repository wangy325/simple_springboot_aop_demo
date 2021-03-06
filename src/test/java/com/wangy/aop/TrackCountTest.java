package com.wangy.aop;

import com.wangy.aop.disk.CompactDisk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wangy
 * @version 1.0
 * @date 2020/3/12 / 14:42
 */
@SpringBootTest
@ActiveProfiles("jc")
public class TrackCountTest {

    @Autowired
    private CompactDisk cd;

    @Autowired
    private TrackCounter tc;

    @Test
    public void testTc() {

        try {
            cd.playTrack(-1);
        } catch (Exception e) {
            // ignore
        }
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
            // ignore
        }
        try {
            cd.playTrack(6);
        } catch (Exception e) {
            // ignore
        }

        assertEquals(3, tc.getPlayCount(1));
        assertEquals(1, tc.getPlayCount(2));
        assertEquals(0, tc.getPlayCount(3));
        assertEquals(2, tc.getPlayCount(4));
        assertEquals(0, tc.getPlayCount(5));
        assertEquals(3, tc.getPlayCount(6));

    }
}
