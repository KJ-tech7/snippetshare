
package com.keshav.snippetshare.repository;

import com.keshav.snippetshare.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    List<Snippet> findAllByOwner_Username(String username);

    Optional<Snippet> findByIdAndOwner_Username(Long id, String username);

    Optional<Snippet> findByShareToken(String shareToken);
}
