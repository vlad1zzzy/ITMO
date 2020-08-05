package markup;

public class Text implements Mark {
    private String text;

    Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder words) {
        words.append(text);
    }

    @Override
    public void toHtml(StringBuilder words) {
        words.append(text);
    }

    @Override
    public void toTeX(StringBuilder words){
        words.append(text);
    }
}