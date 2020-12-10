package com.wangy.aop;

import com.wangy.aop.disk.BlankDisk;
import com.wangy.aop.disk.CompactDisk;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用java配置bean，和xml配置2选1
 *
 * @author wangy
 * @version 1.0
 * @date 2020/3/12 / 13:57
 */
@Configuration
@Profile("jc")
public class DiskConfig {

    @Bean("jcd")
    public CompactDisk saveRock() {
        BlankDisk cd = new BlankDisk();
        cd.setArtist("Fall Out Boy");
        cd.setTitle("Save Rock And Roll");
        List<String> tracks = new ArrayList<>();
        tracks.add("The Phoenix");
        tracks.add("My Songs Know What You Did In the Dark (Light Em Up)");
        tracks.add("Alone Together");
        tracks.add("Where Did the Party Go");
        tracks.add("Just One Yesterday (feat. Foxes)");
        tracks.add("The Mighty Fall (feat. Big Sean)");
        tracks.add("Missing You");
        tracks.add("Death Valley");
        cd.setTracks(tracks);
        return cd;
    }

    @Bean("jtc")
    public TrackCounter trackCounter() {
        return new TrackCounter();
    }
}
