while (<>) {
    s/\([^)]*\)/()/g;
    print;
}
