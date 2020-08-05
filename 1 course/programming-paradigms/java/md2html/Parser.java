package md2html;

class Parser {
    private StringBuilder text;

    Parser(StringBuilder text) {
        this.text = text;
    }

    StringBuilder toHtml() {
        StringBuilder part = new StringBuilder();
        StringBuilder change = new StringBuilder();
        StringBuilder currentBlock = new StringBuilder();
        StringBuilder opener = new StringBuilder();
        String[] candidates = new String[]{"++", "**", "__","--", "`", "*", "_"};
        String[] open = new String[]{"<u>", "<strong>", "<strong>", "<s>", "<code>", "<em>", "<em>"};
        String[] close = new String[]{"</u>", "</strong>", "</strong>", "</s>", "</code>", "</em>", "</em>"};
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            switch (c) {
                case '<':
                    part.append("&lt;");
                    continue;
                case '>':
                    part.append("&gt;");
                    continue;
                case '&':
                    part.append("&amp;");
                    continue;
                case '\\':
                    if (text.charAt(i+1) == '*' || text.charAt(i+1) == '_')
                        part.append("\\");
                    continue;
                default:
                    part.append(c);
            }
        }
        //part - текущий Paragraph/Head для изменения
        for (int i = 0; i < 4; i++) {
            int j = 0;
            while (j < part.length() - 1) {
                char c = part.charAt(j);
                char c2 = part.charAt(j + 1);
                char tag = candidates[i].charAt(0);
                if (c == tag && c2 == tag) {
                    opener.append(open[i]);
                    j += 2;
                    while (j < part.length() - 2 && part.charAt(j) != tag && part.charAt(j + 1) != tag)
                        currentBlock.append(part.charAt(j++));
                    currentBlock.append(part.charAt(j++));
                    if (j >= part.length() - 2 && part.charAt(j) != tag && part.charAt(j + 1) != tag)
                        change.append(candidates[i]).append(currentBlock).append(part.charAt(j)).append(part.charAt(j + 1));
                    else
                        opener.append(currentBlock).append(close[i]);
                        j += 2;
                        change.append(opener);
                        opener = new StringBuilder();
                        currentBlock = new StringBuilder();
                } else {
                    change.append(c);
                    j++;
                }
                if (j >= part.length() - 1) {
                    if (j == part.length() - 1)
                        change.append(part.charAt(j));
                    part = change;
                    change = new StringBuilder();
                    break;
                }
            }
        }
        for (int i = 4; i < candidates.length; i++) {
            int j = 0;
            while (j < part.length()) {
                if (part.charAt(j) == '\\' && String.valueOf(part.charAt(j+1)).equals(candidates[i])) {
                    change.append(part.charAt(j + 1));
                    j += 2;
                    continue;
                }
                if (String.valueOf(part.charAt(j)).equals(candidates[i])) {
                    opener.append(open[i]);
                    j++;
                    while (j < part.length() && !String.valueOf(part.charAt(j)).equals(candidates[i])) {
                        if (part.charAt(j) == '\\' && String.valueOf(part.charAt(j+1)).equals(candidates[i])) {
                            currentBlock.append(part.charAt(j + 1));
                            j += 2;
                        }
                        currentBlock.append(part.charAt(j++));
                    }
                    if (j == part.length() && !String.valueOf(part.charAt(j-1)).equals(candidates[i])) {
                        change.append(candidates[i]).append(currentBlock);
                        currentBlock = new StringBuilder();
                        opener = new StringBuilder();
                    } else {
                        opener.append(currentBlock).append(close[i]);
                        j++;
                        change.append(opener);
                        opener = new StringBuilder();
                        currentBlock = new StringBuilder();
                    }
                } else {
                    change.append(part.charAt(j));
                    j++;
                }
                if (j >= part.length()){
                    part = change;
                    change = new StringBuilder();
                    break;
                }
            }
        }
        return part;
    }
}
