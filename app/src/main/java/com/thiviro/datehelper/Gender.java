package com.thiviro.datehelper;

public enum Gender {
  MALE, FEMALE, ERROR;

  public static Gender getEnum(String gender){
    switch (gender){
      case "MALE":
        return MALE;
      case "FEMALE":
        return FEMALE;
        default:
          return ERROR;
    }
  }

  @Override
  public String toString() {
    if (this == MALE){
      return "MALE";
    }
    else if (this == FEMALE) {
      return "FEMALE";
    }
    else{
      return "ERROR";
    }
  }
}
