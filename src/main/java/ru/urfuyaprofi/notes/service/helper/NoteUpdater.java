package ru.urfuyaprofi.notes.service.helper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import ru.urfuyaprofi.notes.controller.exception.EmptyRequestBody;
import ru.urfuyaprofi.notes.entity.Note;

@UtilityClass
@Slf4j
public class NoteUpdater {
    @Value("${notes.emptytitle-replace.content.char-count}")
    private int contentCountChar;

    public Note createNoteFromJson(JSONObject jsonObject) throws EmptyRequestBody {
        String title = "";
        String content = "";
        if (!jsonObject.get("title").equals("") || jsonObject.get("title") != null)
            title = (String) jsonObject.get("title");
        if (!jsonObject.get("content").equals("") || jsonObject.get("content") != null)
            content = (String) jsonObject.get("content");

        if (content.equals("")) throw EmptyRequestBody.createWith(Note.class.getSimpleName());
        if (title.equals("") && contentCountChar > 0) {
            if (contentCountChar > content.length())
                title = content;
            else
                title = content.substring(0, contentCountChar);
        }
        log.info("converted json to note with param: \ntitle:{} \ncontent:{}", title, content);
        return Note.builder().title(title).content(content).build();
    }

    public Note updateNoteFromJson(Note noteToUpdate, JSONObject jsonObject) {
        String title = noteToUpdate.getTitle();
        String content = noteToUpdate.getContent();
        if (!jsonObject.get("title").equals("") || jsonObject.get("title") != null)
            title = (String) jsonObject.get("title");
        if (!jsonObject.get("content").equals("") || jsonObject.get("content") != null)
        content = (String) jsonObject.get("content");

        log.info("converted json to note with param: \ntitle:{} \ncontent:{}", title, content);
        noteToUpdate.setTitle(title);
        noteToUpdate.setContent(content);
        return noteToUpdate;
    }
}
