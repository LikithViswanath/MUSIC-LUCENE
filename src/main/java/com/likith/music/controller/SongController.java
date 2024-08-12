package com.likith.music.controller;

import com.likith.music.model.Song;
import com.likith.music.service.SongService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public void insert(@RequestBody Song song) {
        songService.insert(song);
    }

    @DeleteMapping("/{songId}")
    public void delete(@PathVariable String songId) {
        songService.delete(songId);
    }

    @GetMapping("/{songId}")
    public Map<String, String> queryById(@PathVariable String songId) {
        return songService.queryById(songId);
    }

    @GetMapping
    public List<Map<String, String>> queryAll() {
        return songService.queryAll();
    }

    @GetMapping("/search")
    public List<Map<String, String>> query(@RequestParam String searchString) {
        return songService.query(searchString);
    }
}
