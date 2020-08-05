package markup;
import java.util.List;

abstract class AbstractParagraph implements Mark {
    private List<Mark> parts;

    AbstractParagraph(List<Mark> parts) {
        this.parts = parts;
    }

    void toMarkdown(StringBuilder words, String symbols) {
        words.append(symbols);
        for (Mark current: parts) {
            current.toMarkdown(words);
        }
        words.append(symbols);
    }

    void toHtml(StringBuilder words, String First, String Last) {
        words.append(First);
        for (Mark current: parts) {
            current.toHtml(words);
        }
        words.append(Last);
    }
    void toTeX(StringBuilder words, String First, String Last) {
        words.append(First);
        for (Mark current: parts) {
            current.toTeX(words);
        }
        words.append(Last);
    }
}