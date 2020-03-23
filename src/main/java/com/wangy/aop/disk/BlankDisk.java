package com.wangy.aop.disk;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangy
 * @version 1.0
 * @date 2020/3/12 / 14:11
 */
@Setter
@Slf4j
public class BlankDisk implements CompactDisk {

    private String title;
    private String artist;
    private List<String> tracks;


    @Override
    public void play() {
        log.info("playing {} by {}", title, artist);
        for (String track : tracks) {
            log.info("-Track {}", track);
        }
    }

    @Override
    public void playTrack(int track) {
        String trackName = tracks.size() > track ? tracks.get(track) : tracks.get(0);
        log.info("-Track {} played", trackName);
    }
}
