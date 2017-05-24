package com.xiaofo1022.b5235.util;

/**
 * xxx
 *
 * @author yuchao
 * @since 2017-04-06 下午5:42
 */
public class SecretUtil {

  public static int[] quickSort(int[] originArray) {
    if (originArray.length == 0) {
      return originArray;
    } else if (originArray.length == 1) {
      return originArray;
    } else if (originArray.length == 2) {
      if (originArray[0] > originArray[1]) {
        int temp = originArray[0];
        originArray[0] = originArray[1];
        originArray[1] = temp;
        return originArray;
      }
    } else {
      int baseItem = originArray[0];
      int lessBaseCount = 0;
      int equalsBaseCount = 0;
      int morethanBaseCount = 0;
      for (int item : originArray) {
        if (item < baseItem) {
          lessBaseCount++;
        } else if (item == baseItem) {
          equalsBaseCount++;
        } else {
          morethanBaseCount++;
        }
      }
      int[] lessBaseArray = new int[lessBaseCount];
      int[] equalsBaseArray = new int[equalsBaseCount];
      int[] morethanBaseArray = new int[morethanBaseCount];
      int lessIndex = 0;
      int equalsIndex = 0;
      int morethanIndex = 0;
      for (int item : originArray) {
        if (item < baseItem) {
          lessBaseArray[lessIndex++] = item;
        } else if (item == baseItem) {
          equalsBaseArray[equalsIndex++] = item;
        } else {
          morethanBaseArray[morethanIndex++] = item;
        }
      }
      int[] sortedLess = quickSort(lessBaseArray);
      int[] sortedMore = quickSort(morethanBaseArray);
      int[] sortedArray = new int[sortedLess.length + equalsBaseArray.length + sortedMore.length];
      int sortedIndex = 0;
      for (int item : sortedLess) {
        sortedArray[sortedIndex++] = item;
      }
      for (int item : equalsBaseArray) {
        sortedArray[sortedIndex++] = item;
      }
      for (int item : sortedMore) {
        sortedArray[sortedIndex++] = item;
      }
      return sortedArray;
    }
    return originArray;
  }

  public static int findMaxByDivide(int[] array) {
    if (array.length == 1) {
      return array[0];
    } else if (array.length == 2) {
      if (array[0] > array[1]) {
        return array[0];
      } else {
        return array[1];
      }
    } else {
      int avg = array.length / 2;
      int[] frontArray = new int[avg];
      int[] backArray = new int[array.length - avg];
      int frontIndex = 0;
      int backIndex = 0;
      for (int i = 0; i < array.length; i++) {
        if (i < avg) {
          frontArray[frontIndex++] = array[i];
        } else {
          backArray[backIndex++] = array[i];
        }
      }
      int frontMax = findMaxByDivide(frontArray);
      int backMax = findMaxByDivide(backArray);
      if (frontMax > backMax) {
        return frontMax;
      } else {
        return backMax;
      }
    }
  }

  public static void main(String[] args) {
    int[] testArray = new int[] { 2, 3, 8, 9, 12, 11, 10, 9, 7, 6, 5, 5, 4, 3, 3, 2, 1 };
    int[] result = quickSort(testArray);
    System.out.println(result);
    int max = findMaxByDivide(testArray);
    System.out.println("Max: " + max);
  }
}
