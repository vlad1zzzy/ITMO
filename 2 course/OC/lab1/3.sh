#!/bin/bash
echo "Choose smth:"
echo -e "1-nano\n2-vi\n3-links\n4-exit"
read num
case $num in
1 ) /bin/nano;;
2 ) /bin/vi;;
3 ) /bin/links;;
4 ) exit 0;;
esac

