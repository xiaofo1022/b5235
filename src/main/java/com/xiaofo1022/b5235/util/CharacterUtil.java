package com.xiaofo1022.b5235.util;

public class CharacterUtil {

  public static String filter4LengthUtf8Character(String originStr) {
    if (originStr != null) {
      char[] originChars = originStr.toCharArray();
      for (int i = 0; i < originChars.length; i++) {
        if ((originChars[i] & 0xF8) == 0xF0) {
          for (int j = 0; j < 4; j++) {
            originChars[i + j] = 0x30;
          }
          i += 3;
        }
      }
      return new String(originChars);
    }
    return originStr;
  }
}
