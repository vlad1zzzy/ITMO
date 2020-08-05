package markup;

public interface Mark {
    void toMarkdown(final StringBuilder words);

    void toHtml(final StringBuilder words);

    void toTeX(final StringBuilder words);
}