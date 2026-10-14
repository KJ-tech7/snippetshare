
package com.keshav.snippetshare.controller;

import com.keshav.snippetshare.model.Snippet;
import com.keshav.snippetshare.repository.SnippetRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/public/snippets")
public class PublicSnippetController {

    private final SnippetRepository snippetRepository;

    public PublicSnippetController(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    @GetMapping("/{token}")
    public Snippet getSharedSnippet(@PathVariable String token) {
        return snippetRepository.findByShareToken(token)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "This sharing link is invalid or has been revoked"));
    }
}
