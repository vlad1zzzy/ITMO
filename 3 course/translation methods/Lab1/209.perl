while (<>) {
    s/\([^)]*\)/()/g;
    print;
}