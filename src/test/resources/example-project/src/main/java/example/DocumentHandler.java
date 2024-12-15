package example;

import java.util.ArrayList;
import java.util.List;

public class DocumentHandler {
    private List<String> documents;

    public DocumentHandler() {
        this.documents = new ArrayList<>();
    }

    public void addDocument(String document) {
        documents.add(document);
    }

    public boolean removeDocument(String document) {
        return documents.remove(document);
    }

    public String getDocument(int index) {
        if (index < 0 || index >= documents.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return documents.get(index);
    }

    public List<String> getAllDocuments() {
        return documents;
    }
}
