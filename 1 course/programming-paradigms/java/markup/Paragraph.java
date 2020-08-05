package markup;

import java.util.List;

public class Paragraph extends AbstractParagraph {

    Paragraph(List<Mark> parts) {
        super(parts);
    }

    @Override
    public void toMarkdown(StringBuilder words) {
        toMarkdown(words, "");
    }

    @Override
    public void toHtml(StringBuilder words) {
        toHtml(words, "","");
    }
    @Override
    public void toTeX(StringBuilder words) {
        toTeX(words, "","");
    }
}