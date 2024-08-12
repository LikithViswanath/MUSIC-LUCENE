package com.likith.music.service;

import com.likith.music.model.Song;
import com.likith.music.exception.CustomException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SongService {


    private final StandardAnalyzer analyzer;

    private final IndexWriter indexWriter;

    private final DirectoryReader directoryReader;

    public SongService(StandardAnalyzer analyzer, IndexWriter indexWriter, DirectoryReader directoryReader) {
        this.analyzer = analyzer;
        this.indexWriter = indexWriter;
        this.directoryReader = directoryReader;
    }

    public void insert(Song song) {
        try {
            Document doc = new Document();
            doc.add(new TextField("songId", song.getSongId(), Field.Store.YES));
            doc.add(new TextField("name", song.getName(), Field.Store.YES));
            doc.add(new TextField("genre", song.getGenre(), Field.Store.YES));
            doc.add(new TextField("artist", song.getArtist(), Field.Store.YES));
            doc.add(new TextField("album", song.getAlbum(), Field.Store.YES));
            doc.add(new TextField("rating", String.valueOf(song.getRating()), Field.Store.YES));
            doc.add(new TextField("ratingNum", String.valueOf(song.getRatingNum()), Field.Store.YES));
            indexWriter.addDocument(doc);
            indexWriter.commit();
        } catch (IOException e) {
            throw new CustomException("Failed to insert song: " + e.getMessage());
        }
    }

    public void delete(String songId) {
        try {
            Query query = new QueryParser("songId", analyzer).parse(songId);
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
        } catch (IOException | ParseException e) {
            throw new CustomException("Failed to delete song: " + e.getMessage());
        }
    }

    public Map<String, String> queryById(String songId) {
        try {
            IndexSearcher searcher = new IndexSearcher(directoryReader);
            Query query = new QueryParser("songId", analyzer).parse(songId);
            TopDocs topDocs = searcher.search(query, 1);
            if (topDocs.totalHits.value > 0) {
                Document doc = searcher.doc(topDocs.scoreDocs[0].doc);
                Map<String, String> result = new HashMap<>();
                result.put("songId", doc.get("songId"));
                result.put("name", doc.get("name"));
                result.put("genre", doc.get("genre"));
                result.put("artist", doc.get("artist"));
                result.put("album", doc.get("album"));
                result.put("rating", doc.get("rating"));
                result.put("ratingNum", doc.get("ratingNum"));
                return result;
            }
            return Collections.emptyMap();
        } catch (IOException | ParseException e) {
            throw new CustomException("Failed to query song by ID: " + e.getMessage());
        }
    }

    public List<Map<String, String>> queryAll() {
        try {
            IndexSearcher searcher = new IndexSearcher(directoryReader);
            Query query = new QueryParser("name", analyzer).parse("*:*");
            TopDocs topDocs = searcher.search(query, 10);
            List<Map<String, String>> result = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                Map<String, String> map = new HashMap<>();
                map.put("songId", doc.get("songId"));
                map.put("name", doc.get("name"));
                map.put("genre", doc.get("genre"));
                map.put("artist", doc.get("artist"));
                map.put("album", doc.get("album"));
                map.put("rating", doc.get("rating"));
                map.put("ratingNum", doc.get("ratingNum"));
                result.add(map);
            }
            return result;
        } catch (IOException | ParseException e) {
            throw new CustomException("Failed to query all songs: " + e.getMessage());
        }
    }

    public List<Map<String, String>> query(String searchString) {
        try {
            IndexSearcher searcher = new IndexSearcher(directoryReader);
            Query query = new QueryParser("name", analyzer).parse(searchString);
            TopDocs topDocs = searcher.search(query, 10);
            List<Map<String, String>> result = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                Map<String, String> map = new HashMap<>();
                map.put("songId", doc.get("songId"));
                map.put("name", doc.get("name"));
                map.put("genre", doc.get("genre"));
                map.put("artist", doc.get("artist"));
                map.put("album", doc.get("album"));
                map.put("rating", doc.get("rating"));
                map.put("ratingNum", doc.get("ratingNum"));
                result.add(map);
            }
            return result;
        } catch (IOException | ParseException e) {
            throw new CustomException("Failed to search songs: " + e.getMessage());
        }
    }
}
