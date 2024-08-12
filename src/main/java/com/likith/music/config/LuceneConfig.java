package com.likith.music.config;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexWriterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class LuceneConfig {

    private static final String INDEX_PATH = "/lucene/index/song";

    @Bean
    public Directory directory() {
        try {
            return FSDirectory.open(Paths.get(INDEX_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Lucene directory: " + e.getMessage(), e);
        }
    }

    @Bean
    public StandardAnalyzer analyzer() {
        return new StandardAnalyzer();
    }

    @Bean
    public IndexWriter indexWriter(Directory directory, StandardAnalyzer analyzer) {
        try {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            return new IndexWriter(directory, config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Lucene IndexWriter: " + e.getMessage(), e);
        }
    }

    @Bean
    public DirectoryReader directoryReader(Directory directory) {
        try {
            return DirectoryReader.open(directory);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create Lucene DirectoryReader: " + e.getMessage(), e);
        }
    }
}
