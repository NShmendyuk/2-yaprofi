package ru.urfuyaprofi.notes.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.urfuyaprofi.notes.controller.exception.EmptyRequestBody;
import ru.urfuyaprofi.notes.controller.exception.NoEntityException;
import ru.urfuyaprofi.notes.entity.Note;
import ru.urfuyaprofi.notes.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/")
    public List<Note> getAllNotes() {
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    public Note getNote(@PathVariable Long id) throws NoEntityException {
        return noteService.getNote(id);
    }

    @GetMapping()
    public List<Note> getQuery(@RequestParam String query) throws NoEntityException {
        return noteService.getNoteByParam(query);
    }

    @PostMapping
    public Note addNote(@RequestBody JSONObject noteJson) throws EmptyRequestBody {
        return noteService.addNote(noteJson);
    }

    @PutMapping("/{id}")
    public Note changeNote(@PathVariable Long id, @RequestBody JSONObject noteUpdate) throws NoEntityException {
        return noteService.changeNote(id, noteUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }
}
