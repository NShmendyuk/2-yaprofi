package ru.urfuyaprofi.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfuyaprofi.notes.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
