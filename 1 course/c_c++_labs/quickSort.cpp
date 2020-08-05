#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    char firstName[21];
    char secondName[21];
    char dadName[21];
    unsigned long long number;
} Info;

void swap(Info **first, Info **second) {
    Info *temp = *first;
    *first = *second;
    *second = temp;
}

int comparator(Info *first, Info *second) {
    int cmp1, cmp2, cmp3;
    cmp1 = strcmp(first->firstName, second->firstName);
    if (cmp1) return cmp1;

    cmp2 = strcmp(first->secondName, second->secondName);
    if (cmp2) return cmp2;

    cmp3 = strcmp(first->dadName, second->dadName);
    if (cmp3) return cmp3;

    long long diff = (long long) (first->number - second->number);
    if (diff) return (diff > 0) ? 1 : -1;

    return 0;
}

int partition(Info **array, int l, int r) {
    int j = l;
    Info *pivot = array[r];
    for (int i = l; i < r; i++) {
        if (comparator(array[i], pivot) < 0) {
            swap(&array[i], &array[j++]);
        }
    }
    swap(&array[r], &array[j]);
    return j;
}

void quickSort(Info **array, int l, int r) {
    while (l < r) {
        int q = partition(array, l, r);
        if (q - l < r - q) {
            quickSort(array, l, q - 1);
            l = q + 1;
        } else {
            quickSort(array, q + 1, r);
            r = q - 1;
        }
    }
}


int main(int argc, char **argv) {
    const int N = 5;
    int capacity = 5;
    int currentSize = 0;
    Info **stat;
    FILE *input, *output;

    if ((input = fopen(argv[1], "r")) == NULL) {
        printf("FAILED OPEN FILE");
        exit(1);
    }

    if ((stat = (Info **) malloc(N * sizeof(Info *))) == NULL) {
        printf("MEMORY ERROR");
        exit(2);
    }

    while (!feof(input)) {
        if (currentSize >= capacity) {
            capacity *= 2;
            if ((stat = (Info **) realloc(stat, capacity * sizeof(Info *))) == NULL) {
                printf("MEMORY ERROR");
                exit(2);
            }
        }

        if ((stat[currentSize] = (Info *) malloc(N * sizeof(Info))) == NULL) {
            printf("MEMORY ERROR");
            exit(2);
        }

        fscanf(input, "%s %s %s %llu",
               stat[currentSize]->firstName,
               stat[currentSize]->secondName,
               stat[currentSize]->dadName,
               &stat[currentSize]->number
        );
        currentSize++;
    }

    quickSort(stat, 0, currentSize - 1);
    //quick(stat, currentSize);

    if ((output = fopen(argv[2], "w")) == NULL) {
        printf("FAILED OPEN FILE");
        exit(1);
    }

    for (int i = 0; i < currentSize; i++) {
        fprintf(output, "%s %s %s %llu\n",
                stat[i]->firstName,
                stat[i]->secondName,
                stat[i]->dadName,
                stat[i]->number
        );
        free(stat[i]);
    }
    free(stat);

    fclose(input);
    fclose(output);

    return 0;
}

//--------V2.0--------------
/*void quick(Info **array, int length) {
    if (length < 2)
        return;

    int pivot = 1;
    for (int i = 0; i < length; i++) {
        if (comparator(array[i], *array) < 0) {
            swap(&array[i], &array[pivot++]);
        }
    }
    swap(&array[0], &array[pivot - 1]);
    quick(array, pivot);
    quick(array + pivot, length - pivot);
}*/