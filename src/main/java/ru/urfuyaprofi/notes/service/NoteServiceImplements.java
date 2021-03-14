package ru.urfuyaprofi.notes.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.urfuyaprofi.notes.controller.exception.EmptyRequestBody;
import ru.urfuyaprofi.notes.controller.exception.NoEntityException;
import ru.urfuyaprofi.notes.entity.Note;
import ru.urfuyaprofi.notes.repository.NoteRepository;
import ru.urfuyaprofi.notes.service.helper.NoteUpdater;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImplements implements NoteService {
    private final NoteRepository noteRepository;

    @Value("${notes.emptytitle-replace.content.char-count}")
    private int contentCountChar;

    public List<Note> getAll() {
        List<Note> notes = noteRepository.findAll();
        notes.forEach(note -> {
            if (note.getTitle().length() == 0 && contentCountChar > 0) {
                String content = note.getContent();
                if (contentCountChar > content.length())
                    note.setTitle(content);
                else
                    note.setTitle(content.substring(0, contentCountChar));
            }
        });
        return notes;
    }

    public Note getNote(Long id) throws NoEntityException {
        Note note = noteRepository.findById(id).orElseThrow(() ->
            NoEntityException.createWithId(Note.class.getSimpleName().toLowerCase(), id));
        if (note.getTitle().length() == 0 && contentCountChar > 0) {
            String content = note.getContent();
                if (contentCountChar > content.length())
                    note.setTitle(content);
                else
                    note.setTitle(content.substring(0, contentCountChar));
        }
        return note;
    }

    public List<Note> getNoteByParam(String query) throws NoEntityException {
        List<Note> allNotes = noteRepository.findAll();
        List<Note> queriedNotes = new ArrayList<>();
        allNotes.forEach(note -> {
            if (note.getContent().contains(query) || note.getTitle().contains(query))
            {
                if (note.getTitle().length() == 0 && contentCountChar > 0) {
                    String content = note.getContent();
                    if (contentCountChar > content.length())
                        note.setTitle(content);
                    else
                        note.setTitle(content.substring(0, contentCountChar));
                }
                queriedNotes.add(note);
            }
        });
        if (queriedNotes.size() == 0)
            throw NoEntityException.createWithParam(Note.class.getSimpleName(), query);
        return queriedNotes;
    }

    public Note addNote(JSONObject noteJson) throws EmptyRequestBody {
        return noteRepository.save(NoteUpdater.createNoteFromJson(noteJson));
    }

    public Note changeNote(Long id, JSONObject jsonUpdate) throws NoEntityException {
        Note noteOld = noteRepository.findById(id).orElseThrow(() ->
                NoEntityException.createWithId(Note.class.getSimpleName().toLowerCase(), id));
        return NoteUpdater.updateNoteFromJson(noteOld, jsonUpdate);
    }

    @Transactional
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
