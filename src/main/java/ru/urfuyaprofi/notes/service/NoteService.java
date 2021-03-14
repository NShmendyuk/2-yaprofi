package ru.urfuyaprofi.notes.service;

import org.json.simple.JSONObject;
import ru.urfuyaprofi.notes.controller.exception.EmptyRequestBody;
import ru.urfuyaprofi.notes.controller.exception.NoEntityException;
import ru.urfuyaprofi.notes.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> getAll();
    Note getNote(Long id) throws NoEntityException;
    List<Note> getNoteByParam(String query) throws NoEntityException;
    Note addNote(JSONObject noteJson) throws EmptyRequestBody;
    Note changeNote(Long id, JSONObject jsonUpdate) throws NoEntityException;
    void deleteNote(Long id);
}
