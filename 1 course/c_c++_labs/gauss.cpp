#include <stdio.h>
#include <stdlib.h>
#include <math.h>

const double eps = 1e-6;

int gauss(float **matrix, float *ans, int n) {
    for (int row = 0; row < n; row++) {
        float firstMax = fabsf(matrix[row][row]);
        int rowIndex = row;
        for (int nextRow = row + 1; nextRow < n; nextRow++) {
            float curMax = fabsf(matrix[nextRow][row]);
            if (curMax > firstMax) {
                firstMax = curMax;
                rowIndex = nextRow;
            }
        }
        if (firstMax < eps)
            continue;
        if (rowIndex != row) {
            for (int curCol = row; curCol <= n; curCol++) {
                float tmp = matrix[row][curCol];
                matrix[row][curCol] = matrix[rowIndex][curCol];
                matrix[rowIndex][curCol] = tmp;
            }
        }
        for (int curRow = row + 1; curRow < n; curRow++) {
            float diff = matrix[curRow][row] / matrix[row][row];
            for (int curCol = row; curCol <= n; curCol++) {
                matrix[curRow][curCol] -= diff * matrix[row][curCol];
            }
        }
    }
    for (int row = n - 1; row >= 0; row--) {
        if (fabsf(matrix[row][row]) < eps && fabsf(matrix[row][n]) > eps) {
            return -1;
        }
        if (fabsf(matrix[row][row]) < eps && fabsf(matrix[row][n]) < eps) {
            return 1;
        }
        ans[row] = matrix[row][n] / matrix[row][row];
        for (int curRow = 0; curRow < row; curRow++)
            matrix[curRow][n] -= matrix[curRow][row] * ans[row];
    }
    return 0;
}


int main(int argc, char **argv) {
    int N, M;
    FILE *input, *output;

    if ((input = fopen(argv[1], "r")) == NULL) {
        printf("FAILED OPEN FILE");
        exit(1);
    }
    fscanf(input, "%i", &N);
    if (N < 1) {
        printf("Bad size");
        fclose(input);
        return 0;
    }
    M = N + 1;
    float *matrix[N];
    for (int i = 0; i < N; i++) {
        float *row;
        if ((row = (float*) malloc(sizeof(float) * M)) == NULL) {
            printf("MEMORY ERROR");
            exit(2);
        }
        for (int j = 0; j < M; j++)
            fscanf(input, "%f", &row[j]);
        matrix[i] = row;
    }
    float ans[N];

    int flag = gauss(matrix, ans, N);

    if ((output = fopen(argv[2], "w")) == NULL) {
        printf("FAILED OPEN FILE");
        exit(1);
    }

    if (flag == 1) {
        fprintf(output, "many solutions\n");
    } else if (flag == -1) {
        fprintf(output, "no solution\n");
    } else {
        for (int j = 0; j < N; j++) {
            fprintf(output, "%f\n", ans[j]);
        }
    }

    for (int i = 0; i < N; i++) {
        free(matrix[i]);
    }
    free(matrix);

    fclose(input);
    fclose(output);
    return 0;
}