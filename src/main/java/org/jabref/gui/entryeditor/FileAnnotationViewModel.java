package org.jabref.gui.entryeditor;

import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.jabref.logic.l10n.Localization;
import org.jabref.model.pdf.FileAnnotation;
import org.jabref.model.pdf.FileAnnotationType;

public class FileAnnotationViewModel extends FileAnnotation {

    private StringProperty author = new SimpleStringProperty();

    public FileAnnotationViewModel(FileAnnotation annotation) {
        super(annotation.getAuthor(), annotation.getTimeModified(), annotation.getPage(), annotation.getContent(),
                annotation.getAnnotationType(), annotation.hasLinkedAnnotation() ? Optional.of(annotation.getLinkedFileAnnotation()) : Optional.empty());
        author.set(annotation.getContent()); // Use content just for test, since some annotations don't have an author
    }

    public StringProperty authorProperty() {
        return author;
    }

    @Override
    public String toString() {
        if (this.hasLinkedAnnotation() && this.getContent().isEmpty()) {
            if (FileAnnotationType.UNDERLINE.equals(this.getAnnotationType())) {
                return Localization.lang("Empty Underline");
            }
            if (FileAnnotationType.HIGHLIGHT.equals(this.getAnnotationType())) {
                return Localization.lang("Empty Highlight");
            }
            return Localization.lang("Empty Marking");
        }

        if (FileAnnotationType.UNDERLINE.equals(this.getAnnotationType())) {
            return Localization.lang("Underline") + ": " + this.getContent();
        }
        if (FileAnnotationType.HIGHLIGHT.equals(this.getAnnotationType())) {
            return Localization.lang("Highlight") + ": " + this.getContent();
        }

        return super.toString();
    }

    public String getDescription() {
        return null;
    }

    private String getMarking(FileAnnotation annotation) {
        if (annotation.hasLinkedAnnotation()) {
            return getContentOrNA(annotation.getLinkedFileAnnotation().getContent());
        }
        return "N/A";
    }

    private String getContentOrNA(String content) {
        if (content.isEmpty()) {
            return "N/A";
        }
        return content;
    }
}
