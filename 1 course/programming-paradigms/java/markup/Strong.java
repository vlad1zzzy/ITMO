package markup;

import java.util.List;

public class Strong extends AbstractParagraph implements Mark {
    Strong(List<Mark> words) {
        super(words);
    }

    @Override
    public void toMarkdown(StringBuilder words) {
        toMarkdown(words, "__");
    }

    @Override
    public void toHtml(StringBuilder words) {
        toHtml(words, "<strong>","</strong>");
    }
    @Override
    public void toTeX(StringBuilder words) {
        toTeX(words, "\\textbf{", "}");
    }
}