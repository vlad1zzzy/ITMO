$isStart = 1;
$isBreak = 0;
$isEmpty = '^\s*$';

while (<>) {
    s/<[^>]*>//g;

    if (/$isEmpty/ and $isStart) {
        next;
    }

    if (not /$isEmpty/) {
        if ($isBreak) {
            print "\n";
        }
        $isBreak = $isStart = 0;
        s/(^\s+)|(\s+$)//g;
        s/(\s){2,}/ /g;
        print;
        print "\n";
    } else {
        $isBreak = 1;
    }
}