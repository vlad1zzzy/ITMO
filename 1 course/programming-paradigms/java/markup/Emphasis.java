package markup;

import java.util.List;

public class Emphasis extends AbstractParagraph implements Mark {
    Emphasis(List<Mark> words) {
        super(words);
    }

    @Override
    public void toMarkdown(StringBuilder words) {
        toMarkdown(words, "*");
    }

    @Override
    public void toHtml(StringBuilder words) {
        toHtml(words, "<em>", "</em>");
    }
    @Override
    public void toTeX(StringBuilder words) {
        toTeX(words, "\\emph{", "}");
    }
}