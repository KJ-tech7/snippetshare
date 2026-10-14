
package com.keshav.snippetshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "snippet_versions")
public class SnippetVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "snippet_id", nullable = false)
    private Snippet snippet;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private LocalDateTime savedAt;

    public SnippetVersion() {
    }

    public SnippetVersion(
            Snippet snippet,
            String title,
            String code,
            String language) {
        this.snippet = snippet;
        this.title = title;
        this.code = code;
        this.language = language;
        this.savedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }
}
