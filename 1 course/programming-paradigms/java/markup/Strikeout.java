package markup;

import java.util.List;

public class Strikeout extends AbstractParagraph implements Mark {
    Strikeout(List<Mark> words) {
        super(words);
    }

    @Override
    public void toMarkdown(StringBuilder words) {
        toMarkdown(words, "~");
    }

    @Override
    public void toHtml(StringBuilder words) {
        toHtml(words, "<s>","</s>");
    }
    @Override
    public void toTeX(StringBuilder words) {
        toTeX(words, "\\textst{", "}");
    }
}
