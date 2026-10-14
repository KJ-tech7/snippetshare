
package com.keshav.snippetshare.controller;

import com.keshav.snippetshare.model.AppUser;
import com.keshav.snippetshare.model.Snippet;
import com.keshav.snippetshare.model.SnippetVersion;
import com.keshav.snippetshare.repository.AppUserRepository;
import com.keshav.snippetshare.repository.SnippetRepository;
import com.keshav.snippetshare.repository.SnippetVersionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/snippets")
public class SnippetController {

    private final SnippetRepository snippetRepository;
    private final AppUserRepository appUserRepository;
    private final SnippetVersionRepository versionRepository;

    public SnippetController(
            SnippetRepository snippetRepository,
            AppUserRepository appUserRepository,
            SnippetVersionRepository versionRepository) {
        this.snippetRepository = snippetRepository;
        this.appUserRepository = appUserRepository;
        this.versionRepository = versionRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Snippet createSnippet(
            @RequestBody Snippet snippet,
            @AuthenticationPrincipal UserDetails currentUser) {

        snippet.setTitle(requireText(snippet.getTitle(), "title"));
        snippet.setCode(requireText(snippet.getCode(), "code"));
        snippet.setLanguage(requireText(snippet.getLanguage(), "language"));

        AppUser owner = appUserRepository
                .findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));

        snippet.setOwner(owner);
        snippet.setShareToken(null);

        return snippetRepository.save(snippet);
    }

    @GetMapping
    public List<Snippet> getAllSnippets(
            @AuthenticationPrincipal UserDetails currentUser) {

        return snippetRepository.findAllByOwner_Username(
                currentUser.getUsername());
    }

    @GetMapping("/{id}")
    public Snippet getSnippetById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        return findOwnedSnippet(id, currentUser.getUsername());
    }

    @PutMapping("/{id}")
    @Transactional
    public Snippet updateSnippet(
            @PathVariable Long id,
            @RequestBody Snippet updatedSnippet,
            @AuthenticationPrincipal UserDetails currentUser) {

        Snippet existingSnippet =
                findOwnedSnippet(id, currentUser.getUsername());

        String newTitle =
                requireText(updatedSnippet.getTitle(), "title");
        String newCode =
                requireText(updatedSnippet.getCode(), "code");
        String newLanguage =
                requireText(updatedSnippet.getLanguage(), "language");

        // Save the current version before changing anything.
        versionRepository.save(new SnippetVersion(
                existingSnippet,
                existingSnippet.getTitle(),
                existingSnippet.getCode(),
                existingSnippet.getLanguage()
        ));

        existingSnippet.setTitle(newTitle);
        existingSnippet.setCode(newCode);
        existingSnippet.setLanguage(newLanguage);

        return snippetRepository.save(existingSnippet);
    }

    @GetMapping("/{id}/versions")
    public List<SnippetVersion> getSnippetVersions(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        findOwnedSnippet(id, currentUser.getUsername());

        return versionRepository
                .findAllBySnippet_IdOrderBySavedAtDesc(id);
    }

    @PostMapping("/{id}/share")
    public Map<String, String> createShareLink(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        Snippet snippet = findOwnedSnippet(id, currentUser.getUsername());

        if (snippet.getShareToken() == null
                || snippet.getShareToken().isBlank()) {
            snippet.setShareToken(UUID.randomUUID().toString());
            snippetRepository.save(snippet);
        }

        return Map.of("token", snippet.getShareToken());
    }

    @DeleteMapping("/{id}/share")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeShareLink(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        Snippet snippet = findOwnedSnippet(id, currentUser.getUsername());
        snippet.setShareToken(null);
        snippetRepository.save(snippet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSnippet(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        Snippet snippet = findOwnedSnippet(id, currentUser.getUsername());
        snippetRepository.delete(snippet);
    }

    private Snippet findOwnedSnippet(Long id, String username) {
        return snippetRepository
                .findByIdAndOwner_Username(id, username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Snippet not found"));
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    fieldName + " is required");
        }

        return value;
    }
}
