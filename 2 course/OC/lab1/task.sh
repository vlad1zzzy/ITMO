if [[ "$#" -eq 13 ]];
then
	for i in "$@"; do
		echo Обработка параметра "$i" дала $(($i * RANDOM))
	done;
fi;