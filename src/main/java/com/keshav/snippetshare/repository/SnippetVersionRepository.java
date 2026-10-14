
package com.keshav.snippetshare.repository;

import com.keshav.snippetshare.model.SnippetVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetVersionRepository
        extends JpaRepository<SnippetVersion, Long> {

    List<SnippetVersion> findAllBySnippet_IdOrderBySavedAtDesc(
            Long snippetId);
}
